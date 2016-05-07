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
import java.util.Scanner;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping("/api/sales/orders")
public class PurchaseOrderRestController {
    @Autowired
    SalesService salesService;

    @RequestMapping(method = POST, path = "")
    public ResponseEntity<PurchaseOrderDTO> createPurchaseOrder(@RequestBody PurchaseOrderDTO poDTO) throws Exception {
        Scanner in = new Scanner(poDTO.getLinks().get(0).getHref()).useDelimiter("[^0-9]+");
        int id = in.nextInt();

        poDTO = salesService.createPurchaseOrder(poDTO,Long.parseLong(id+""));

        HttpHeaders headers = new HttpHeaders();
       // headers.setLocation(new URI(poDTO.getId().getHref));
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
    public PurchaseOrderDTO reSubmitPurchaseOrder(@PathVariable Long id) throws Exception {
        PurchaseOrderDTO poDTO = salesService.reSubmitPurchaseOrder(id);
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

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    public String bindExceptionHandler(Exception ex) {
        return ex.getMessage();
    }
}
