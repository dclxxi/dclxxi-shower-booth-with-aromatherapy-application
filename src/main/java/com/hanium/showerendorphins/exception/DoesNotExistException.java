package com.hanium.showerendorphins.exception;

public class DoesNotExistException extends RuntimeException {
    public DoesNotExistException() {
        super("찾는 데이터가 존재하지 않습니다.");
    }

    public DoesNotExistException(String message) {
        super(message);
    }

    public DoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public DoesNotExistException(Throwable cause) {
        super(cause);

    }
}
