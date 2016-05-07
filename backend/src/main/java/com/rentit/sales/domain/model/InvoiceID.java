package com.rentit.sales.domain.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;
import javax.persistence.Embeddable;
import java.io.Serializable;
@Embeddable
@Value
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
@AllArgsConstructor(staticName = "of")
public class InvoiceID implements Serializable {
    Long id;
}
