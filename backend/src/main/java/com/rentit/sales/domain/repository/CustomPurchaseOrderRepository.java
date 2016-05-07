package com.rentit.sales.domain.repository;

import com.rentit.common.domain.model.BusinessPeriod;
import com.rentit.sales.domain.model.POStatus;
import com.rentit.sales.domain.model.PurchaseOrder;

import java.util.List;

/**
 * Created by akaiz on 3/24/2016.
 */
public interface CustomPurchaseOrderRepository  {
    public PurchaseOrder finalPurchaseOrderConfirmation(POStatus poStatus,Long id);
    public PurchaseOrder resubmitOrderConfirmation(Long id);
    public PurchaseOrder extendPurchaseOrder(Long id, BusinessPeriod businessPeriod);
    public PurchaseOrder deletePurchaseOrder(Long id);
    public PurchaseOrder extendPurchaseOrderConfirmation(POStatus poStatus,Long oid, Long eid);
    public List<PurchaseOrder> findOrdersThatNeedInvoice();

}

