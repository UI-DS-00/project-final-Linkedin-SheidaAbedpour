package main;

import com.fasterxml.jackson.databind.ObjectMapper;
import graph.AdjacencyMapGraph;
import graph.InnerVertex;
import user.User;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;


public class Main {

    public static void main(String[] args) {

        User[] users = readUsers();

        AdjacencyMapGraph<User,?> adjacencyMapGraph = createGraph(users);
    }

    private static User[] readUsers() {

        ObjectMapper objectMapper = new ObjectMapper();

        try {

            return objectMapper.readValue(Paths.get("users.json").toFile(), User[].class);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    private static<V,E> AdjacencyMapGraph<User, E> createGraph(User[] users) {

        AdjacencyMapGraph<User,E> adjacencyMapGraph = new AdjacencyMapGraph<>();

        for (User user: users)
            adjacencyMapGraph.insertVertex(user);

        for (User user: users) {

           ArrayList<String> connectionIds = user.getConnectionId();

           for (String id: connectionIds) {
               try {
                   User opposite = getUser(id, users);
                   adjacencyMapGraph.insertEdge(adjacencyMapGraph.getVertex(user), adjacencyMapGraph.getVertex(opposite),null);
               }catch (IllegalArgumentException ignored) {}
           }

        }

        return adjacencyMapGraph;
    }

    private static User getUser(String id, User[] users) {
        for (User user: users)
            if (user.getId().equals(id))
                return user;
        return null;
    }

}