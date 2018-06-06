package pl.earnings.calculator.boundary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.earnings.calculator.control.NetEarningsCalculatorService;
import pl.earnings.calculator.entity.CountryCode;
import pl.earnings.calculator.entity.NetEarningAmount;

import java.math.BigDecimal;

@CrossOrigin("*")
@RestController
@RequestMapping("/earnings")
public class NetEarningsCalculatorController {

    private final NetEarningsCalculatorService netEarningsCalculatorService;

    @Autowired
    public NetEarningsCalculatorController(NetEarningsCalculatorService netEarningsCalculatorService) {
        this.netEarningsCalculatorService = netEarningsCalculatorService;
    }

    @GetMapping
    public NetEarningAmount calculateNetEarningAmount(@RequestParam("dailyGrossEarnings") BigDecimal dailyGrossEarnings,
                                                      @RequestParam("countryCode") CountryCode countryCode) {
        return netEarningsCalculatorService.calculateNetEarningAsPLN(dailyGrossEarnings, countryCode);
    }
}
