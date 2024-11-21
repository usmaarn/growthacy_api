package africa.techverse.growthacy.v1.filters;


import africa.techverse.growthacy.v1.entities.User;
import africa.techverse.growthacy.v1.services.UserService;
import africa.techverse.growthacy.v1.utils.JsonWebToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;


public class AuthenticationFilter extends HttpFilter {
    private final JsonWebToken jsonWebToken;
    private final UserService userService;
    private final List<String> publicRoutes = Arrays.asList(
            "/v1/auth/register",
            "/v1/auth/login",
            "/v1/auth/forgot-password",
            "/v1/auth/reset-password"
    );

    public AuthenticationFilter(JsonWebToken jsonWebToken, UserService userService) {
        this.jsonWebToken = jsonWebToken;
        this.userService = userService;
    }

    @Override
    public void doFilter(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain
    )
            throws IOException, ServletException
    {
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.addHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        }

        String uri = request.getRequestURI();

        if (publicRoutes.contains(uri)) {
            chain.doFilter(request, response);
            return;
        }

        try {
            String authorization = request.getHeader("Authorization");
            if (authorization == null || !authorization.startsWith("Bearer ")) {
                throw new ServletException("Authorization header is invalid");
            }
            String token = authorization.substring(7);

            if (jsonWebToken.isTokenExpired(token)) throw new ServletException("Token expired");

            String email = jsonWebToken.getEmailFromToken(token);
            User user = userService.getUserByEmail(email).orElse(null);
            request.setAttribute("user", user);
            chain.doFilter(request, response);

        }catch (Exception e) {
            System.out.println(e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getOutputStream().write("{\"message\": \"Invalid bearer token or token missing in header. \"}".getBytes());
        }
    }
}
