package com.rentit.sales.infrastructure.idgeneration;

import com.rentit.common.infrastructure.HibernateBasedIdentifierGenerator;
import com.rentit.sales.domain.model.InvoiceID;
import com.rentit.sales.domain.model.PurchaseOrderExtensionID;
import com.rentit.sales.domain.model.PurchaseOrderID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SalesIdentifierGenerator {
    @Autowired
    HibernateBasedIdentifierGenerator hibernateGenerator;

    public PurchaseOrderID nextPurchaseOrderID() {
        return PurchaseOrderID.of(hibernateGenerator.getID("PurchaseOrderIDSequence"));
    }
    public PurchaseOrderExtensionID nextPurchaseOrderExtensionID() {
        return PurchaseOrderExtensionID.of(hibernateGenerator.getID("PurchaseOrderExtensionIDSequence"));
    }
    public InvoiceID nextInvoiceID() {
        return InvoiceID.of(hibernateGenerator.getID("InvoiceIDSequence"));
    }
}
