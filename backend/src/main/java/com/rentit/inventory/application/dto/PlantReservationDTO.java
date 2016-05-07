package com.rentit.inventory.application.dto;


import com.rentit.common.application.dto.BusinessPeriodDTO;
import com.rentit.common.domain.model.BusinessPeriod;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.ResourceSupport;


@Getter
@Setter
@NoArgsConstructor
public class PlantReservationDTO extends ResourceSupport {
    Long _id;
    BusinessPeriodDTO schedule;
    PlantInventoryItemDTO plant;
}
