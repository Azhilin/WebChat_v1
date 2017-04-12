package ua.training.validation;

import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpSession;

/**
 * Created by Adrien on 07.03.2017.
 */
public class Validator {
    public static boolean registration(BindingResult bindingResult, HttpSession session){
        if(bindingResult.getAllErrors().size() != 0) {
            session.setAttribute("error", bindingResult.getAllErrors()
                    .get(0).getDefaultMessage());
            return true;
        }
        return false;
    }
}
