package org.example.elite_driving_school_management_system.util;

public class RegexValidator {
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";
    private static final String CONTACT_REGEX = "^(\\d+)$";

    public static boolean isValidEmail(String email) {
        return email != null && email.matches(EMAIL_REGEX);
    }

    public static boolean isValidContact(String contact) {
        return contact != null && contact.matches(CONTACT_REGEX);
    }
}
