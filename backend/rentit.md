FORMAT: 1A
HOST: http://localhost:8090

# ESI Rentit-API
RentIT API

# API
Notes related resources of the **Plants API**

## Plant Catalog [/api/inventory/plants{?name,startDate,endDate}]
### Retrieve All Plants [GET]
+ Parameters
    + name (optional,string) ... Name of the plant
    + startDate (optional,date) ... Starting date for rental
    + endDate (optional,date) ... End date for rental

+ Response 200 (application/json)

        [
            {
                "name":"D-Truck",
                "description":"15 Tonne Articulating Dump Truck",
                "price":250.00,
                "_links":{
                    "self":{
                        "href":"http://localhost:8090/api/inventory/plants/13"
                    }
                }
            },
            {
                "name":"D-Truck",
                "description":"30 Tonne Articulating Dump Truck",
                "price":300.00,
                "_links":{
                    "self":{
                        "href":"http://localhost:8090/api/inventory/plants/14"
                    }
                }
            }
        ]
        
## Plant [/api/inventory/plants/{id}]
### Retrieve Plant By id  [GET]
+ Parameters
    + id (required, int) ... Plant ID

+ Response 200 (application/json)

    + Headers

            Location: /api/inventory/plants/14

    + Body

            {
                "name":"D-Truck",
                "description":"30 Tonne Articulating Dump Truck",
                "price":300.00,
                "_links": {
                    "self": {
                        "href":"http://localhost:8090/api/inventory/plants/14"
                    }
                }
            }

## Purchase Order - Create [/api/sales/orders]
### Create Purchase Order [POST]
+ Request (application/json)

        {
            "plant":{
                "_links":{
                    "self":{
                        "href":"http://localhost:8090/api/inventory/plants/13"
                    }
                }
            },
            "rentalPeriod": {
                "startDate":"2016-11-22", 
                "endDate":"2016-11-25"
            }
        }

+ Response 200 (application/json)

    + Headers

            Location: /api/sales/orders/100

    + Body

            {
                "plant": {
                    "name": "D-Truck",
                    "description": "15 Tonne Articulating Dump Truck",
                    "price": 250,
                    "_links": {
                        "self": {"href": "http://localhost:8090/api/inventory/plants/13"}
                    }
                },
                "rentalPeriod": {
                    "startDate": "2016-11-22", 
                    "endDate": "2016-11-25"
                },
                "status": "PENDING",
                "total": 750,
                "_links": {
                    "self": {
                        "href": "http://localhost:8090/api/sales/orders/100"
                    }
                },
                "_xlinks": [
                    {
                        "_rel": "accept",
                        "href": "http://localhost:8090/api/sales/orders/100/accept",
                        "method": "POST"
                    },
                    {
                        "_rel": "reject",
                        "href": "http://localhost:8090/api/sales/orders/100/accept",
                        "method": "DELETE"    
                    }
                ]
            }

### Resubmit Purchase Order [PUT]
+ Request (application/json)

        {
            "plant":{
                "_links":{"self":{"href":"http://localhost:8090/api/inventory/plants/13"}}
            },
            "rentalPeriod": {"startDate":"2016-11-12", "endDate":"2016-11-14"}
        }

+ Response 201 (application/json)

    + Headers

            Location: http://192.168.99.100:3000/api/sales/orders/100

    + Body

            {
                "plant": {
                    "name": "D-Truck",
                    "description": "15 Tonne Articulating Dump Truck",
                    "price": 250,
                    "_links": {
                        "self": {"href": "http://192.168.99.100:3000/api/inventory/plants/13"}
                    }
                },
                "rentalPeriod": {"startDate": "2016-11-12", "endDate": "2016-11-14"},
                "status": "OPEN",
                "total": 750,
                "_links": {
                    "self": {"href": "http://192.168.99.100:3000/api/sales/orders/100"}
                },
                "_xlinks": [
                    {
                        "_rel": "extend",
                        "href": "http://192.168.99.100:3000/api/sales/orders/100/extensions",
                        "method": "POST"
                    }
                ]
            }



## Purchase Order - Cancel [/api/sales/orders/{id}/cancel]
### Cancel Purchase Order [DELETE]
+ Parameters
    + id (required, int) ... Purchase Order ID

+ Response 200 (application/json)

    + Headers

            Location: /api/sales/orders/100

    + Body

            {
                "plant": {
                    "name": "D-Truck",
                    "description": "15 Tonne Articulating Dump Truck",
                    "price": 250,
                    "_links": {
                        "self": {"href": "http://localhost:8090/api/inventory/plants/13"}
                    }
                },
                "rentalPeriod": {"startDate": "2016-11-22", "endDate": "2016-11-25"},
                "status": "CANCELLED",
                "total": 750,
                "_links": {
                    "self": {"href": "http://localhost:8090/api/sales/orders/100"}
                }
            }
            
+ Response 409 (application/json)

    + Body
    
            {"Purchase Order Cancellation Failed."}
            
## Purchase Order Extension - Create [/api/sales/orders/{id}/extensions]
### Create Purchase Order Extension [POST]
+ Parameters
    + id (required, int) ... Purchase Order ID
    
+ Request (application/json)

       {
            "id":100,
            "rentalPeriod": {
                "startDate":"2016-11-22", 
                "endDate":"2016-11-25"
            }
        }
        
+ Response 201 (application/json)

    + Headers

            Location: /api/sales/orders/100

    + Body

            {
                "plant": {
                    "name": "D-Truck",
                    "description": "15 Tonne Articulating Dump Truck",
                    "price": 250,
                    "_links": {
                        "self": {"href": "http://localhost:8090/api/inventory/plants/13"}
                    }
                },
                "rentalPeriod": {"startDate": "2016-11-22", "endDate": "2016-11-29"},
                "status": "PENDING_UPDATE",
                "total": 1750,
                "_links": {
                    "self": {"href": "http://localhost:8090/api/sales/orders/100"}
                },
                "_xlinks": [
                    {
                        "_rel": "acceptRPExtension",
                        "href": "http://localhost:8090/api/sales/orders/100/extensions/1/accept",
                        "method": "POST"
                    },
                    {
                        "_rel": "rejectRPExtension",
                        "href": "http://localhost:8090/api/sales/orders/100/extensions/1/accept",
                        "method": "DELETE"    
                    }
                ]
            }

