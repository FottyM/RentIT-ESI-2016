package com.rentit.inventory.application.dto;


import lombok.Data;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import java.math.BigDecimal;
import java.util.List;

@Data
public class PlantInventoryEntryDTO extends ResourceSupport {
   // Long _id;
    String name;
    String description;
    BigDecimal price;
    List<Link>xlinks;
}
