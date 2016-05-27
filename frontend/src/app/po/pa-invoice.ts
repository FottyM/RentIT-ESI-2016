import {Component} from 'angular2/core';

import {Http} from 'angular2/http';


import {Invoice} from "../orders/purchase-order-listing.component.ts";
import {PurchaseOrder} from "./declarations";
import {RentitService} from "./rentit-service";
import {rentit} from "../Configuration";




@Component({

  templateUrl: '/app/po/pa-invoice-list.html'
})
export class PAIDListingComponent {
    orders: PurchaseOrder[];
    constructor (public http:Http,public rentitService:RentitService) {
        this.http.get(rentit+"/api/rentit/invoice/orders/paid")
            .subscribe(resp => this.orders = resp.json());
    }
    follow(invoice: Invoice) {

        this.rentitService.executeQuery(invoice);
    }
}
