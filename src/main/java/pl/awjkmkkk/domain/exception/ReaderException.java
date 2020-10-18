package pl.awjkmkkk.domain.exception;

import java.io.IOException;

public class ReaderException extends IOException {

    /*------------------------ FIELDS REGION ------------------------*/

    /*------------------------ METHODS REGION ------------------------*/
    public ReaderException() {
    }

    public ReaderException(String message) {
        super(message);
    }

    public ReaderException(Throwable cause) {
        super(cause);
    }
}
