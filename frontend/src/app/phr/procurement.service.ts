import {Injectable} from 'angular2/core';
import {Http} from 'angular2/http';

import moment from 'moment';

import {Plant, Query, PlantHireRequest, RentalPeriod} from './declarations';
import {XLink} from "../orders/purchase-order-listing.component";

@Injectable()
export class ProcurementService {
    phr: PlantHireRequest = new PlantHireRequest();
    constructor(public http: Http) {
    }
    setPlant(plant: Plant, query: Query) {
        this.phr.plant = plant;
        this.phr.rentalPeriod = new RentalPeriod();
        this.phr.rentalPeriod.startDate = query.startDate;
        this.phr.rentalPeriod.endDate = query.endDate;
        this.phr.total = (moment(query.endDate).diff(moment(query.startDate), 'days') + 1) * plant.price;
    }

    returnedStatus: String;

    executeAcceptQuery(url:XLink){

        if(url.method=="POST"){
            this.http.post(url.href,null)
                .subscribe(response => {
                        var x = JSON.parse(JSON.stringify(response));
                        var xx = JSON.parse(x["_body"]);
                        if(xx["status"] == "accepted"){
                            var elemId = url.href.split('/')[5];
                            $("#"+elemId).css("background-color","lightgreen")
                            this.returnedStatus =  "accepted";
                        }

                    },
                    error => {
                        alert("Error Some thing went wrong");

                    });
        }else if(url.method=="DELETE"){
            this.http.delete(url.href)
                .subscribe(response => {
                    var x = JSON.parse(JSON.stringify(response));
                    var xx = JSON.parse(x["_body"]);
                    if(xx["status"] == "deleted"){
                        var elemId = url.href.split('/')[5];
                        $("#"+elemId).remove();
                        this.returnedStatus =  "rejected";
                    }

                });
        }
    }

    executePlantHireRequest(){

      this.http.post("http://192.168.99.100:3000/api/phrs/",JSON.stringify({plant:this.phr.plant,rentalPeriod:this.phr.rentalPeriod}))
            .subscribe(response => {

                    if(response.status==201)
                    {

                        window.location ="/phrs";
                       
                    }
                else {

                    }


                },
                error => {
                    alert("Sorry something went wrong")

                });

    }
}