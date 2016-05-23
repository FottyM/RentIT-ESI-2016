package com.rentit.sales.application.service;

import com.rentit.common.application.dto.BusinessPeriodDTO;
import com.rentit.common.application.exceptions.PlantNotFoundException;
import com.rentit.common.domain.model.UserType;
import com.rentit.common.rest.ExtendedLink;
import com.rentit.inventory.application.service.InventoryService;
import com.rentit.sales.application.dto.PurchaseOrderDTO;
import com.rentit.sales.domain.model.POStatus;
import com.rentit.sales.domain.model.PurchaseOrder;
import com.rentit.sales.domain.model.PurchaseOrderExtension;
import com.rentit.sales.domain.model.PurchaseOrderExtensionID;
import com.rentit.sales.domain.repository.PurchaseOrderExtensionRepository;
import com.rentit.sales.rest.InvoiceRestController;
import com.rentit.sales.rest.PurchaseOrderRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Service;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static org.springframework.http.HttpMethod.*;

@Service
public class PurchaseOrderAssembler extends ResourceAssemblerSupport<PurchaseOrder, PurchaseOrderDTO> {
    @Autowired
    InventoryService inventoryService;
    @Autowired
    PurchaseOrderExtensionRepository purchaseOrderExtensionRepository;
    UserType userType=UserType.RENTIT;
    public PurchaseOrderAssembler() {
        super(PurchaseOrderRestController.class, PurchaseOrderDTO.class);
    }
    public void setUserType(UserType userType) {

        this.userType=userType;
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

                    if (userType.equals(UserType.BUILDIT))
                    {
                        dto.add(new ExtendedLink(
                                linkTo(methodOn(PurchaseOrderRestController.class)
                                        .deletePurchaseOrder(purchaseOrder.getId().getId())).toString(),
                                "Cancel", DELETE));


                    }
                    else{

                        dto.add(new ExtendedLink(
                                linkTo(methodOn(PurchaseOrderRestController.class)
                                        .acceptPurchaseOrder(purchaseOrder.getId().getId())).toString(),
                                "accept", POST));
                        dto.add(new ExtendedLink(
                                linkTo(methodOn(PurchaseOrderRestController.class)
                                        .rejectPurchaseOrder(purchaseOrder.getId().getId())).toString(),
                                "reject", DELETE));
                    }



                    break;
                case OPEN:




//                       dto.add(new ExtendedLink(
//
//                            linkTo(methodOn(PurchaseOrderRestController.class)
//                                    .purchaseOrderCancel(purchaseOrder.getId().getId())).toString(),
//                            "cancel",GET));

                    if (userType.equals(UserType.RENTIT)){

                        dto.add(new ExtendedLink(

                                linkTo(methodOn(PurchaseOrderRestController.class)
                                        .deletePurchaseOrder(purchaseOrder.getId().getId())).toString(),
                                "delete", DELETE));

                        dto.add(new ExtendedLink(

                                linkTo(methodOn(PurchaseOrderRestController.class)
                                        .purchaseOrderUpdateStatus(purchaseOrder.getId().getId(), POStatus.DISPATCHED)).toString(),
                                "DISPATCHED", POST));

                        dto.add(new ExtendedLink(

                                linkTo(methodOn(PurchaseOrderRestController.class)
                                        .purchaseOrderUpdateStatus(purchaseOrder.getId().getId(), POStatus.DELIVERED)).toString(),
                                "DELIVERED", POST));
                        dto.add(new ExtendedLink(

                                linkTo(methodOn(PurchaseOrderRestController.class)
                                        .purchaseOrderUpdateStatus(purchaseOrder.getId().getId(), POStatus.PLANT_REJECTED)).toString(),
                                "PLANT_REJECTED",POST ));




                    }
                    else if (userType.equals(UserType.BUILDIT)){
                        dto.add(new ExtendedLink(
                                linkTo(methodOn(PurchaseOrderRestController.class)
                                        .extendPurchaseOrder(purchaseOrder.getId().getId(),dto.getRentalPeriod())).toString(),
                                "extend", POST));


                    }



                    break;
                case PENDING_EXTENSION:





                    break;
                case DELIVERED:
                    if (userType.equals(UserType.BUILDIT)){

                        dto.add(new ExtendedLink(

                                linkTo(methodOn(PurchaseOrderRestController.class)
                                        .purchaseOrderUpdateStatus(purchaseOrder.getId().getId(), POStatus.REJECTED)).toString(),
                                "REJECT", POST));

                        dto.add(new ExtendedLink(
                                linkTo(methodOn(PurchaseOrderRestController.class)
                                        .extendPurchaseOrder(purchaseOrder.getId().getId(),dto.getRentalPeriod())).toString(),
                                "extend", POST));

                    }
                    else {
                        dto.add(new ExtendedLink(

                                linkTo(methodOn(PurchaseOrderRestController.class)
                                        .purchaseOrderUpdateStatus(purchaseOrder.getId().getId(), POStatus.RETURNED)).toString(),
                                "RETURNED", POST));
                    }



                    break;
                default:
                    break;
            }


        } catch (Exception e) {}





        if (userType.equals(UserType.RENTIT)){
            if(purchaseOrder.getExtensions().size()!=0){

                for (PurchaseOrderExtensionID purchaseOrderExtensionID:purchaseOrder.getExtensions()  ) {

                    PurchaseOrderExtension p=     purchaseOrderExtensionRepository.findOne(purchaseOrderExtensionID);
                    try {
                        switch (p.getStatus()) {
                            case PENDING:

                                dto.add(new ExtendedLink(
                                        linkTo(methodOn(PurchaseOrderRestController.class)
                                                .acceptPurchaseOrderExtension(purchaseOrder.getId().getId(),purchaseOrderExtensionID.getId())).toString(),
                                        "AcceptExtension", POST));
                                dto.add(new ExtendedLink(
                                        linkTo(methodOn(PurchaseOrderRestController.class)
                                                .rejectPurchaseOrderExtension(purchaseOrder.getId().getId(),purchaseOrderExtensionID.getId())).toString(),
                                        "RejectExtension", DELETE));
                                break;

                            default:
                                break;
                        }
                    } catch (Exception e) {}

                }


            }
        }



        return dto;
    }
}

