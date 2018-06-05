package grandeveloper.cuspmx;

//BY: Gutierrez Merida Cristhian David

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//By: Gutierrez Merida Cristhian David


public class VerificarEmail {
    boolean isValid;
    String expression;
    CharSequence inputStr;
    Pattern pattern;
    Matcher matcher;
    String email;

    public VerificarEmail(String email1) {
        this.isValid = isValid;
        this.expression = expression;
        this.inputStr = inputStr;
        this.pattern = pattern;
        this.matcher = matcher;
        this.email = email1;
    }

    public boolean verifica(){
        isValid = false;

        expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        inputStr = email;

        pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }
}

