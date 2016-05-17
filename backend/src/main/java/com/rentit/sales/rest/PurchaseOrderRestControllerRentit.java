package com.rentit.sales.rest;

import com.rentit.common.application.dto.BusinessPeriodDTO;
import com.rentit.common.domain.model.BusinessPeriod;
import com.rentit.common.domain.model.UserType;
import com.rentit.inventory.application.dto.PlantInventoryEntryDTO;
import com.rentit.sales.application.dto.InvoiceDTO;
import com.rentit.sales.application.dto.PurchaseOrderDTO;
import com.rentit.sales.application.service.SalesService;
import com.rentit.sales.domain.model.POStatus;
import com.rentit.sales.domain.model.PurchaseOrderID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping("/api/sales/rentit/orders")
@CrossOrigin
public class PurchaseOrderRestControllerRentit {
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    SalesService salesService;
    @RequestMapping(method = GET, path = "")
    public List<PurchaseOrderDTO> getPurchaseOrders(){
        List<PurchaseOrderDTO> purchaseOrderDTO=salesService.findPurcahseOrders(UserType.RENTIT);
        return purchaseOrderDTO;
    }

    @RequestMapping(method = GET, path = "/{id}")
    public PurchaseOrderDTO showPurchaseOrder(@PathVariable Long id) throws Exception {
        PurchaseOrderDTO poDTO = salesService.findPurchaseOrder(PurchaseOrderID.of(id));
        return poDTO;
    }
    @RequestMapping(method = POST, path = "/{id}/accept")
    public PurchaseOrderDTO acceptPurchaseOrder(@PathVariable Long id) throws Exception {

        PurchaseOrderDTO poDTO = salesService.confirmOrRejectPurchaseOrder(POStatus.OPEN,id);
        return poDTO;
    }

    @RequestMapping(method = DELETE, path = "/{id}/accept")
    public PurchaseOrderDTO rejectPurchaseOrder(@PathVariable Long id) throws Exception {

        PurchaseOrderDTO poDTO = salesService.confirmOrRejectPurchaseOrder(POStatus.REJECTED,id);

        return poDTO;
    }

    @RequestMapping(method = DELETE, path = "/{id}")
    public PurchaseOrderDTO deletePurchaseOrder(@PathVariable Long id) throws Exception {
        PurchaseOrderDTO poDTO = salesService.DeletePurchaseOrder(id);
        return poDTO;
    }

    @RequestMapping(method = POST, path = "/{oid}/extensions/{eid}/accept")
    public PurchaseOrderDTO acceptPurchaseOrderExtension(@PathVariable Long oid,@PathVariable Long eid) throws Exception {

        PurchaseOrderDTO poDTO = salesService.confirmOrRejectPurchaseOrderExtension(POStatus.OPEN,oid,eid);
        return poDTO;
    }

    @RequestMapping(method = DELETE, path = "/{oid}/extensions/{eid}/accept")
    public PurchaseOrderDTO rejectPurchaseOrderExtension(@PathVariable Long oid,@PathVariable Long eid) throws Exception {

        PurchaseOrderDTO poDTO = salesService.confirmOrRejectPurchaseOrderExtension(POStatus.REJECTED,oid,eid);

        return poDTO;
    }
    @RequestMapping(method = POST, path = "/{oid}/updatestatus")
    public PurchaseOrderDTO purchaseOrderUpdateStatus(@PathVariable Long oid,@RequestBody POStatus poStatus) throws Exception {

        PurchaseOrderDTO poDTO = salesService.updatePurchaseOrder(oid,poStatus);

        return poDTO;
    }
    @RequestMapping(method = GET, path = "/{oid}/cancel")
    public PurchaseOrderDTO purchaseOrderCancel(@PathVariable Long oid) throws Exception {

        PurchaseOrderDTO poDTO = salesService.cancelPurchaseOrder(oid);
        return poDTO;
    }


    @RequestMapping(method = GET, path = "/call")
    public String p() throws Exception {

        InvoiceDTO[] plants = restTemplate.getForObject(
                "http://localhost:8080/api/invoice/",
                InvoiceDTO[].class);
        return Arrays.asList(plants).toString();

    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    public String bindExceptionHandler(Exception ex) {
        return ex.getMessage();
    }
}
