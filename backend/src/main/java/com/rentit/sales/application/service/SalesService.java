package com.rentit.sales.application.service;

import com.rentit.common.application.dto.BusinessPeriodDTO;
import com.rentit.common.domain.model.BusinessPeriod;
import com.rentit.common.domain.model.UserType;
import com.rentit.common.domain.validation.BusinessPeriodValidator;
import com.rentit.inventory.application.dto.PlantReservationDTO;
import com.rentit.inventory.application.service.InventoryService;
import com.rentit.inventory.application.service.PlantInventoryEntryAssembler;
import com.rentit.inventory.domain.model.PlantInventoryEntry;
import com.rentit.inventory.domain.model.PlantReservation;
import com.rentit.common.application.exceptions.PlantNotFoundException;
import com.rentit.inventory.domain.model.PlantReservationID;
import com.rentit.sales.application.dto.PurchaseOrderDTO;
import com.rentit.sales.domain.model.Invoice;
import com.rentit.sales.domain.model.POStatus;
import com.rentit.sales.domain.model.PurchaseOrder;
import com.rentit.sales.domain.model.PurchaseOrderID;
import com.rentit.sales.domain.repository.InvoiceRepository;
import com.rentit.sales.domain.repository.PurchaseOrderExtensionRepository;
import com.rentit.sales.domain.repository.PurchaseOrderRepository;
import com.rentit.sales.domain.validation.ContactPersonValidator;
import com.rentit.sales.domain.validation.PurchaseOrderValidator;
import com.rentit.sales.infrastructure.idgeneration.SalesIdentifierGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindException;
import org.springframework.validation.DataBinder;

import java.util.ArrayList;
import java.util.List;

@Service
public class SalesService {
    @Autowired
    InventoryService inventoryService;
    @Autowired
    PurchaseOrderAssembler purchaseOrderAssembler;
    @Autowired
    PurchaseOrderRepository purchaseOrderRepository;
    @Autowired
    SalesIdentifierGenerator identifierGenerator;
    @Autowired
    InvoiceRepository invoiceRepository;
    @Autowired
    PurchaseOrderExtensionRepository purchaseOrderExtensionRepository;
    @Autowired
    PlantInventoryEntryAssembler plantInventoryEntryAssembler;
    public PurchaseOrderDTO createPurchaseOrder(PurchaseOrderDTO purchaseOrderDTO,Long id) throws BindException, PlantNotFoundException {
        PlantInventoryEntry plant = inventoryService.findPlant(id);
        PurchaseOrder po = PurchaseOrder.of(
                identifierGenerator.nextPurchaseOrderID(),
                plant.getId(),
                BusinessPeriod.of(purchaseOrderDTO.getRentalPeriod().getStartDate(), purchaseOrderDTO.getRentalPeriod().getEndDate()),purchaseOrderDTO.getEmail()
        );

        DataBinder binder = new DataBinder(po);
        binder.addValidators(new PurchaseOrderValidator(new BusinessPeriodValidator(), new ContactPersonValidator()));
        binder.validate();

        if (binder.getBindingResult().hasErrors())
            throw new BindException(binder.getBindingResult());

        purchaseOrderRepository.save(po);



        PlantReservationDTO reservationDTO = inventoryService.createPlantReservation(plantInventoryEntryAssembler.toResource(plant), purchaseOrderDTO.getRentalPeriod());

        po.confirmReservation(
                PlantReservationID.of(reservationDTO.get_id()),
                plant.getPrice()); // Shouldn't we also pass the reservation schedule as a parameter to check if it matches rental period?

        binder = new DataBinder(po);
        binder.addValidators(new PurchaseOrderValidator(new BusinessPeriodValidator(), new ContactPersonValidator()));
        binder.validate();

        if (binder.getBindingResult().hasErrors())
            throw new BindException(binder.getBindingResult());

        purchaseOrderRepository.save(po);

        return purchaseOrderAssembler.toResource(po);
    }

    public PurchaseOrderDTO findPurchaseOrder(PurchaseOrderID id,UserType userType) {


        PurchaseOrder purchaseOrder = purchaseOrderRepository.findOne(id);

           if(purchaseOrder==null){

            return  new PurchaseOrderDTO();
        }
        else {
               purchaseOrderAssembler.setUserType(userType);
               return purchaseOrderAssembler.toResource(purchaseOrderRepository.findOne(id));
           }


    }
    public PurchaseOrderDTO confirmOrRejectPurchaseOrder(POStatus poStatus,long id) throws BindException, PlantNotFoundException {

        PurchaseOrder po = purchaseOrderRepository.finalPurchaseOrderConfirmation(poStatus,id);

        return purchaseOrderAssembler.toResource(po);




    }



    public PurchaseOrderDTO reSubmitPurchaseOrder(long id,BusinessPeriod businessPeriod) throws BindException, PlantNotFoundException {

        PurchaseOrder po = purchaseOrderRepository.resubmitOrderConfirmation(id,businessPeriod);

        return purchaseOrderAssembler.toResource(po);




    }

    public PurchaseOrderDTO extendPurchaseOrder(long id, BusinessPeriodDTO businessPeriodDTO) throws BindException, PlantNotFoundException {


        BusinessPeriod businessPeriod= BusinessPeriod.of(businessPeriodDTO.getStartDate(),businessPeriodDTO.getEndDate());


        PurchaseOrder po = purchaseOrderRepository.extendPurchaseOrder(id,businessPeriod);

        return purchaseOrderAssembler.toResource(po);




    }



    public PurchaseOrderDTO DeletePurchaseOrder(long id) throws BindException, PlantNotFoundException {

        PurchaseOrder po = purchaseOrderRepository.deletePurchaseOrder(id);

        return purchaseOrderAssembler.toResource(po);




    }

    public PurchaseOrderDTO confirmOrRejectPurchaseOrderExtension(POStatus poStatus,long oid,Long eid) throws BindException, PlantNotFoundException {

        PurchaseOrder po = purchaseOrderRepository.extendPurchaseOrderConfirmation(poStatus,oid,eid);

        return purchaseOrderAssembler.toResource(po);


    }
    public List<PurchaseOrderDTO>findPurcahseOrdersThatNeedInvoice(){
        List<PurchaseOrderDTO> purchaseOrderDTOs = new ArrayList<PurchaseOrderDTO>();
        for (PurchaseOrder purchaseOrder:purchaseOrderRepository.findOrdersThatNeedInvoice()
             ) {
            purchaseOrderAssembler.setUserType(UserType.RENTIT);
               purchaseOrderDTOs.add(purchaseOrderAssembler.toResource( purchaseOrder));
            }

        return purchaseOrderDTOs;
    }
    public List<PurchaseOrderDTO>findPurcahseOrders(UserType userType){
        List<PurchaseOrderDTO> purchaseOrderDTOs = new ArrayList<PurchaseOrderDTO>();
        for (PurchaseOrder purchaseOrder:purchaseOrderRepository.findAll()
                ) {
            purchaseOrderAssembler.setUserType(userType);
            purchaseOrderDTOs.add(purchaseOrderAssembler.toResource( purchaseOrder));
        }

        return purchaseOrderDTOs;
    }

    public Boolean createInvoice(Long purchaseOrderId){
        Invoice invoice = Invoice.of(identifierGenerator.nextInvoiceID(),PurchaseOrderID.of(purchaseOrderId));

          Long id = invoiceRepository.save(invoice).getPurchaseOrder().getId();

         if(id==purchaseOrderId){
           return  true;
         }
        else {
             return  false;
         }

    }
    public void remittanceReceived(Long purchaseOrderId){
       invoiceRepository.RemitanceAdviceRecieved(purchaseOrderId);

    }
    public PurchaseOrderDTO updatePurchaseOrder(Long id,POStatus status){
        return  purchaseOrderAssembler.toResource(purchaseOrderRepository.updatePurchaseOrderStatus(id,status));

    }
    public PurchaseOrderDTO cancelPurchaseOrder(Long id){
        return  purchaseOrderAssembler.toResource(purchaseOrderRepository.cancelPurchaseOrder(id));

    }
    public Long getLastExtensionId(PurchaseOrderID purchaseOrderID){
        return purchaseOrderExtensionRepository.getLastExtension(purchaseOrderID);
    }
}
