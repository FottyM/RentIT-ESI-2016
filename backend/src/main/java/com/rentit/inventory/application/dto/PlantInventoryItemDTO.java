package com.rentit.inventory.application.dto;


import com.rentit.inventory.domain.model.EquipmentCondition;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.ResourceSupport;

@Getter @Setter
@NoArgsConstructor
public class PlantInventoryItemDTO extends ResourceSupport {
    //Long _id;
    String serialNumber;
    PlantInventoryEntryDTO plantInfo;
    EquipmentCondition condition;
}
