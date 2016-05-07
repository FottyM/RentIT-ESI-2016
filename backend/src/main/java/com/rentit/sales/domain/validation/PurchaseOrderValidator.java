package com.rentit.sales.domain.validation;

import com.rentit.common.domain.model.BusinessPeriod;
import com.rentit.common.domain.validation.BusinessPeriodValidator;
import com.rentit.sales.domain.model.ContactPerson;
import com.rentit.sales.domain.model.POStatus;
import com.rentit.sales.domain.model.PurchaseOrder;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Created by lgbanuelos on 10/03/16.
 */
public class PurchaseOrderValidator implements Validator {
    private final BusinessPeriodValidator periodValidator;

    public PurchaseOrderValidator(BusinessPeriodValidator periodValidator,
                                  ContactPersonValidator contactValidator) {
        if (periodValidator == null) {
            throw new IllegalArgumentException("The supplied [Validator] is " +
                    "required and must not be null.");
        }
        if (!periodValidator.supports(BusinessPeriod.class)) {
            throw new IllegalArgumentException("The supplied [Validator] must " +
                    "support the validation of [BusinessPeriod] instances.");
        }
        if (contactValidator == null) {
            throw new IllegalArgumentException("The supplied [Validator] is " +
                    "required and must not be null.");
        }
        if (!contactValidator.supports(ContactPerson.class)) {
            throw new IllegalArgumentException("The supplied [Validator] must " +
                    "support the validation of [ContactPerson] instances.");
        }

        this.periodValidator = periodValidator;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return PurchaseOrder.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        PurchaseOrder order = (PurchaseOrder)o;
        if (order.getId() == null)
            errors.reject("id", "Purchase Order id cannot be null");
        if (order.getPlant() == null)
            errors.reject("plant", "Reference to 'PlantInventoryEntry' cannot be null");

        if (!order.getStatus().equals(POStatus.PENDING)) {
            if (order.getReservations().size() == 0)
                errors.reject("reservations", "At least one reference to 'PlantReservation' is required");
            if (order.getTotal() == null)
                errors.rejectValue("total", "Purchase order's total cannot be null");
            else if (order.getTotal().signum() != 1)
                errors.rejectValue("total", "Purchase order's total must be a positive value");
        }

        errors.pushNestedPath("rentalPeriod");
        ValidationUtils.invokeValidator(periodValidator, order.getRentalPeriod(), errors);
        errors.popNestedPath();
    }
}
