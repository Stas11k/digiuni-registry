package ua.edu.ukma.network;

import java.util.Map;

public record Request(String command, String login, String password, String token, Map<String, String> params) {}