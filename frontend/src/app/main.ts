/// <reference path="../../node_modules/angular2/typings/browser.d.ts" />

import {bootstrap} from 'angular2/platform/browser';
import {Component} from 'angular2/core';
import {HTTP_BINDINGS} from 'angular2/http';
import {RouteConfig, Route, ROUTER_PROVIDERS, ROUTER_DIRECTIVES} from 'angular2/router';

import {POListingComponent} from './orders/purchase-order-listing.component';
import {PHRWizardComponent} from './phr/phr-wizard.component';
import {PlantCatalogService} from './phr/catalog.service';
import {ProcurementService} from './phr/procurement.service';
import {PHRListingComponent} from "./phr/phr-requests.component";


@Component({
  selector: 'app',
  directives: [ROUTER_DIRECTIVES],
  template: `
    <nav>
      <a [routerLink]="['PHRWizard']">Create PHR</a>
       <a [routerLink]="['PHRListing']">List all PHR's</a>
      <a [routerLink]="['POListing']">List all POs</a>
      
    </nav>
    <h1> WELCOME TO BUILD IT</h1>
    
    <router-outlet></router-outlet>
  `
})
@RouteConfig([
  new Route({path: '/wizard', name: 'PHRWizard', component: PHRWizardComponent}),
  new Route({path: '/orders', name: 'POListing', component: POListingComponent}),
  new Route({path: '/phrs', name: 'PHRListing', component: PHRListingComponent}),
])
export class AppComponent {    
}

bootstrap(AppComponent, [HTTP_BINDINGS, ROUTER_PROVIDERS, PlantCatalogService, ProcurementService]);

