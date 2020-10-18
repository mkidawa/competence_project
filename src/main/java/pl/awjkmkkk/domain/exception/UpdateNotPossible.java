package pl.awjkmkkk.domain.exception;

public class UpdateNotPossible extends Exception {

    /*------------------------ FIELDS REGION ------------------------*/

    /*------------------------ METHODS REGION ------------------------*/
    public UpdateNotPossible() {
    }

    public UpdateNotPossible(String message) {
        super(message);
    }

    public UpdateNotPossible(Throwable cause) {
        super(cause);
    }
}
