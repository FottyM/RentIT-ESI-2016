import {Component, Input} from 'angular2/core';

import {PlantHireRequest} from '../declarations';
import {ProcurementService} from "../procurement.service";

@Component({
    selector: 'overview-component',
  templateUrl: '/app/phr/overview/overview.html'
})
export class OverviewComponent {
    @Input() phr: PlantHireRequest;
    constructor(public procumentService:ProcurementService) {}
    createPHR() {

        // $.post( "http://127.0.0.1:3000/api/phrs/", function( data ) {
        //     var x = JSON.parse(JSON.stringify(data));
        //     window.location = x["url"];
        // });

     this.procumentService.executePlantHireRequest()
       

       
    }
    
    
    
}
