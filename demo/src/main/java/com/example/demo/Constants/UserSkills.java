package com.example.demo.Constants;

public enum UserSkills {
    INFORMATION_TECHNOLOGY("Information technology"),
    MARKETING("Marketing"),
    MANAGEMENT("Management"),
    BUSINESS_DEVELOPMENT("Business development"),
    HUMAN_RESOURCES("Human resources"),
    FINANCE("Finance"),
    EDUCATION("Education"),
    TOURISM("Tourism"),
    SALES("Sales"),
    MEDIA("Media");

    private final String value;

    UserSkills(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static UserSkills fromValue(String value) {
        for (UserSkills skills : values()) {
            if (skills.value.equalsIgnoreCase(value)) {
                return skills;
            }
        }
        throw new IllegalArgumentException("Грешно потребителско умение: " + value);
    }
}
