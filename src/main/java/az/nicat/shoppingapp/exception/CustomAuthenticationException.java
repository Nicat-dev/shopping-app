package az.nicat.shoppingapp.exception;

public class CustomAuthenticationException extends RuntimeException {
    public CustomAuthenticationException(String explanation,Throwable throwable) {
        super(explanation,throwable);
    }

    public CustomAuthenticationException(String message) {
        super(message);
    }
}
