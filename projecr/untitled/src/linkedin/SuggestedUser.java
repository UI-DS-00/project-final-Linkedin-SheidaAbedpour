package linkedin;

import main.Main;
import user.User;

import java.util.ArrayList;
import java.util.Map;

public class SuggestedUser {

    private final User user;
    private final Map<String, Integer> priority;
    private Map<User, Integer> scores;

    public SuggestedUser(User user, Map<String,Integer> priority) {
        this.user = user;
        this.priority = priority;
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

    private int getScore(double similarity, int priority) {
        int similarityScore = (int) (similarity / 25);      // 0 - 4
        return similarityScore * priority;                  // 0 - 20
    }

    private int totalScore(User connecting) {
        return specialityScore(user,connecting) + universityLocationScore(user,connecting) +
                fieldScore(user,connecting) + workplaceScore(user,connecting);
    }

    private void rateUsers() {
        for (User u: Main.users) {
            int totalScore = totalScore(u);
            if (totalScore > 50)
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
            for (int j = 0; j < list.size() - i - 1; j++)
                if (scores.get(list.get(j)) < scores.get(list.get(j + 1)) ) {
                    User temp = list.get(i);
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
