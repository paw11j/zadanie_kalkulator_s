package pl.earnings.calculator.control.exchange.validator;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class AmountValidator {

    private Pattern pattern;

    public AmountValidator() {
        pattern = Pattern.compile("^\\d*\\.?\\d{2}$");
    }

    public void validate(BigDecimal amount) {
        String input = amount.toPlainString();
        Matcher matcher = pattern.matcher(input);
        if (!matcher.matches()) {
            throw new InvalidAmountException("Amount must be in format XXX.00");
        }
        if (!(amount.compareTo(BigDecimal.ZERO) > 0)) {
            throw new InvalidAmountException("Amount must be greater than 0");
        }
    }
}
