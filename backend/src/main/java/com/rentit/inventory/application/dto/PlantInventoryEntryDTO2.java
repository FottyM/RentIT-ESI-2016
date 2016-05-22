package com.rentit.inventory.application.dto;


import com.rentit.common.rest.ResourceSupport;
import lombok.Data;
import org.springframework.hateoas.Link;


import java.math.BigDecimal;
import java.util.List;

@Data
public class PlantInventoryEntryDTO2 {
    String name;
    String description;
    BigDecimal price;
    List<Link>links;
}
