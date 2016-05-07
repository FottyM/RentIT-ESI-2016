package com.rentit.inventory.application.service;

import com.rentit.common.application.dto.BusinessPeriodDTO;
import com.rentit.inventory.application.dto.PlantInventoryEntryDTO;
import com.rentit.inventory.application.dto.PlantReservationDTO;
import com.rentit.inventory.domain.model.PlantInventoryEntry;
import com.rentit.inventory.domain.model.PlantReservation;
import com.rentit.inventory.rest.PlantInventoryEntryRestController;
import com.rentit.inventory.rest.PlantReservationRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Service;

@Service
public class PlantReservationAssembler extends ResourceAssemblerSupport<PlantReservation, PlantReservationDTO> {
    @Autowired
    PlantInventoryItemAssembler itemAssembler;

    public PlantReservationAssembler() {super(PlantReservationRestController.class, PlantReservationDTO.class);
    }

    @Override
    public PlantReservationDTO toResource(PlantReservation plantReservation) {
        PlantReservationDTO dto = createResourceWithId(plantReservation.getId().getId(), plantReservation);
        dto.set_id(plantReservation.getId().getId());
        dto.setPlant(itemAssembler.toResource(plantReservation.getPlant()));
        dto.setSchedule(BusinessPeriodDTO.of(plantReservation.getSchedule().getStartDate(), plantReservation.getSchedule().getEndDate()));
        return dto;
    }
}
