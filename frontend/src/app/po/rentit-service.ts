import {Injectable} from 'angular2/core';
import {Http, Headers, RequestOptions} from 'angular2/http';
import {Observable} from 'rxjs/Rx';

import {Plant, Query} from './declarations';
import {XLink, Invoice} from "../orders/purchase-order-listing.component";

@Injectable()
export class RentitService {
    plants: Plant[] = [];
    extension= "";
    // request = require('./ajax-request');
    constructor(public http: Http) {}

    executeQuery(invoice: Invoice) {
        var headers = new Headers();
        headers.append('Content-type', 'application/json');

        this.http.post("http://localhost:8090/api/rentit/invoice/sendInvoice/", JSON.stringify({"poId":invoice.poId,"email":invoice.email,"total":invoice.total}), new RequestOptions({headers: headers}))
            .subscribe(response => {
           console.log("yes")

                },
                error => {
                    this.extension = "Rejected";


                });
    }

}
