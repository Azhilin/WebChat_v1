package ua.training.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ua.training.dto.ResponseBanDTO;
import ua.training.exception.BanUserException;
import ua.training.exception.LoginServiceException;
import ua.training.exception.UserLoginServiceException;

import javax.servlet.http.HttpSession;

/**
 * Created by Adrien on 28.02.2017.
 */
@ControllerAdvice
public class ExceptionHandlerAdvice {
    @ExceptionHandler(LoginServiceException.class)
    public String loginServiceExceptionHandler(LoginServiceException e,
                                               HttpSession session){
        session.setAttribute("error", e.getMessage());
        return "redirect:registration";
    }
    @ExceptionHandler(UserLoginServiceException.class)
    public String verificationUserExceptionHandler(UserLoginServiceException e,
                                                   HttpSession session){
        session.setAttribute("error", e.getMessage());
        return "redirect:login";
    }
    @ExceptionHandler(BanUserException.class)
    public ResponseEntity<ResponseBanDTO> banUserExceptionHandler(BanUserException e){
        ResponseBanDTO responseBanDTO = new ResponseBanDTO();
        responseBanDTO.setAuth("yes");
        responseBanDTO.setSuccess("no");
        return new ResponseEntity<ResponseBanDTO>(responseBanDTO,
                HttpStatus.OK);
    }

    @ExceptionHandler(Exception.class)
    public void exceptionHandler(Exception e){
        e.printStackTrace();
    }
}
