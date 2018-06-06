package pl.earnings.calculator.control.net;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import pl.earnings.calculator.control.exchange.CurrencyToPLNConverter;
import pl.earnings.calculator.entity.CountryCode;
import pl.earnings.calculator.entity.CurrencyCode;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@RunWith(MockitoJUnitRunner.class)
public class NetEarningsCalculatorStrategyProviderTest {

    @Mock
    private NetEarningsCalculator calculator;

    @Mock
    private CurrencyToPLNConverter converter;

    @InjectMocks
    private NetEarningsCalculatorStrategyProvider provider;

    @Test
    public void shouldCallUKStrategy_whenUKCodeUsed() {
        Mockito.when(calculator.calculate(new BigDecimal("100.00"), new BigDecimal("0.25"), new BigDecimal(600))).thenReturn(new BigDecimal("2000.00"));
        Mockito.when(converter.convert(CurrencyCode.GBP, new BigDecimal("2000.00"))).thenReturn(new BigDecimal("4000.00"));

        BigDecimal result = provider.provide(CountryCode.UK).calculate(new BigDecimal("100.00"));
        Assert.assertNotNull(result);
        Mockito.verify(calculator, Mockito.times(1)).calculate(any(), any(), any());
        Mockito.verify(converter, Mockito.times(1)).convert(eq(CurrencyCode.GBP), any());
    }

    @Test
    public void shouldCallDEStrategy_whenDECodeUsed() {
        Mockito.when(calculator.calculate(new BigDecimal("100.00"), new BigDecimal("0.20"), new BigDecimal(800))).thenReturn(new BigDecimal("2500.00"));
        Mockito.when(converter.convert(CurrencyCode.EUR, new BigDecimal("2500.00"))).thenReturn(new BigDecimal("5000.00"));

        BigDecimal result = provider.provide(CountryCode.DE).calculate(new BigDecimal("100.00"));
        Assert.assertNotNull(result);
        Mockito.verify(calculator, Mockito.times(1)).calculate(any(), any(), any());
        Mockito.verify(converter, Mockito.times(1)).convert(eq(CurrencyCode.EUR), any());
    }

    @Test
    public void shouldCallPLStrategy_whenPLCodeUsed() {
        Mockito.when(calculator.calculate(new BigDecimal("50.00"), new BigDecimal("0.19"), new BigDecimal(1200))).thenReturn(new BigDecimal("800.00"));
        BigDecimal result = provider.provide(CountryCode.PL).calculate(new BigDecimal("50.00"));
        Assert.assertNotNull(result);
        Mockito.verify(calculator, Mockito.times(1)).calculate(any(), any(), any());
        Mockito.verify(converter, Mockito.never()).convert(any(), any());
    }
}