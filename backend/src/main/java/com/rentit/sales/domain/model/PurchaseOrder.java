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
public class PurchaseOrder {
    @EmbeddedId
    PurchaseOrderID id;

    @Embedded
    @AttributeOverrides({@AttributeOverride(name="id", column=@Column(name="plant_id"))})
    PlantInventoryEntryID plant;
    @Embedded
    BusinessPeriod rentalPeriod;

    @ElementCollection
    @AttributeOverrides({@AttributeOverride(name="id", column=@Column(name="reservation_id"))})
    List<PlantReservationID> reservations = new ArrayList<>();

    LocalDate issueDate;
    LocalDate paymentSchedule;

    @Column(precision = 8, scale = 2)
    BigDecimal total;

    @Enumerated(EnumType.STRING)
    POStatus status;

    @ElementCollection
    @AttributeOverrides({@AttributeOverride(name="id", column=@Column(name="extension_id"))})
    List<PurchaseOrderExtensionID> extensions = new ArrayList<>();
    String contactEmail;

    public static PurchaseOrder of(PurchaseOrderID id, PlantInventoryEntryID plant, BusinessPeriod period,String contactEmail) {
        PurchaseOrder po = new PurchaseOrder();
        po.id = id;
        po.plant = plant;
        po.rentalPeriod = period;
        po.status = POStatus.PENDING;
        po.contactEmail=contactEmail;
        return po;
    }

    public void confirmReservation(PlantReservationID reservation, BigDecimal plantPrice) {
        reservations.add(reservation);
        total = plantPrice.multiply(BigDecimal.valueOf(rentalPeriod.numberOfWorkingDays()));
        //status = POStatus.OPEN;
    }


 public void addExtension(PurchaseOrderExtensionID extension) {
        extensions.add(extension);

    }
    public void setStatus(POStatus status) {
        this.status=status;

    }
}
