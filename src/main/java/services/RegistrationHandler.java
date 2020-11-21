package services;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationHandler {
    public Map<String, Object> errors;
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
                    "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private Pattern pattern;
    private Matcher matcher;

    public Map<String,Object> validate(String name, String password, String email, String birth) {
        this.handleName(name);
        this.handlePassword(password);
        this.handleEmail(email);
        this.handleDate(birth);
        return errors;
    }

    private void handleName(String name) {
        if (name.equals("")) {
            errors.put("errorMessage", "Incorrect name");
        }
    }

    private void handlePassword(String password) {
        if (password.length() < 8) {
            errors.put("errorMessage", "The password must contain 8 or more characters");
        }
    }

    private void handleEmail(String email) {
        matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            errors.put("errorMessage", "Incorrect email");
        }
    }

    private void handleDate(String birth) {
        if (birth.equals("")) {
            errors.put("errorMessage", "Date is not entered");
        }
    }

    public RegistrationHandler() {
        errors = new HashMap<>();
        pattern = Pattern.compile(EMAIL_PATTERN);
    }
}
