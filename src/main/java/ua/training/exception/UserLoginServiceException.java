package ua.training.exception;

/**
 * Created by Adrien on 02.03.2017.
 */
public class UserLoginServiceException extends RuntimeException {
    public UserLoginServiceException(String message){
        super(message);
    }
}
