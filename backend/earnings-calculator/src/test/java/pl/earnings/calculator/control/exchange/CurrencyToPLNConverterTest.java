package pl.earnings.calculator.control.exchange;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import pl.earnings.calculator.entity.CurrencyCode;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.eq;

@RunWith(MockitoJUnitRunner.class)
public class CurrencyToPLNConverterTest {

    @Mock
    private ExchangeRateService exchangeRateService;

    @InjectMocks
    private CurrencyToPLNConverter converter;

    @Test
    public void shouldConvertGBPToPLN() {
        Mockito.when(exchangeRateService.getCurrentExchangeRate(CurrencyCode.GBP)).thenReturn(new BigDecimal("4.8913"));
        BigDecimal result = converter.convert(CurrencyCode.GBP, new BigDecimal(100000));
        Assert.assertEquals(result, new BigDecimal("489130.0000"));
        Mockito.verify(exchangeRateService, Mockito.times(1)).getCurrentExchangeRate(eq(CurrencyCode.GBP));
    }

    @Test
    public void shouldConvertEURToPLN() {
        Mockito.when(exchangeRateService.getCurrentExchangeRate(CurrencyCode.EUR)).thenReturn(new BigDecimal("4.2804"));
        BigDecimal result = converter.convert(CurrencyCode.EUR, new BigDecimal(100000));
        Assert.assertEquals(result, new BigDecimal("428040.0000"));
        Mockito.verify(exchangeRateService, Mockito.times(1)).getCurrentExchangeRate(eq(CurrencyCode.EUR));
    }
}