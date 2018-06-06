package pl.earnings.calculator.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class NetEarningAmount {
    private BigDecimal amount;

    public NetEarningAmount() {
    }

    public NetEarningAmount(BigDecimal amount) {
        this.amount = amount.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
