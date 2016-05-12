import {Injectable} from 'angular2/core';
import {Http, Headers, RequestOptions} from 'angular2/http';
import {Observable} from 'rxjs/Rx';

import {Plant, Query} from './declarations';
import {XLink} from "../orders/purchase-order-listing.component";

@Injectable()
export class PlantCatalogService {
    plants: Plant[] = [];
    extension= "";
    // request = require('./ajax-request');
    constructor(public http: Http) {}
    
    executeQuery(query: Query) {
        this.http.get('http://192.168.99.100:3000/api/inventory/plants')
            .subscribe(response => this.plants = response.json());
    }
    executeExtensionQuery(url:XLink){

        if(url.method=="POST"){

        if (url._rel=="DISPATCHED"||url._rel=="DELIVERED"||url._rel=="PLANT_REJECTED"){
            var headers = new Headers();
            headers.append('Content-type', 'application/json');

            this.http.post(url.href, JSON.stringify(url._rel), new RequestOptions({headers: headers}))
                .subscribe(response => {

                        var x = response.json();
                        this.extension = x.status.response;

                    },
                    error => {
                        this.extension = "Rejected";


                    });

        }
else{
            this.http.post(url.href,null)
                .subscribe(response => {

                        var x = response.json();
                        this.extension = x.status.response;

                    },
                    error => {
                        this.extension = "Rejected";




                    });
        }







        }
        else if(url.method=="DELETE"){


               this.http.delete(url.href)
                .subscribe(response =>{
                    var x = response.json();
                    this.extension = x.status.response;
                });
        }
        else if(url.method=="GET"){


            this.http.get(url.href)
                .subscribe(response =>{
                    var x = response.json();
                    this.extension = x.status.response;
                });
        }



    }
}
