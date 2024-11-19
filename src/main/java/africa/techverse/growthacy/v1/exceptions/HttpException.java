package africa.techverse.growthacy.v1.exceptions;

public class HttpException extends RuntimeException {
    private final int errorCode;

    public HttpException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public Integer getErrorCode() {
        return errorCode;
    }
}
