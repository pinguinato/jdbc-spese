package it.gianotto.spese.exception;

import java.io.Serial;

public class DatabaseException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;
    public DatabaseException() {}

    public DatabaseException(String message, Throwable cause, boolean enableSuppression, boolean writeableStack) {
        super(message, cause, enableSuppression, writeableStack);
    }

    public DatabaseException(String message) {
        super(message);
    }

    public DatabaseException(Throwable cause) {
        super(cause);
    }
}
