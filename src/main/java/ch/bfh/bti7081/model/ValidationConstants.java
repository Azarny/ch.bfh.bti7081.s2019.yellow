package ch.bfh.bti7081.model;

/**
 * These are model-wide constants for validation.
 * They can be reused by other parts of the product.
 *
 * @author luscm1
 */
public enum ValidationConstants {
    MAX_YEARS_IN_FUTURE(5),
    MIN_TITLE_LENGTH(4),
    MIN_DESCRIPTION_LENGTH(10),
    MIN_STREET_LENGTH(2),
    MIN_STREETNUMBER_LENGTH(1),
    MIN_LOCATION_LENGTH(2);

    public final int value;

    ValidationConstants(int value) {
        this.value = value;
    }
}