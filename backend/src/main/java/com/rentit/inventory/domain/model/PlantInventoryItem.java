package com.rentit.inventory.domain.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter @ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@AllArgsConstructor(staticName = "of")
public class PlantInventoryItem {
    @EmbeddedId
    PlantInventoryItemID id;

    String serialNumber;

    @Embedded
    @AttributeOverrides({@AttributeOverride(name="id", column=@Column(name="plant_info_id"))})
    PlantInventoryEntryID plantInfo;

    @Enumerated(EnumType.STRING)
    EquipmentCondition condition;
}
