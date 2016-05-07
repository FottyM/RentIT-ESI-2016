package com.rentit.sales.domain.repository;

import com.rentit.sales.domain.model.PurchaseOrder;
import com.rentit.sales.domain.model.PurchaseOrderExtension;
import com.rentit.sales.domain.model.PurchaseOrderExtensionID;
import com.rentit.sales.domain.model.PurchaseOrderID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface PurchaseOrderExtensionRepository extends JpaRepository<PurchaseOrderExtension, PurchaseOrderExtensionID>,CustomPurchaseOrderExtensionRepository{


}