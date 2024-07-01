/**
 * 
 */
package com.rosenzest.base.annotation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

import com.rosenzest.base.enums.EnumUtils;
import com.rosenzest.base.enums.IEnum;

import cn.hutool.core.convert.Convert;

/**
 * 校验枚举值有效性
 */
@Documented
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(EnumValue.List.class)
@Constraint(validatedBy = EnumValue.Validator.class)
public @interface EnumValue {

    String message() default "参数不合法";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class<? extends IEnum<? extends Enum<?>>> enumClass();

    /**
     * Defines several {@code @EnumValue} constraints on the same element.
     *
     * @see EnumValue
     */
    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
    @Retention(RUNTIME)
    @Documented
    public @interface List {
        EnumValue[] value();
    }

    class Validator implements ConstraintValidator<EnumValue, Object> {

        private Class<? extends IEnum<? extends Enum<?>>> enumClass;

        @Override
        public void initialize(EnumValue enumValue) {
            enumClass = enumValue.enumClass();
        }

        @Override
        public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
            if (value == null) {
                return Boolean.TRUE;
            }

            if (enumClass == null) {
                return Boolean.TRUE;
            }

            try {
                return EnumUtils.init(enumClass).isInEnum(Convert.toStr(value));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
