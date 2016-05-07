import {Component} from 'angular2/core';

import {Http} from 'angular2/http';
import {PlantCatalogService} from "../phr/catalog.service";
class Plant {
    name: string;
    description: string;
    price: number;
}

class RentalPeriod {
    startDate: Date;
    endDate: Date;
}

export class XLink {
    _rel: string;
    href: string;
    method: string;
}

class PurchaseOrder {
    plant: Plant;
    rentalPeriod: RentalPeriod;
    status: string;
    total: number;
    _xlinks: XLink[];
}

@Component({
  templateUrl: '/app/orders/list.html'
})
export class POListingComponent {
    orders: PurchaseOrder[];
    constructor (public http:Http,public catalog: PlantCatalogService) {
        this.http.get("http://192.168.99.100:3000/api/sales/orders")
            .subscribe(resp => this.orders = resp.json());
    }
    follow(link: XLink) {

        this.catalog.executeExtensionQuery(link)

       
    }
}
