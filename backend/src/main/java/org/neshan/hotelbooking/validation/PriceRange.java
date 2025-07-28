package org.neshan.hotelbooking.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PriceRangeValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PriceRange {
    String message() default "Maximum price must be greater than minimum price";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    
    String priceFrom() default "priceFrom";
    String priceTo() default "priceTo";
}