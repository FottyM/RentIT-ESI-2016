import {Component} from 'angular2/core';

import {Http} from 'angular2/http';


import {Invoice} from "../orders/purchase-order-listing.component.ts";
import {PurchaseOrder} from "./declarations";
import {RentitService} from "./rentit-service";




@Component({

  templateUrl: '/app/po/po-invoice-list.html'
})
export class PHRListingComponent {
    orders: PurchaseOrder[];
    constructor (public http:Http,public rentitService:RentitService) {
        this.http.get("http://localhost:8090/api/rentit/invoice/orders")
            .subscribe(resp => this.orders = resp.json());
    }
    follow(invoice: Invoice) {
        console.log(invoice);
        this.rentitService.executeQuery(invoice);
    }
}
