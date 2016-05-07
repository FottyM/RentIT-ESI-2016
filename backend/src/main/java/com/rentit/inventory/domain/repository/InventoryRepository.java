package com.rentit.inventory.domain.repository;

import com.rentit.inventory.domain.model.PlantInventoryEntry;
import com.rentit.inventory.domain.model.PlantInventoryEntryID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<PlantInventoryEntry, PlantInventoryEntryID>, CustomInventoryRepository {
    List<PlantInventoryEntry> findByNameContainingIgnoreCase(String name);
}
