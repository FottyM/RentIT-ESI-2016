package com.rentit.sales.domain.repository;

import com.rentit.sales.domain.model.Invoice;
import com.rentit.sales.domain.model.InvoiceID;
import com.rentit.sales.domain.model.PurchaseOrder;
import com.rentit.sales.domain.model.PurchaseOrderID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by akaiz on 5/5/2016.
 */
@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, InvoiceID>,CustomInvoiceRepository{
}
