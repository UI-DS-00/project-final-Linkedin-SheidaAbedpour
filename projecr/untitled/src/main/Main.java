package main;

import com.fasterxml.jackson.databind.ObjectMapper;
import graph.AdjacencyMapGraph;
import linkedin.LinkedIn;
import user.User;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;


public class Main {

    public static ArrayList<User> users;
    private static AdjacencyMapGraph<user.User,Integer> adjacencyMapGraph;
    private static User user;

    public static void main(String[] args) {

        User[] list = readUsers();
        users.addAll(Arrays.asList(list));

        adjacencyMapGraph = createGraph(list);

        showMenu();
    }

    private static void showMenu() {

        System.out.println("1.login / 2.show list of all users / 3.search user / 4.sing up / 5.exit");
        Scanner scanner = new Scanner(System.in);
        int command = scanner.nextInt();

        while (command != 5) {
            switch (command) {
                case 1:
                    login();
                    break;
                case 2:
                    System.out.println("users: ");
                    for (User user : users)
                        System.out.println(user);
                    System.out.println();
                    break;
                case 3:
                    search();
                    break;
                case 4:
                    singUp();
                    login();
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

        System.out.println("set priority? 1.yes 2.no ");
        int givePriority = scanner.nextInt();
        if (givePriority == 1) {
            setPriority(priority);
            suggested = getSuggested(user, priority);
            for (User sug: suggested)
                System.out.println(sug);
        }

        showMenu();
    }

    private static void setPriority(Map<String, Integer> priority) {

        System.out.println("rate 1 to 5 : ");
        System.out.println("specialities / universityLocation / field / workplace");
        Scanner scanner = new Scanner(System.in);

        priority.put("specialities",scanner.nextInt());
        priority.put("universityLocation", scanner.nextInt());
        priority.put("field", scanner.nextInt());
        priority.put("workplace", scanner.nextInt());
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

    private static void singUp() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("id : ");
        String id = scanner.next();

        boolean repeated = false;
        do {
            repeated = false;
            for (User u : users)
                if (u.getId().equals(id)) {
                    repeated = true;
                    System.out.println("try another id");
                    id = scanner.next();
                    break;
                }
        }while (repeated);

        System.out.println("name / dateOfBirth / email / universityLocation / field / workplace");
        User user = new User(id, scanner.next(), scanner.next(), scanner.next(), scanner.next(), scanner.next(),
                scanner.next(), new ArrayList<>(), new ArrayList<>());


        System.out.println("num of specialities : ");
        int size = scanner.nextInt();
        for (int i = 0; i < size; i++)
            user.getSpecialties().add(scanner.next());

        adjacencyMapGraph.insertVertex(user);
        users.add(user);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(new FileOutputStream("users.json"),user);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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