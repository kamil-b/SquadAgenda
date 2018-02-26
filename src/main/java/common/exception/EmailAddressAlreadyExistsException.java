package common.exception;

public class EmailAddressAlreadyExistsException extends RuntimeException {

    public EmailAddressAlreadyExistsException() {
        super("Email address already exists in database");
    }
}
