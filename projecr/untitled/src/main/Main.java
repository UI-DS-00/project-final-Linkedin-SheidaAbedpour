package main;

import com.fasterxml.jackson.databind.ObjectMapper;
import graph.AdjacencyMapGraph;
import linkedin.LinkedIn;
import user.User;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class Main {

    public static User[] users;
    private static AdjacencyMapGraph<user.User,Integer> adjacencyMapGraph;
    private static User user;

    public static void main(String[] args) {

        users = readUsers();

        adjacencyMapGraph = createGraph(users);

        showMenu();
    }

    private static void showMenu() {

        System.out.println("1.login / 2.show list of all users / 3.search user / 4.exit");
        Scanner scanner = new Scanner(System.in);
        int command = scanner.nextInt();

        while (command != 4) {
            switch (command) {
                case 1:
                    login();
                    break;
                case 2:
                    System.out.println("users: ");
                    for (User user : users)
                        System.out.println(user);
                    System.out.println("----------------------------------------------------------------------------------");
                    System.out.println();
                    break;
                case 3:
                    search();
                    break;
            }

            System.out.println();
            System.out.println("1.login / 2.show list of all users / 3.search user / 4.exit");
            command = scanner.nextInt();
        }

    }

    private static void login() {
        System.out.println("enter your id: ");
        Scanner scanner = new Scanner(System.in);
        String id = scanner.next();
        user = getUser(id);

        if (user == null) {
            System.out.println("incorrect id. try again. ");
            showMenu();
        }

        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.println(user);
        System.out.println();
        System.out.println("*suggested users for you*");

        Map<String, Integer> priority = new HashMap<>();
        priority.put("specialities",5);
        priority.put("universityLocation", 1);
        priority.put("field", 1);
        priority.put("workplace", 1);

        ArrayList<User> suggested = getSuggested(user, priority);
        for (User sug: suggested)
            System.out.println(sug);
        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.println();

        // choose priority
        // connect others
        // log out

    }

    private static void search() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("enter id : ");
        String search = scanner.next();
        User searched = getUser(search);
        System.out.println(searched);
        System.out.println();
        System.out.println("connect? : 1.yes  2.no ");
        int connect = scanner.nextInt();
        if (connect == 1) {
            while (user == null) {
                System.out.println("you have to login first! id: ");
                user = getUser(scanner.next());
            }
            connect(user, searched);
        }
    }

    private static void connect(User user, User connect) {
        adjacencyMapGraph.insertEdge(adjacencyMapGraph.getVertex(user),adjacencyMapGraph.getVertex(connect),null);
    }

    private static ArrayList<User> getSuggested(User user, Map<String, Integer> priority) {
        LinkedIn linkedIn = new LinkedIn(user,adjacencyMapGraph);
        return linkedIn.suggestedUsers(priority);
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
                   User opposite = getUser(id);
                   adjacencyMapGraph.insertEdge(adjacencyMapGraph.getVertex(user), adjacencyMapGraph.getVertex(opposite),null);
               }catch (IllegalArgumentException ignored) {}
           }

        }

        return adjacencyMapGraph;
    }

    public static User getUser(String id) {
        for (User user: users)
            if (user.getId().equals(id))
                return user;
        return null;
    }

}