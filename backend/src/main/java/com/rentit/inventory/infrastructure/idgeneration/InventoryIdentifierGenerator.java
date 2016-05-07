package com.rentit.inventory.infrastructure.idgeneration;

import com.rentit.common.infrastructure.HibernateBasedIdentifierGenerator;
import com.rentit.inventory.domain.model.PlantInventoryEntryID;
import com.rentit.inventory.domain.model.PlantInventoryItem;
import com.rentit.inventory.domain.model.PlantInventoryItemID;
import com.rentit.inventory.domain.model.PlantReservationID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventoryIdentifierGenerator {
    @Autowired
    HibernateBasedIdentifierGenerator hibernateGenerator;

    public PlantInventoryEntryID nextPlantInventoryEntryID() {
        return PlantInventoryEntryID.of(hibernateGenerator.getID("PlantInventoryEntryIDSequence"));
    }

    public PlantInventoryItemID nextPlantInventoryItemID() {
        return PlantInventoryItemID.of(hibernateGenerator.getID("PlantInventoryItemIDSequence"));
    }

    public PlantReservationID nextPlantReservationID() {
        return  PlantReservationID.of(hibernateGenerator.getID("PlantReservationIDSequence"));
    }
}
