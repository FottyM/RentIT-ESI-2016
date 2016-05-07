FORMAT: 1A
HOST: http://localhost

# ESI14-RentIt
Excerpt of RentIt's API

# Group Purchase Orders
Notes related resources of the **Plants API**

## Plant Catalog [/api/inventory/plants{?name,startDate,endDate}]
### Retrieve Plants [GET]
+ Parameters
    + name (optional,string) ... Name of the plant
    + startDate (optional,date) ... Starting date for rental
    + endDate (optional,date) ... End date for rental

+ Response 200 (application/json)

        [
            {"_id":13,"name":"D-Truck","description":"15 Tonne Articulating Dump Truck","price":250.00,"_links":{"self":{"href":"http://localhost:8090/api/inventory/plants/13"}}},
            {"_id":14,"name":"D-Truck","description":"30 Tonne Articulating Dump Truck","price":300.00,"_links":{"self":{"href":"http://localhost:8090/api/inventory/plants/14"}}}
        ]

## Purchase Order - Container [/api/sales/orders]
### Create Purchase Order [POST]
+ Request (application/json)

        {
            "plant":{
                "_links":{"self":{"href":"http://192.168.99.100:3000/api/inventory/plants/13"}}
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
                "status": "PENDING",
                "_links": {
                    "self": {"href": "http://192.168.99.100:3000/api/sales/orders/100"}
                },
                "_xlinks": [
                    {
                        "_rel": "accept",
                        "href": "http://192.168.99.100:3000/api/sales/orders/100/accept",
                        "method": "PATCH"
                    },
                    {
                        "_rel": "reject",
                        "href": "http://192.168.99.100:3000/api/sales/orders/100/accept",
                        "method": "DELETE"
                    }
                ]
            }


### Retrieve All Purchase Order [GET]

+ Response 200 (application/json)

        [
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
                    },
                    {
                        "_rel": "close",
                        "href": "http://192.168.99.100:3000/api/sales/orders/100",
                        "method": "DELETE"
                    }
                ]
            },
            {
                            "plant": {
                                "name": "D-Truck2",
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
                                    "href": "http://192.168.99.100:3000/api/sales/orders/101/extensions",
                                    "method": "POST"
                                },
                                {
                                    "_rel": "close",
                                    "href": "http://192.168.99.100:3000/api/sales/orders/101",
                                    "method": "DELETE"
                                }
                            ]
                        }
        ]

## Purchase Order - Instance [/api/sales/orders/{id}]
### Retrieve Purchase Order [GET]
+ Parameters
    + id: 100 (required, number) - Purchase order ID in form of a long integer

+ Response 200 (application/json)

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

+ Response 409 (application/json)

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
            "status": "REJECTED",
            "_links": {
                "self": {"href": "http://192.168.99.100:3000/api/sales/orders/100"}
            },
            "_xlinks": [
                {
                    "_rel": "resubmit",
                    "href": "http://192.168.99.100:3000/api/sales/orders/100",
                    "method": "PUT"
                }
            ]
        }
## Purchase Order Extension - Instance [/api/sales/orders/100/extensions]
### Extend Purchase Order [POST]
+ Response 201 (application/json)

    + Headers

            Location: http://192.168.99.100:3000/api/sales/orders/100

    + Body

            {
                "status": {
                    "response": "Accepted"

                },

                "_xlinks": [
                    {
                        "_rel": "accepted",
                        "href": "http://rentit.com/api/sales/orders/124/extensions/1",
                        "method": "GET"
                    }
                ]
            }
## Purchase Order Extension - Instance [/api/sales/orders/101/extensions]
### Extend Reject Purchase Order [POST]
+ Response 409 (application/json)

    + Headers

            Location: http://192.168.99.100:3000/api/sales/orders/100

    + Body

            {
                "status": {
                    "response": "Rejected"

                },


            }
## Purchase Order Extension - Instance [/api/sales/orders/{id}]
### Extend Purchase Order REJECTION [DELETE]
+ Response 201 (application/json)

    + Headers

            Location: http://192.168.99.100:3000/api/sales/orders/100

    + Body

            {
                "status": {
                    "response": "closed"

                }


            }



## BUILD IT
## Plant Hire Request - Container [/api/phrs/]
### Create Plant Hire Request [POST]
+ Request (application/json)

        {
            "plant":{
                "_links":{"self":{"href":"http://192.168.99.100:3000/api/inventory/plants/13"}}
            },
            "rentalPeriod": {"startDate":"2016-11-12", "endDate":"2016-11-14"}
        }

+ Response 201 (application/json)

    + Headers



    + Body

            {
                 "response": "created"
            }






### Retrieve Purchase Order [GET]
+ Parameters
    + id: 100 (required, number) - Purchase order ID in form of a long integer

+ Response 200 (application/json)

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

+ Response 409 (application/json)

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
            "status": "REJECTED",
            "_links": {
                "self": {"href": "http://192.168.99.100:3000/api/sales/orders/100"}
            },
            "_xlinks": [
                {
                    "_rel": "resubmit",
                    "href": "http://192.168.99.100:3000/api/sales/orders/100",
                    "method": "PUT"
                }
            ]
        }

## Plant Hire Requests - Container [/api/sales/phrs]
### Retrieve Plant Hire Requests [GET]

+ Response 200 (application/json)

        [
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
                        "_rel": "accept",
                        "href": "http:///192.168.99.100:3000/api/phrs/100/",
                        "method": "POST"
                    },
                    {
                        "_rel": "reject",
                        "href": "http:///192.168.99.100:3000/api/phrs/100/",
                        "method": "DELETE"
                    }
                ]
            },
            {
                "plant": {
                    "name": "D-Truck",
                    "description": "16 Tonne Articulating Dump Truck",
                    "price": 250,
                    "_links": {
                        "self": {"href": "http://192.168.99.100:3000/api/inventory/plants/13"}
                    }
                },
                "rentalPeriod": {"startDate": "2016-11-12", "endDate": "2016-11-14"},
                "status": "OPEN",
                "total": 760,
                "_links": {
                    "self": {"href": "http://192.168.99.100:3000/api/sales/orders/101"}
                },
                "_xlinks": [
                    {
                        "_rel": "accept",
                        "href": "http:///192.168.99.100:3000/api/phrs/101/",
                        "method": "POST"
                    },
                    {
                        "_rel": "reject",
                        "href": "http:///192.168.99.100:3000/api/phrs/101/",
                        "method": "DELETE"
                    }
                ]
            },
            {
                "plant": {
                    "name": "D-Truck",
                    "description": "16 Tonne Articulating Dump Truck",
                    "price": 250,
                    "_links": {
                        "self": {"href": "http://192.168.99.100:3000/api/inventory/plants/13"}
                    }
                },
                "rentalPeriod": {"startDate": "2016-11-12", "endDate": "2016-11-14"},
                "status": "OPEN",
                "total": 770,
                "_links": {
                    "self": {"href": "http://192.168.99.100:3000/api/sales/orders/102"}
                },
                "_xlinks": [
                    {
                        "_rel": "accept",
                        "href": "http:///192.168.99.100:3000/api/phrs/102/",
                        "method": "POST"
                    },
                    {
                        "_rel": "reject",
                        "href": "http:///192.168.99.100:3000/api/phrs/102/",
                        "method": "DELETE"
                    }
                ]
            }
        ]
## Plant hire request - Container [/api/phrs/{id}]
+ Parameters
    + id: 100 (required, number) - Purchase order ID in form of a long integer

### Create PHR [POST]
+ Request (application/json)

        {
        }

+ Response 201 (application/json)

    + Headers

            Location: http://192.168.99.100:3000/api/phrs/100

    + Body

            {
                "status": "accepted"
            }

### Delete PHR [DELETE]

 + Response 201 (application/json)

     + Headers

             Location: http://192.168.99.100:3000/api/phrs/100

     + Body

             {
                 "status": "deleted"
             }

