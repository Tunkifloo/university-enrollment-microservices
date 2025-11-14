package com.university.common.constants;

public final class EventTypes {
    private EventTypes() {}

    // Auth events
    public static final String USER_REGISTERED = "USER_REGISTERED";
    public static final String USER_LOGIN = "USER_LOGIN";

    // Faculty events
    public static final String FACULTY_CREATED = "FACULTY_CREATED";
    public static final String FACULTY_UPDATED = "FACULTY_UPDATED";
    public static final String FACULTY_DELETED = "FACULTY_DELETED";

    // Career events
    public static final String CAREER_CREATED = "CAREER_CREATED";
    public static final String CAREER_UPDATED = "CAREER_UPDATED";
    public static final String CAREER_DELETED = "CAREER_DELETED";
}