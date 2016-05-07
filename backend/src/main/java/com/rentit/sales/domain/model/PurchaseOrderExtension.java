package com.rentit.sales.domain.model;

import com.rentit.common.domain.model.BusinessPeriod;
import com.rentit.inventory.domain.model.PlantInventoryEntry;
import com.rentit.inventory.domain.model.PlantInventoryEntryID;
import com.rentit.inventory.domain.model.PlantReservation;
import com.rentit.inventory.domain.model.PlantReservationID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class PurchaseOrderExtension {
    @EmbeddedId
    PurchaseOrderExtensionID id;

    @Embedded
    @AttributeOverrides({@AttributeOverride(name="id", column=@Column(name="order_id"))})
    PurchaseOrderID purchaseOrder;
    @Embedded
    BusinessPeriod rentalPeriod;

    @Enumerated(EnumType.STRING)
    POStatus status;

    public static PurchaseOrderExtension of(PurchaseOrderExtensionID id, PurchaseOrderID purchaseOrder, BusinessPeriod period,POStatus poStatus) {
        PurchaseOrderExtension po = new PurchaseOrderExtension();
        po.id = id;
        po.purchaseOrder = purchaseOrder;
        po.rentalPeriod = period;
        po.status=poStatus;
        return po;
    }

//    public void confirmReservation(PlantReservationID reservation, BigDecimal plantPrice) {
//        reservations.add(reservation);
//        total = plantPrice.multiply(BigDecimal.valueOf(rentalPeriod.numberOfWorkingDays()));
//        //status = POStatus.OPEN;
//    }


}
