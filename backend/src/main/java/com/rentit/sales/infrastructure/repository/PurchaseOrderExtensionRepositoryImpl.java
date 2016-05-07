package com.rentit.sales.infrastructure.repository;

import com.rentit.sales.domain.model.POStatus;
import com.rentit.sales.domain.model.PurchaseOrderExtension;
import com.rentit.sales.domain.repository.CustomPurchaseOrderExtensionRepository;
import com.rentit.sales.domain.repository.PurchaseOrderExtensionRepository;

/**
 * Created by akaiz on 3/30/2016.
 */
public class PurchaseOrderExtensionRepositoryImpl implements CustomPurchaseOrderExtensionRepository {
    @Override
    public PurchaseOrderExtension PurchaseOrderExtensionConfirmation(POStatus poStatus, Long eid) {
        return null;
    }
}
