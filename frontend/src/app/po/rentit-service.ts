import {Injectable} from 'angular2/core';
import {Http, Headers, RequestOptions} from 'angular2/http';
import {Observable} from 'rxjs/Rx';

import {Plant, Query} from './declarations';
import {XLink, Invoice} from "../orders/purchase-order-listing.component";
import {rentit} from "../Configuration";

@Injectable()
export class RentitService {
    plants: Plant[] = [];
    extension= "";
    // request = require('./ajax-request');
    constructor(public http: Http) {}

    executeQuery(invoice: Invoice) {
        var headers = new Headers();
        headers.append('Content-type', 'application/json');

        this.http.post(rentit+"/api/rentit/invoice/sendInvoice/", JSON.stringify({"poId":invoice.poId,"email":invoice.email,"total":invoice.total}), new RequestOptions({headers: headers}))
            .subscribe(response => {

                 alert("success invoice sent")
                },
                error => {
                    alert("error")


                });
    }

}
