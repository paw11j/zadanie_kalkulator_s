package pl.earnings.calculator.control.exchange.validator;

import org.junit.Test;

import java.math.BigDecimal;

public class AmountValidatorTest {

    private final AmountValidator validator = new AmountValidator();

    @Test
    public void shouldValidateWithoutThrowing_whenAmountFormatIsCorrect() {
        validator.validate(new BigDecimal("100.00"));
    }

    @Test(expected = InvalidAmountException.class)
    public void shouldThrow_whenAmountHasTooManyZeros() {
        validator.validate(new BigDecimal("100.000"));
    }

    @Test(expected = InvalidAmountException.class)
    public void shouldThrow_whenAmountHasNotEnoughZeros() {
        validator.validate(new BigDecimal("100.0"));
    }

}