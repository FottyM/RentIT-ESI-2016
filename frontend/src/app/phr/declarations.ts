import {XLink} from "../orders/purchase-order-listing.component";
export class Plant {
    name: string;
    description: string;
    price: number;
}

export class Query {
    name: string;
    startDate: Date;
    endDate: Date;
}

export class RentalPeriod {
    startDate: Date;
    endDate: Date;
}

export class PlantHireRequest {
    plant: Plant;
    rentalPeriod: RentalPeriod;
    status: string;
    total: number;   
}
export class PurchaseOrder {
    plant: Plant;
    rentalPeriod: RentalPeriod;
    status: string;
    total: number;
    _xlinks: XLink[];
}