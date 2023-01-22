package linkedin;

import main.Main;
import user.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SuggestedUser {

    private final User user;
    private final Map<String, Integer> priority;
    private final Map<User, Integer> scores = new HashMap<>();
    private Map<User, Integer> connections = new HashMap<>();

    public SuggestedUser(User user, Map<String,Integer> priority, Map<User,Integer> connections) {
        this.user = user;
        this.priority = priority;
        this.connections = connections;
    }

    public void setConnections(Map<User,Integer> connections) {
        this.connections = connections;
    }

    public Map<User,Integer> getConnections() {
        return connections;
    }

    private int specialityScore(User user, User connecting) {

        int prioritySpeciality = priority.get("specialities");
        double similarity = Similarity.specialtiesPercent(user, connecting);

        return getScore(similarity, prioritySpeciality);

    }


    private int universityLocationScore(User user, User connecting) {

        int prioritySpeciality = priority.get("universityLocation");
        double similarity = Similarity.universityLocationPercent(user, connecting);

        return getScore(similarity, prioritySpeciality);

    }

    private int fieldScore(User user, User connecting) {

        int prioritySpeciality = priority.get("field");
        double similarity = Similarity.fieldPercent(user, connecting);

        return getScore(similarity, prioritySpeciality);

    }

    private int workplaceScore(User user, User connecting) {

        int prioritySpeciality = priority.get("workplace");
        double similarity = Similarity.workplacePercent(user, connecting);

        return getScore(similarity, prioritySpeciality);

    }

    private int levelScore(User connecting) {

        int priorityLevel = priority.get("level");

        if (connections.get(connecting) >= 20)
            return priorityLevel * 5;

        return (20 - connections.get(connecting)) * priorityLevel;

    }

    private int getScore(double similarity, int priority) {
        return (int) similarity * priority;
    }

    private int totalScore(User connecting) {
        return specialityScore(user,connecting) + universityLocationScore(user,connecting) +
                fieldScore(user,connecting) + workplaceScore(user,connecting) +
                levelScore(connecting);
    }

    private void rateUsers() {
        for (User u: connections.keySet()) {
            int totalScore = totalScore(u);
            if (totalScore >= 300)
                scores.put(u, totalScore);
        }
    }

    public Map<User, Integer> getScores() {
        return scores;
    }

    public void setScores(Map<User, Integer> scores) {
        rateUsers();
    }

    private ArrayList<User> sort() {

        ArrayList<User> list = new ArrayList<>(scores.keySet());

        for (int i = 0; i < list.size() - 1; i++)
            for (int j = 0; j < list.size() - 1; j++)
                if (scores.get(list.get(j)) < scores.get(list.get(j + 1)) ) {
                    User temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                }

        return list;
    }

    public ArrayList<User> suggestedUsers() {
        rateUsers();
        return sort();
    }

}
