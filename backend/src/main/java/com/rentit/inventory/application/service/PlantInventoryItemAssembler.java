package com.rentit.inventory.application.service;

import com.rentit.inventory.application.dto.PlantInventoryItemDTO;
import com.rentit.inventory.domain.model.PlantInventoryItem;
import com.rentit.inventory.domain.model.PlantInventoryItemID;
import com.rentit.inventory.domain.repository.PlantInventoryItemRepository;
import com.rentit.inventory.rest.PlantInventoryItemRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Service;

@Service
public class PlantInventoryItemAssembler extends ResourceAssemblerSupport<PlantInventoryItem, PlantInventoryItemDTO> {
    @Autowired
    PlantInventoryEntryAssembler entryAssembler;
    @Autowired
    PlantInventoryItemRepository itemRepository;

    public PlantInventoryItemAssembler() {
        super(PlantInventoryItemRestController.class, PlantInventoryItemDTO.class);
    }

    @Override
    public PlantInventoryItemDTO toResource(PlantInventoryItem item) {
        PlantInventoryItemDTO dto = createResourceWithId(item.getId().getId(), item);
        //dto.set_id(item.getId().getId());
        dto.setCondition(item.getCondition());
        dto.setPlantInfo(entryAssembler.toResource(item.getPlantInfo()));
        dto.setSerialNumber(item.getSerialNumber());
        return dto;
    }

    public PlantInventoryItemDTO toResource(PlantInventoryItemID plant) {
        return toResource(itemRepository.findOne(plant));
    }
}
