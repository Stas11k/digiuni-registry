package ua.edu.ukma.auth;

public final class Permission {
    private Permission() {
    }

    public static final int VIEW_FACULTIES      = 1 << 0;
    public static final int EDIT_FACULTIES      = 1 << 1;

    public static final int VIEW_DEPARTMENTS    = 1 << 2;
    public static final int EDIT_DEPARTMENTS    = 1 << 3;

    public static final int VIEW_SPECIALTIES    = 1 << 4;
    public static final int EDIT_SPECIALTIES    = 1 << 5;

    public static final int VIEW_STUDENTS       = 1 << 6;
    public static final int EDIT_STUDENTS       = 1 << 7;

    public static final int VIEW_TEACHERS       = 1 << 8;
    public static final int EDIT_TEACHERS       = 1 << 9;

    public static final int VIEW_REPORTS        = 1 << 10;
    public static final int EDIT_UNIVERSITY     = 1 << 11;
    public static final int MANAGE_USERS        = 1 << 12;

    public static boolean has(int permissions, int flag) {
        return (permissions & flag) == flag;
    }

    public static int add(int permissions, int flag) {
        return permissions | flag;
    }

    public static int remove(int permissions, int flag) {
        return permissions & ~flag;
    }

    public static int toggle(int permissions, int flag) {
        return permissions ^ flag;
    }
}