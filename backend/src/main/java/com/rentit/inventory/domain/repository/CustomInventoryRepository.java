package com.rentit.inventory.domain.repository;

import com.rentit.common.domain.model.BusinessPeriod;
import com.rentit.inventory.domain.model.PlantInventoryEntry;
import com.rentit.inventory.domain.model.PlantInventoryItem;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface CustomInventoryRepository {
    List<Pair<PlantInventoryEntry, Integer>> findNumberOfAvailable(String name, BusinessPeriod period);
    List<PlantInventoryEntry> findAvailablePlants(String name, BusinessPeriod period);
    List<PlantInventoryItem> findAvailableInventoryItems(String name, BusinessPeriod period);
    boolean checkIfAvailableFor(PlantInventoryEntry plant, BusinessPeriod period);
    boolean checkIfPossiblyAvailableFor(PlantInventoryEntry plant, BusinessPeriod period);
    List<PlantInventoryItem> notHiredForMonths(Integer numberOfMonths);
}
