import {Component} from 'angular2/core';

import {Http} from 'angular2/http';
import {PlantCatalogService} from "../po/catalog.service";
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
export class Invoice {
    poId: number;
    email: string;
    total: number;
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
        this.http.get("http://localhost:8090/api/sales/rentit/orders")
            .subscribe(resp => this.orders = resp.json());


    }

    cleaner(data){
       //// if(data.)



    }
    follow(link: XLink) {


        console.log(link);
        this.catalog.executeExtensionQuery(link)

       
    }
}
