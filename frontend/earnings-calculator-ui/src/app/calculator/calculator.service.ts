import {Injectable} from '@angular/core';
import {Observable} from "rxjs/Observable";
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {environment} from "../../environments/environment";

@Injectable()
export class CalculatorService {

  constructor(private httpClient: HttpClient) {
  }

  public calculate(dailyGrossEarnings: string, countryCode: string): Observable<any> {
    let headers = new HttpHeaders({'Content-type': 'application/json'});
    let params = new HttpParams()
      .set("dailyGrossEarnings", dailyGrossEarnings)
      .set("countryCode", countryCode);

    return this.httpClient.get(`${environment.BASE_URL}/earnings`, {
      headers: headers,
      params: params
    });
  }

}
