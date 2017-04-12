package ua.training.exception;

/**
 * Created by Adrien on 28.03.2017.
 */
public class BanUserException extends RuntimeException {
    public BanUserException(String message) {
        super(message);
    }
}
