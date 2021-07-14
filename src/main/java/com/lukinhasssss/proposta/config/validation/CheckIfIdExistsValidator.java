package com.lukinhasssss.proposta.config.validation;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CheckIfIdExistsValidator implements ConstraintValidator<CheckIfIdExists, Object> {

    private String domainAttribute;
    private Class<?> klass;

    @PersistenceContext
    private EntityManager em;

    @Override
    public void initialize(CheckIfIdExists params) {
        domainAttribute = params.fieldName();
        klass = params.domainClass();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null)
            return true;

        return !em.createQuery("select 1 from "+ klass.getName() + " where " + domainAttribute + " = :pValue")
                .setParameter("pValue",value)
                .getResultList()
                .isEmpty();
    }
}
