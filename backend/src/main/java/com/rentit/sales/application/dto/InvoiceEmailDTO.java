package com.rentit.sales.application.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class InvoiceEmailDTO {
    Long poId;
    String email;
    BigDecimal total;
}
