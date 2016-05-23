/// <reference path="../../node_modules/angular2/typings/browser.d.ts" />

import {bootstrap} from 'angular2/platform/browser';
import {Component} from 'angular2/core';
import {HTTP_BINDINGS} from 'angular2/http';
import {RouteConfig, Route, ROUTER_PROVIDERS, ROUTER_DIRECTIVES} from 'angular2/router';

import {POListingComponent} from './orders/purchase-order-listing.component';
import {RentitService} from './po/rentit-service';
import {PlantCatalogService} from './po/catalog.service';

import {PHRListingComponent} from "./po/po-invoice";


@Component({
  selector: 'app',
  directives: [ROUTER_DIRECTIVES],
  template: `
    <nav>
      
       <a [routerLink]="['PInvoice']">Invoice</a>
      <a [routerLink]="['POListing']">List all POs</a>
      
    </nav>
    <h1> WELCOME TO RENTIT IT</h1>
    
    <router-outlet></router-outlet>
  `
})
@RouteConfig([

  new Route({path: '/orders', name: 'POListing', component: POListingComponent}),
  new Route({path: '/invoices', name: 'PInvoice', component: PHRListingComponent}),
])
export class AppComponent {

  
}

bootstrap(AppComponent, [HTTP_BINDINGS, ROUTER_PROVIDERS, PlantCatalogService,RentitService]);

