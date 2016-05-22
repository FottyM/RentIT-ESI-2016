package com.rentit.sales.application.dto;

import com.rentit.common.application.dto.BusinessPeriodDTO;
import com.rentit.common.rest.ResourceSupport;
import com.rentit.inventory.application.dto.PlantInventoryEntryDTO;
import com.rentit.inventory.application.dto.PlantInventoryEntryDTO2;
import com.rentit.sales.domain.model.POStatus;
import com.rentit.sales.domain.model.PurchaseOrderExtension;
import com.rentit.sales.domain.model.PurchaseOrderExtensionID;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor(force = true)
public class PurchaseOrderDTO2 extends ResourceSupport {
    //  Long _id;
    PlantInventoryEntryDTO2 plant;
    BusinessPeriodDTO rentalPeriod;
    BigDecimal total;
    POStatus status;
    String email;
    List<PurchaseOrderExtensionID>extensions;

}
