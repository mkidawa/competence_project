package pl.awjkmkkk.domain.exception;

public class ObjectNotFound extends Exception {

    /*------------------------ FIELDS REGION ------------------------*/

    /*------------------------ METHODS REGION ------------------------*/
    public ObjectNotFound() {
    }

    public ObjectNotFound(String message) {
        super(message);
    }

    public ObjectNotFound(Throwable cause) {
        super(cause);
    }
}
