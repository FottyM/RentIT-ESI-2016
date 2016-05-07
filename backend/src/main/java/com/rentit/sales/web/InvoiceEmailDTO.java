package com.rentit.sales.web;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class InvoiceEmailDTO {
    Long poId;
    String email;
    BigDecimal total;
}
