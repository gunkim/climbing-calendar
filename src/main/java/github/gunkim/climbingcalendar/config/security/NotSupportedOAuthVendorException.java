package github.gunkim.climbingcalendar.config.security;

public class NotSupportedOAuthVendorException extends RuntimeException {
    public NotSupportedOAuthVendorException(String message) {
        super(message);
    }
}
