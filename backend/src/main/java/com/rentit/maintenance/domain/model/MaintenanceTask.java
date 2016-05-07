package com.rentit.maintenance.domain.model;

import com.rentit.common.domain.model.BusinessPeriod;
import com.rentit.inventory.domain.model.PlantReservationID;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@AllArgsConstructor(staticName = "of")
public class MaintenanceTask {
    @EmbeddedId
    MaintenanceTaskID id;

    @Enumerated(EnumType.STRING)
    TypeOfWork typeOfWork;

    @Embedded
    @AttributeOverrides({@AttributeOverride(name="id", column=@Column(name="reservation_id"))})
    PlantReservationID reservation;

    @Column(precision = 8, scale = 2)
    BigDecimal price;

    @Embedded
    BusinessPeriod schedule;
}
