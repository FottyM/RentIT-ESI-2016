package com.rentit.inventory.domain.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter @ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@AllArgsConstructor(staticName = "of")
public class PlantInventoryEntry {
    @EmbeddedId
    PlantInventoryEntryID id;
    String name;
    String description;
    @Column(precision = 8, scale = 2)
    BigDecimal price;
    public static PlantInventoryEntry of(PlantInventoryEntryID plantInventoryEntryID){
        PlantInventoryEntry plantInventoryEntry = new PlantInventoryEntry();
        plantInventoryEntry.id=plantInventoryEntryID;
        return plantInventoryEntry;
    }
}
