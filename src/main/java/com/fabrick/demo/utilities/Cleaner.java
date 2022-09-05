package com.fabrick.demo.utilities;

import org.springframework.web.client.HttpServerErrorException;

public class Cleaner {

    public static String cleanFabrickError(HttpServerErrorException error) {
        String message = error.getMessage();
        if (error == null || message == null || message.length() == 0) return "";

        message = message.replace("500 Internal Server Error: ", "");
        message = message.replace("<EOL>", "");
        message = message.substring(1, message.length() - 1);
        return message;
    }
}
