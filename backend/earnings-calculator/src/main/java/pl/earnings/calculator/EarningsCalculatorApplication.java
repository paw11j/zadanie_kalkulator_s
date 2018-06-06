package pl.earnings.calculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
public class EarningsCalculatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(EarningsCalculatorApplication.class, args);
    }
}
