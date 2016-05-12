package com.rentit.sales.domain.repository;

import com.rentit.sales.domain.model.POStatus;
import com.rentit.sales.domain.model.PurchaseOrder;
import com.rentit.sales.domain.model.PurchaseOrderExtension;
import com.rentit.sales.domain.model.PurchaseOrderID;

/**
 * Created by akaiz on 3/30/2016.
 */
public interface CustomPurchaseOrderExtensionRepository {
    public PurchaseOrderExtension PurchaseOrderExtensionConfirmation(POStatus poStatus, Long eid);
    public Long getLastExtension (PurchaseOrderID purchaseOrderID);

}
