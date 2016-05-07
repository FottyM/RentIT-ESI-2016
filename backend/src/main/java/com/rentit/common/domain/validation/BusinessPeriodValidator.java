package com.rentit.common.domain.validation;

import com.rentit.common.domain.model.BusinessPeriod;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class BusinessPeriodValidator implements Validator {

    public boolean supports(Class<?> clazz) {
        return BusinessPeriod.class.equals(clazz);
    }

    public void validate(Object object, Errors errors) {
        BusinessPeriod period = (BusinessPeriod) object;

        if (period.getStartDate() == null)
            errors.rejectValue("startDate", "'Start date' in Business Period cannot be NULL");

        if (period.getStartDate() == null)
            errors.rejectValue("endDate", "'End date' in Business Period cannot be NULL");

        if (period.getEndDate().isBefore(period.getStartDate())) {
            errors.rejectValue("startDate", "'Start date' happens after 'end date'");
            errors.rejectValue("endDate", "'End date' happens before 'start date'");
        }
    }
}
