package com.rentit.sales.domain.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

import javax.persistence.Embeddable;

@Embeddable
@Value
@NoArgsConstructor(access=AccessLevel.PROTECTED, force=true)
@AllArgsConstructor(staticName="of")
public class ContactPerson {
	String name;
	String email;
}
