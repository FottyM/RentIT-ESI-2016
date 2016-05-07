package com.rentit.sales.domain.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Created by akaiz on 5/5/2016.
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class Invoice {
    @EmbeddedId
    InvoiceID id;
    @AttributeOverrides({@AttributeOverride(name="id", column=@Column(name="purchase_order_id"))})
    PurchaseOrderID purchaseOrder;
    Boolean remittancePaid ;
    LocalDate remittanceDate;
public  static Invoice of(InvoiceID invoiceID,PurchaseOrderID purchaseOrderID){
    Invoice invoice = new Invoice();
    invoice.id= invoiceID;
    invoice.purchaseOrder=purchaseOrderID;
    return  invoice;
}
}
