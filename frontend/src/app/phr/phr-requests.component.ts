import {Component} from 'angular2/core';

import {Http} from 'angular2/http';


import {XLink} from "../orders/purchase-order-listing.component.ts";
import {PurchaseOrder} from "./declarations";
import {ProcurementService} from "./procurement.service";



@Component({

  templateUrl: '/app/phr/phr-requests-list.html'
})
export class PHRListingComponent {
    orders: PurchaseOrder[];
    constructor (public http:Http, public catalog: ProcurementService) {
        this.http.get("http://192.168.99.100:3000/api/sales/phrs")
            .subscribe(resp => this.orders = resp.json());
    }
    follow(link: XLink) {

        this.catalog.executeAcceptQuery(link);
    }
}
