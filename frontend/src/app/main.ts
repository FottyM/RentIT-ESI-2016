/// <reference path="../../node_modules/angular2/typings/browser.d.ts" />

import {bootstrap} from 'angular2/platform/browser';
import {Component} from 'angular2/core';
import {HTTP_BINDINGS} from 'angular2/http';
import {RouteConfig, Route, ROUTER_PROVIDERS, ROUTER_DIRECTIVES} from 'angular2/router';

import {POListingComponent} from './orders/purchase-order-listing.component';
import {RentitService} from './po/rentit-service';
import {PlantCatalogService} from './po/catalog.service';

import {PHRListingComponent} from "./po/po-invoice";
import {PAIDListingComponent} from "./po/pa-invoice";

@Component({
  selector: 'app',
  directives: [ROUTER_DIRECTIVES],
  template: `
    
      
    
    <div id="sb-site">


    
    <div id="page-wrapper" >

        <div id="page-sidebar" style="height: 100%">
            <div id="header-logo" class="logo-bg"><a href="index-2.html" class="logo-content-big" title="DelightUI">Rentit </a>
            </div>


            <!--    header-->



            <div class="scroll-sidebar">
                <ul id="sidebar-menu">
                    <li class="header"><span>Menu Items</span>
                    </li>
                     <li>   <a [routerLink]="['POListing']"><i class="glyph-icon icon-linecons-diamond"></i> <span>Purchase Orders</span></a></li>
                    <li>   <a [routerLink]="['PInvoice']"><i class="glyph-icon icon-linecons-diamond"></i> <span>Invoice</span></a></li>
                    <li>   <a [routerLink]="['PAInvoice']"><i class="glyph-icon icon-linecons-diamond"></i> <span>Paid Invoice</span></a></li>
                     
                    




                </ul>
            </div>
        </div>



        <!--   body  -->
        <div id="page-content-wrapper">
            <div id="page-content"  style="margin-left: 10%">


    
  
    

                <div id="page-title">
           
          <nav>
      
           
    </nav>
    <h1> WELCOME TO RENTIT </h1>
    
    <router-outlet></router-outlet>
                </div>

            </div>
        </div>
    </div>
   
</div>
  `
})
@RouteConfig([

  new Route({path: '/orders', name: 'POListing', component: POListingComponent}),
  new Route({path: '/invoices', name: 'PInvoice', component: PHRListingComponent}),
  new Route({path: '/invoicespaid', name: 'PAInvoice', component: PAIDListingComponent})
])
export class AppComponent {

  
}

bootstrap(AppComponent, [HTTP_BINDINGS, ROUTER_PROVIDERS, PlantCatalogService,RentitService]);

