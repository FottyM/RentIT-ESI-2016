package com.rentit.inventory.application.dto;


import com.rentit.common.rest.ResourceSupport;
import lombok.Data;
import org.springframework.hateoas.Link;


import java.math.BigDecimal;
import java.util.List;

@Data
public class PlantInventoryEntryDTO extends ResourceSupport {
   // Long _id;
    String name;
    String description;
    BigDecimal price;
    //List<Link>xlinks;
   List<Link>_links;
}
