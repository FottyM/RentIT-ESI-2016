package com.rentit.sales.application.dto;


import lombok.Data;
import org.springframework.hateoas.ResourceSupport;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Created by rain on 27.04.16.
 */
@Data
public class InvoiceDTO extends ResourceSupport {
    Long _id;
    BigDecimal total;
    String poLink;
    InvoiceStatus status;
    LocalDate reminderReceived;
}
