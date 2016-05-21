package com.rentit.sales.infrastructure.repository;

import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.jpa.impl.JPAUpdateClause;
import com.rentit.common.domain.model.BusinessPeriod;
import com.rentit.sales.domain.model.*;
import com.rentit.sales.domain.repository.CustomPurchaseOrderRepository;
import com.rentit.sales.domain.repository.PurchaseOrderExtensionRepository;
import com.rentit.sales.domain.repository.PurchaseOrderRepository;
import com.rentit.sales.infrastructure.idgeneration.SalesIdentifierGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by akaiz on 3/24/2016.
 */
public class PurchaseOrderRepositoryImpl implements CustomPurchaseOrderRepository {
    @Autowired
  EntityManager em;
    QPurchaseOrder qpurchaseOrder=QPurchaseOrder.purchaseOrder;
    @Autowired
    PurchaseOrderExtensionRepository purchaseOrderExtensionRepository;
    QPurchaseOrderExtension qPurchaseOrderExtension = QPurchaseOrderExtension.purchaseOrderExtension;
    @Autowired
    PurchaseOrderRepository purchaseOrderRepository;
    @Autowired
    SalesIdentifierGenerator identifierGenerator;
    @Override
    @Transactional   // we need this if we are going to update or delete
    public PurchaseOrder finalPurchaseOrderConfirmation(POStatus poStatus, Long id) {

        new JPAUpdateClause(em,qpurchaseOrder)
                .set(qpurchaseOrder.status,poStatus).where(qpurchaseOrder.id.id.eq(id)).execute();

        return new  JPAQuery(em)
                .from(qpurchaseOrder)
                .where(qpurchaseOrder.id.id.eq(id)).uniqueResult(qpurchaseOrder);
    }

    @Override
    @Transactional
    public PurchaseOrder resubmitOrderConfirmation(Long id,BusinessPeriod businessPeriod) {
        new JPAUpdateClause(em,qpurchaseOrder)
                .set(qpurchaseOrder.status,POStatus.PENDING).set(qpurchaseOrder.rentalPeriod,businessPeriod).where(qpurchaseOrder.id.id.eq(id).and(qpurchaseOrder.status.eq(POStatus.REJECTED))).execute();

        return new  JPAQuery(em)
                .from(qpurchaseOrder)
                .where(qpurchaseOrder.id.id.eq(id)).uniqueResult(qpurchaseOrder);
    }

    @Override
    @Transactional
    public PurchaseOrder extendPurchaseOrder(Long id, BusinessPeriod businessPeriod) {

        PurchaseOrderExtension purchaseOrderExtension= PurchaseOrderExtension.of
                (identifierGenerator.nextPurchaseOrderExtensionID(), PurchaseOrderID.of(id),businessPeriod,POStatus.PENDING);
     PurchaseOrderExtension v =   purchaseOrderExtensionRepository.save(purchaseOrderExtension);
          PurchaseOrder purchaseOrder=new  JPAQuery(em)
                  .from(qpurchaseOrder)
                  .where(qpurchaseOrder.id.id.eq(id)).uniqueResult(qpurchaseOrder);
           purchaseOrder.addExtension(v.getId());
            purchaseOrder.setStatus(POStatus.PENDING_EXTENSION);
           purchaseOrderRepository.save(purchaseOrder);


         return new  JPAQuery(em)
                .from(qpurchaseOrder)
                .where(qpurchaseOrder.id.id.eq(id)).uniqueResult(qpurchaseOrder);
    }

    @Override
    @Transactional
    public PurchaseOrder deletePurchaseOrder(Long id) {
        new JPAUpdateClause(em,qpurchaseOrder)
                .set(qpurchaseOrder.status,POStatus.CLOSED).where(qpurchaseOrder.id.id.eq(id).and(qpurchaseOrder.status.eq(POStatus.OPEN))).execute();

        return new  JPAQuery(em)
                .from(qpurchaseOrder)
                .where(qpurchaseOrder.id.id.eq(id)).uniqueResult(qpurchaseOrder);
    }

    @Override
    @Transactional
    public PurchaseOrder extendPurchaseOrderConfirmation(POStatus poStatus,Long oid, Long eid) {

        POStatus status = new  JPAQuery(em)
                .from(qpurchaseOrder)
                .where(qpurchaseOrder.id.id.eq(oid)).uniqueResult(qpurchaseOrder).getStatus();

         if(status.equals(POStatus.PENDING_EXTENSION)){
             new JPAUpdateClause(em,qPurchaseOrderExtension)
                     .set(qPurchaseOrderExtension.status,poStatus).where(qPurchaseOrderExtension.id.id.eq(eid)
                     .and(qPurchaseOrderExtension.purchaseOrder.id.eq(oid))).execute();

              new JPAUpdateClause(em,qpurchaseOrder)
                     .set(qpurchaseOrder.status,POStatus.EXTENDED).where(qpurchaseOrder.id.id.eq(oid)
                    ).execute();

         }
               return new  JPAQuery(em)
                .from(qpurchaseOrder)
                .where(qpurchaseOrder.id.id.eq(oid)).uniqueResult(qpurchaseOrder);
    }

    @Override
    public List<PurchaseOrder> findOrdersThatNeedInvoice() {
        return new  JPAQuery(em)
                .from(qpurchaseOrder)
                .where(qpurchaseOrder.status.eq(POStatus.CLOSED)).distinct().list(qpurchaseOrder);
    }

    @Override
    @Transactional
    public PurchaseOrder updatePurchaseOrderStatus(Long id, POStatus poStatus) {
        new JPAUpdateClause(em,qpurchaseOrder)
                .set(qpurchaseOrder.status,poStatus).where(qpurchaseOrder.id.id.eq(id)).execute();

        return new  JPAQuery(em)
                .from(qpurchaseOrder)
                .where(qpurchaseOrder.id.id.eq(id)).uniqueResult(qpurchaseOrder);

    }

    @Override
    @Transactional
    public PurchaseOrder cancelPurchaseOrder(Long id) {

        POStatus poStatus= new JPAQuery(em).from(qpurchaseOrder).where(qpurchaseOrder.id.id.eq(id)).uniqueResult(qpurchaseOrder).getStatus();
        if(poStatus!=POStatus.DELIVERED||poStatus!=POStatus.DISPATCHED){
            new JPAUpdateClause(em,qpurchaseOrder)
                    .set(qpurchaseOrder.status,POStatus.CANCELLED).where(qpurchaseOrder.id.id.eq(id)).execute();
        }
        return new  JPAQuery(em)
                .from(qpurchaseOrder)
                .where(qpurchaseOrder.id.id.eq(id)).uniqueResult(qpurchaseOrder);
    }


}
