package com.rentit.sales.application.service;

import com.rentit.common.application.dto.BusinessPeriodDTO;
import com.rentit.common.application.exceptions.PlantNotFoundException;
import com.rentit.common.rest.ExtendedLink;
import com.rentit.inventory.application.dto.PlantInventoryEntryDTO;
import com.rentit.inventory.application.service.InventoryService;
import com.rentit.sales.application.dto.PurchaseOrderDTO;
import com.rentit.sales.domain.model.PurchaseOrder;
import com.rentit.sales.domain.model.PurchaseOrderExtension;
import com.rentit.sales.domain.model.PurchaseOrderExtensionID;
import com.rentit.sales.domain.repository.PurchaseOrderExtensionRepository;
import com.rentit.sales.domain.repository.PurchaseOrderRepository;
import com.rentit.sales.rest.PurchaseOrderRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.PUT;

@Service
public class PurchaseOrderAssembler extends ResourceAssemblerSupport<PurchaseOrder, PurchaseOrderDTO> {
    @Autowired
    InventoryService inventoryService;
    @Autowired
    PurchaseOrderExtensionRepository purchaseOrderExtensionRepository;

    public PurchaseOrderAssembler() {
        super(PurchaseOrderRestController.class, PurchaseOrderDTO.class);
    }

    @Override
    public PurchaseOrderDTO toResource(PurchaseOrder purchaseOrder) {
        PurchaseOrderDTO dto = createResourceWithId(purchaseOrder.getId().getId(), purchaseOrder);
        try {
            dto.setPlant(inventoryService.findPlant(purchaseOrder.getPlant()));
        } catch (PlantNotFoundException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
       // dto.set_id(purchaseOrder.getId().getId());
        dto.setRentalPeriod(BusinessPeriodDTO.of(purchaseOrder.getRentalPeriod().getStartDate(), purchaseOrder.getRentalPeriod().getEndDate()));
        dto.setTotal(purchaseOrder.getTotal());
        dto.setStatus(purchaseOrder.getStatus());
        dto.setExtensions(purchaseOrder.getExtensions());
        dto.setEmail(purchaseOrder.getContactEmail());




        try {
            switch (purchaseOrder.getStatus()) {
                case PENDING:

                    dto.add(new ExtendedLink(
                            linkTo(methodOn(PurchaseOrderRestController.class)
                                    .acceptPurchaseOrder(purchaseOrder.getId().getId())).toString(),
                            "accept", POST));
                    dto.add(new ExtendedLink(
                            linkTo(methodOn(PurchaseOrderRestController.class)
                                    .rejectPurchaseOrder(purchaseOrder.getId().getId())).toString(),
                            "reject", DELETE));
                    break;
                case OPEN:

                    dto.add(new ExtendedLink(
                            linkTo(methodOn(PurchaseOrderRestController.class)
                                    .extendPurchaseOrder(purchaseOrder.getId().getId(),dto.getRentalPeriod())).toString(),
                            "extend", POST));


                    dto.add(new ExtendedLink(

                            linkTo(methodOn(PurchaseOrderRestController.class)
                                    .deletePurchaseOrder(purchaseOrder.getId().getId())).toString(),
                            "delete", DELETE));
                    break;
                case REJECTED:


                    dto.add(new ExtendedLink(
                            linkTo(methodOn(PurchaseOrderRestController.class)
                                    .reSubmitPurchaseOrder(purchaseOrder.getId().getId())).toString(),
                            "resubmit", PUT));

                    break;
                default:
                    break;
            }
        } catch (Exception e) {}


        if(purchaseOrder.getExtensions().size()!=0){

            for (PurchaseOrderExtensionID purchaseOrderExtensionID:purchaseOrder.getExtensions()  ) {

                PurchaseOrderExtension p=     purchaseOrderExtensionRepository.findOne(purchaseOrderExtensionID);
                try {
                    switch (p.getStatus()) {
                        case PENDING:

                            dto.add(new ExtendedLink(
                                    linkTo(methodOn(PurchaseOrderRestController.class)
                                            .acceptPurchaseOrderExtension(purchaseOrder.getId().getId(),purchaseOrderExtensionID.getId())).toString(),
                                    "acceptRpExtension", POST));
                            dto.add(new ExtendedLink(
                                    linkTo(methodOn(PurchaseOrderRestController.class)
                                            .rejectPurchaseOrderExtension(purchaseOrder.getId().getId(),purchaseOrderExtensionID.getId())).toString(),
                                    "rejectRpExtension", DELETE));
                            break;

                        default:
                            break;
                    }
                } catch (Exception e) {}

            }


        }

        return dto;
    }
    }

