package com.rentit.sales.infrastructure.repository;

import com.mysema.query.jpa.impl.JPAQuery;
import com.rentit.sales.domain.model.POStatus;
import com.rentit.sales.domain.model.PurchaseOrderExtension;
import com.rentit.sales.domain.model.PurchaseOrderID;
import com.rentit.sales.domain.model.QPurchaseOrderExtension;
import com.rentit.sales.domain.repository.CustomPurchaseOrderExtensionRepository;
import com.rentit.sales.domain.repository.PurchaseOrderExtensionRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

/**
 * Created by akaiz on 3/30/2016.
 */
public class PurchaseOrderExtensionRepositoryImpl implements CustomPurchaseOrderExtensionRepository {
    @Autowired
    EntityManager em;
    QPurchaseOrderExtension qPurchaseOrderExtension= QPurchaseOrderExtension.purchaseOrderExtension;
    @Override
    public PurchaseOrderExtension PurchaseOrderExtensionConfirmation(POStatus poStatus, Long eid) {
        return null;
    }

    @Override
    public Long getLastExtension(PurchaseOrderID purchaseOrderID) {
        return new JPAQuery(em)
                .from(qPurchaseOrderExtension)
                .where(qPurchaseOrderExtension.purchaseOrder.eq(purchaseOrderID)).uniqueResult(qPurchaseOrderExtension).getId().getId();
    }
}
