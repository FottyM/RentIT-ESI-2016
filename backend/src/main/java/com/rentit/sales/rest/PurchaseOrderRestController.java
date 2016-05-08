package com.rentit.sales.rest;

import com.rentit.common.application.dto.BusinessPeriodDTO;
import com.rentit.common.domain.model.BusinessPeriod;
import com.rentit.sales.application.dto.PurchaseOrderDTO;
import com.rentit.sales.application.service.SalesService;
import com.rentit.sales.domain.model.POStatus;
import com.rentit.sales.domain.model.PurchaseOrderID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.Scanner;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping("/api/sales/orders")
public class PurchaseOrderRestController {
    @Autowired
    SalesService salesService;
    @RequestMapping(method = GET, path = "")
    public List<PurchaseOrderDTO> getPurchaseOrders(){
        List<PurchaseOrderDTO> purchaseOrderDTO=salesService.findPurcahseOrders();
        return purchaseOrderDTO;
    }





    @RequestMapping(method = POST, path = "")
    public ResponseEntity<PurchaseOrderDTO> createPurchaseOrder(@RequestBody PurchaseOrderDTO poDTO) throws Exception {


        String x =poDTO.getPlant().getLinks().get(0).getHref().toString();
        int id = Integer.parseInt(x.replaceAll("[^0-9]", ""));

        poDTO = salesService.createPurchaseOrder(poDTO,Long.parseLong(id+""));

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(new URI(poDTO.getId().getHref()));
        return new ResponseEntity<PurchaseOrderDTO>(poDTO, headers, HttpStatus.CREATED);
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
    @RequestMapping(method = PUT, path = "/{id}")
    public PurchaseOrderDTO reSubmitPurchaseOrder(@PathVariable Long id,@RequestBody BusinessPeriod businessPeriod) throws Exception {
        PurchaseOrderDTO poDTO = salesService.reSubmitPurchaseOrder(id,businessPeriod);
        return poDTO;
    }
    @RequestMapping(method = DELETE, path = "/{id}")
    public PurchaseOrderDTO deletePurchaseOrder(@PathVariable Long id) throws Exception {
        PurchaseOrderDTO poDTO = salesService.DeletePurchaseOrder(id);
        return poDTO;
    }
    @RequestMapping(method = POST, path = "/{id}/extensions")
    public  ResponseEntity<PurchaseOrderDTO> extendPurchaseOrder(@PathVariable Long id, @RequestBody BusinessPeriodDTO businessPeriod) throws Exception {


        PurchaseOrderDTO poDTO = salesService.extendPurchaseOrder(id,businessPeriod);


        HttpHeaders headers = new HttpHeaders();
       // headers.setLocation(new URI("/"+poDTO.getId()));
        return new ResponseEntity<PurchaseOrderDTO>(poDTO, headers, HttpStatus.CREATED);
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

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    public String bindExceptionHandler(Exception ex) {
        return ex.getMessage();
    }
}