package org.neshan.hotelbooking.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

import java.time.LocalDateTime;

public class DateRangeValidator implements ConstraintValidator<DateRange, Object> {
    
    private String dateFromFieldName;
    private String dateToFieldName;
    
    @Override
    public void initialize(DateRange constraintAnnotation) {
        this.dateFromFieldName = constraintAnnotation.dateFrom();
        this.dateToFieldName = constraintAnnotation.dateTo();
    }
    
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        LocalDateTime dateFrom = (LocalDateTime) new BeanWrapperImpl(value).getPropertyValue(dateFromFieldName);
        LocalDateTime dateTo = (LocalDateTime) new BeanWrapperImpl(value).getPropertyValue(dateToFieldName);
        
        if (dateFrom == null || dateTo == null) {
            return true; // Let @NotNull handle null validation
        }
        
        return dateTo.isAfter(dateFrom);
    }
}