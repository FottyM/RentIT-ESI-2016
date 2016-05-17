package com.rentit.inventory.application.service;

import com.rentit.inventory.application.dto.PlantInventoryEntryDTO;
import com.rentit.inventory.domain.model.PlantInventoryEntry;
import com.rentit.inventory.domain.model.PlantInventoryEntryID;
import com.rentit.inventory.domain.repository.PlantInventoryEntryRepository;
import com.rentit.inventory.rest.PlantInventoryEntryRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.core.AnnotationMappingDiscoverer;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.UriTemplate;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Service
public class PlantInventoryEntryAssembler extends ResourceAssemblerSupport<PlantInventoryEntry, PlantInventoryEntryDTO> {
    UriTemplate uriTemplate;
    @Autowired
    PlantInventoryEntryRepository entryRepository;

    public PlantInventoryEntryAssembler() {
        super(PlantInventoryEntryRestController.class, PlantInventoryEntryDTO.class);

        AnnotationMappingDiscoverer discoverer = new AnnotationMappingDiscoverer(RequestMapping.class);
        try {
            String mapping = discoverer.getMapping(PlantInventoryEntryRestController.class,
                    PlantInventoryEntryRestController.class.getMethod("show", Long.class));

            uriTemplate = new UriTemplate(mapping);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public PlantInventoryEntryID resolveId(Link link) {
        return PlantInventoryEntryID.of(Long.parseLong(uriTemplate.match(link.getHref()).get("id")));
    }

    @Override
    public PlantInventoryEntryDTO toResource(PlantInventoryEntry plantInventoryEntry) {
        PlantInventoryEntryDTO dto = createResourceWithId(plantInventoryEntry.getId().getId(), plantInventoryEntry);
       // dto.set_id(plantInventoryEntry.getId().getId());
        dto.setName(plantInventoryEntry.getName());
        dto.setDescription(plantInventoryEntry.getDescription());
        dto.setPrice(plantInventoryEntry.getPrice());
       // dto.setXlinks(dto.getLinks());
        dto.set_links(dto.getLinks());
         return dto;
    }

    public PlantInventoryEntryDTO toResource(PlantInventoryEntryID plantInfo) {
        return toResource(entryRepository.getOne(plantInfo));
    }
}

