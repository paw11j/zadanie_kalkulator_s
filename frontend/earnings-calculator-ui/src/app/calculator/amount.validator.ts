import {AbstractControl, Validator, ValidatorFn} from "@angular/forms";

export function amountFormatValidator(): ValidatorFn {
  return (control: AbstractControl): {[key: string]: any} | null => {
    return (!(/^\d+\.\d{2}$/.test(control.value))) ? {'amountFormat': {value: control.value}} : null;
  };
}
