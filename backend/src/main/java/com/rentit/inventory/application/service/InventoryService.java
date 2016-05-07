package com.rentit.inventory.application.service;

import com.rentit.common.application.dto.BusinessPeriodDTO;
import com.rentit.common.domain.model.BusinessPeriod;
import com.rentit.inventory.application.dto.PlantInventoryEntryDTO;
import com.rentit.inventory.application.dto.PlantReservationDTO;
import com.rentit.inventory.domain.model.PlantInventoryEntry;
import com.rentit.inventory.domain.model.PlantInventoryEntryID;
import com.rentit.inventory.domain.model.PlantInventoryItem;
import com.rentit.inventory.domain.model.PlantReservation;
import com.rentit.inventory.domain.repository.InventoryRepository;
import com.rentit.inventory.domain.repository.PlantReservationRepository;
import com.rentit.common.application.exceptions.PlantNotFoundException;
import com.rentit.inventory.infrastructure.idgeneration.InventoryIdentifierGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class InventoryService {
    @Autowired
    InventoryIdentifierGenerator identifierGenerator;

    @Autowired
    PlantInventoryEntryAssembler entryAssembler;
    @Autowired
    PlantReservationAssembler reservationAssembler;

    @Autowired
    InventoryRepository inventoryRepository;
    @Autowired
    PlantReservationRepository reservationRepository;

    public List<PlantInventoryEntryDTO> findAllPlantInventoryEntries() {
        return entryAssembler.toResources(inventoryRepository.findAll());
    }

    public List<PlantInventoryEntryDTO> findAvailablePlants(String name, LocalDate startDate, LocalDate endDate) {
        return entryAssembler.toResources(inventoryRepository.findAvailablePlants(name,
                BusinessPeriod.of(startDate, endDate)));
    }

    public List<PlantInventoryEntryDTO> findPlantsByName(String name) {
        return entryAssembler.toResources(inventoryRepository.findByNameContainingIgnoreCase(name));
    }

    public PlantInventoryEntry findPlant(Long id) {
        return inventoryRepository.findOne(PlantInventoryEntryID.of(id));
    }

//    public PlantInventoryEntryDTO findPlant(PlantInventoryEntry plant) {
//        return entryAssembler.toResource(inventoryRepository.findOne(plant.getId()));
//    }

    public PlantInventoryEntryDTO findPlant(PlantInventoryEntryID plantID) throws PlantNotFoundException {
        PlantInventoryEntry entry = inventoryRepository.findOne(plantID);
        if (entry == null)
            throw new PlantNotFoundException("Requested plant does not exist!");
        return entryAssembler.toResource(entry);
    }

    public PlantReservationDTO createPlantReservation(PlantInventoryEntryDTO plant, BusinessPeriodDTO periodDTO) throws PlantNotFoundException {
        BusinessPeriod period = BusinessPeriod.of(periodDTO.getStartDate(), periodDTO.getEndDate());
        List<PlantInventoryItem> list = inventoryRepository.findAvailableInventoryItems(plant.getName(), period);

        if (list.size() == 0)
            throw new PlantNotFoundException("Requested plant is not available");

        PlantReservation reservation = PlantReservation.of(
                identifierGenerator.nextPlantReservationID(),
                period,
                list.get(0).getId()
                );

        reservationRepository.save(reservation);

        return reservationAssembler.toResource(reservation);
    }
    public PlantInventoryEntryDTO findPlantFullRepresentation(PlantInventoryEntryDTO plant) {
        PlantInventoryEntryID id = entryAssembler.resolveId(plant.getId());
        return entryAssembler.toResource(inventoryRepository.findOne(id));
    }

    public Boolean checkIfPlantIsAvailable (Long id,BusinessPeriodDTO businessPeriodDTO){
        BusinessPeriod period = BusinessPeriod.of(businessPeriodDTO.getStartDate(),businessPeriodDTO.getEndDate());
        PlantInventoryEntry plantInventoryEntry =  PlantInventoryEntry.of(PlantInventoryEntryID.of(id));
         Boolean result = inventoryRepository.checkIfAvailableFor(plantInventoryEntry,period);
         Boolean result2 = inventoryRepository.checkIfPossiblyAvailableFor(plantInventoryEntry,period);
        if (result||result2){
            return true;
        }
        return  false;
    }
}
