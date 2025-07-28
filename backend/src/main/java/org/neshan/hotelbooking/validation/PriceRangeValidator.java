package org.neshan.hotelbooking.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

public class PriceRangeValidator implements ConstraintValidator<PriceRange, Object> {
    
    private String priceFromFieldName;
    private String priceToFieldName;
    
    @Override
    public void initialize(PriceRange constraintAnnotation) {
        this.priceFromFieldName = constraintAnnotation.priceFrom();
        this.priceToFieldName = constraintAnnotation.priceTo();
    }
    
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        Double priceFrom = (Double) new BeanWrapperImpl(value).getPropertyValue(priceFromFieldName);
        Double priceTo = (Double) new BeanWrapperImpl(value).getPropertyValue(priceToFieldName);
        
        if (priceFrom == null || priceTo == null) {
            return true; // Let @NotNull handle null validation
        }
        
        return priceTo > priceFrom;
    }
}