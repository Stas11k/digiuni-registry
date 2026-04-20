package ua.edu.ukma.network;
public record Response(boolean success, String message, Object data) {
    public static Response ok(String message, Object data) {
        return new Response(true, message, data);
    }

    public static Response ok(String message) {
        return new Response(true, message, null);
    }

    public static Response error(String message) {
        return new Response(false, message, null);
    }
}
