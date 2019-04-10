package com.mdem.costms.util;

import com.mdem.costms.model.User;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserUtil {

    public static void userValidate(User user, BindingResult bindingResult, String userPasswordPattern) throws MethodArgumentNotValidException {
        String validationPattern = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}$";
        Pattern pattern = Pattern.compile(validationPattern);
        Matcher matcher = pattern.matcher(user.getPassword());
        if (!matcher.matches()) {
            FieldError error = new FieldError("user", "password", userPasswordPattern);
            bindingResult.addError(error);
            throw new MethodArgumentNotValidException(null, bindingResult);
        }
    }
}
