package com.corebanking.engine.domain.model.valueobject;

import java.util.Objects;

public final class FullName {

    private final String firstName;
    private final String lastName;

    private FullName(String firstName, String lastName) {

        if (firstName == null || firstName.isBlank())
            throw new IllegalArgumentException("First name cannot be empty");

        if (lastName == null || lastName.isBlank())
            throw new IllegalArgumentException("Last name cannot be empty");

        String f = firstName.trim();
        String l = lastName.trim();

        if (f.length() > 50 || l.length() > 50)
            throw new IllegalArgumentException("Name length exceeds limit");

        this.firstName = capitalize(f);
        this.lastName  = capitalize(l);
    }

    public static FullName of(String firstName, String lastName) {
        return new FullName(firstName, lastName);
    }

    private static String capitalize(String value) {
        return value.substring(0,1).toUpperCase() + value.substring(1).toLowerCase();
    }

    public String firstName() { return firstName; }
    public String lastName()  { return lastName; }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FullName)) return false;
        FullName other = (FullName) o;
        return firstName.equals(other.firstName) &&
               lastName.equals(other.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }
}
