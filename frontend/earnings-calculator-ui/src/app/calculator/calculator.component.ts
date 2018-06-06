import {Component, OnInit} from '@angular/core';
import {CalculatorService} from "./calculator.service";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {amountFormatValidator} from "./amount.validator";

@Component({
  selector: 'app-calculator',
  templateUrl: './calculator.component.html',
  styleUrls: ['./calculator.component.css']
})
export class CalculatorComponent implements OnInit {
  private calculatorService: CalculatorService;

  constructor(calculatorService: CalculatorService) {
    this.calculatorService = calculatorService;
  }

  private netEarningsAmount: string;
  private dailyGrossEarnings: string;
  private countryCode: string;
  private countryCodes: string[] = ["DE", "UK", "PL"];
  private error: boolean = false;

  private form: FormGroup;

  ngOnInit() {
    this.form = new FormGroup({
      earnings: new FormControl(this.dailyGrossEarnings, [
        Validators.required,
        Validators.min(0),
        amountFormatValidator()
      ]),
      code: new FormControl(this.countryCode, [
        Validators.required
      ])
    });
    this.form.valueChanges.
    subscribe(_ => {
      this.error = false;
    });
  }

  calculate() {
    console.log(`Daily earnings: '${this.dailyGrossEarnings}' and country code: '${this.countryCode}'`);
    this.calculatorService.calculate(this.dailyGrossEarnings, this.countryCode).subscribe(value => {
      this.netEarningsAmount = value.amount;
      this.error = false;
    }, _ => {
      this.netEarningsAmount = "";
      this.error = true;
    })
  }

}
