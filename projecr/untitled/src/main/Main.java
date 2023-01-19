package main;

import com.fasterxml.jackson.databind.ObjectMapper;
import user.User;

import java.io.IOException;
import java.nio.file.Paths;


public class Main {

    public static void main(String[] args) {

        User[] users = readUsers();

    }

    private static User[] readUsers() {

        ObjectMapper objectMapper = new ObjectMapper();

        try {

            return objectMapper.readValue(Paths.get("users.json").toFile(), User[].class);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}