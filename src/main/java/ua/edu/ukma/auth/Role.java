package ua.edu.ukma.auth;

public enum Role {
    USER(
            Permission.VIEW_FACULTIES
                    | Permission.VIEW_DEPARTMENTS
                    | Permission.VIEW_SPECIALTIES
                    | Permission.VIEW_STUDENTS
                    | Permission.VIEW_TEACHERS
                    | Permission.VIEW_REPORTS
    ),

    MANAGER(
            Permission.VIEW_FACULTIES | Permission.EDIT_FACULTIES
                    | Permission.VIEW_DEPARTMENTS | Permission.EDIT_DEPARTMENTS
                    | Permission.VIEW_SPECIALTIES | Permission.EDIT_SPECIALTIES
                    | Permission.VIEW_STUDENTS | Permission.EDIT_STUDENTS
                    | Permission.VIEW_TEACHERS | Permission.EDIT_TEACHERS
                    | Permission.VIEW_REPORTS
    ),

    ADMIN(
            Permission.VIEW_FACULTIES | Permission.EDIT_FACULTIES
                    | Permission.VIEW_DEPARTMENTS | Permission.EDIT_DEPARTMENTS
                    | Permission.VIEW_SPECIALTIES | Permission.EDIT_SPECIALTIES
                    | Permission.VIEW_STUDENTS | Permission.EDIT_STUDENTS
                    | Permission.VIEW_TEACHERS | Permission.EDIT_TEACHERS
                    | Permission.VIEW_REPORTS
                    | Permission.EDIT_UNIVERSITY
                    | Permission.MANAGE_USERS
    );

    private final int defaultPermissions;

    Role(int defaultPermissions) {
        this.defaultPermissions = defaultPermissions;
    }

    public int getDefaultPermissions() {
        return defaultPermissions;
    }
}