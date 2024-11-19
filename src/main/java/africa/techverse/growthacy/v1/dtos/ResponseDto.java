package africa.techverse.growthacy.v1.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto {
    private HttpStatus status = HttpStatus.OK;
    private String message = "SUCCESS";
    private Object data = null;
}
