package com.lukinhasssss.proposta.config.validation;

import javax.validation.Constraint;
import javax.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = {CheckIfIdExistsValidator.class})
@Target({FIELD})
@Retention(RUNTIME)
public @interface CheckIfIdExists {

    String message() default "Este id não existe";

    Class<?> [] groups() default { };

    Class<? extends Payload>[] payload() default { };

    String fieldName();

    Class<?> domainClass();
}
