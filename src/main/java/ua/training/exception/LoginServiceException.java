package ua.training.exception;

/**
 * Created by Adrien on 28.02.2017.
 */
public class LoginServiceException extends RuntimeException {
    public LoginServiceException(String message){
        super(message);
    }
}
