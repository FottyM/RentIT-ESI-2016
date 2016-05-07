import {Injectable} from 'angular2/core';
import {Http} from 'angular2/http';
import {Observable} from 'rxjs/Rx';

import {Plant, Query} from './declarations';
import {XLink} from "../orders/purchase-order-listing.component";

@Injectable()
export class PlantCatalogService {
    plants: Plant[] = [];
    extension= "";
    constructor(public http: Http) {}
    
    executeQuery(query: Query) {
        this.http.get('http://192.168.99.100:3000/api/inventory/plants')
            .subscribe(response => this.plants = response.json());
    }
    executeExtensionQuery(url:XLink){

        if(url.method=="POST"){
            this.http.post(url.href,null)
                .subscribe(response => {

                   var x = response.json();
                    this.extension = x.status.response;

                },
                error => {
                    this.extension = "Rejected";




                });
        }
        else{


               this.http.delete(url.href)
                .subscribe(response =>{
                    var x = response.json();
                    this.extension = x.status.response;
                });
        }



    }
}
