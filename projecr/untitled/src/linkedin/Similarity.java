package linkedin;

import user.User;

public class Similarity {

    public static double specialtiesPercent(User user1, User user2) {

        double similarity = 0;

        for (String specialty1: user1.getSpecialties())
            for (String speciality2: user2.getSpecialties()) {

                if (specialty1.equals(speciality2)) {

                    similarity++;

                }

            }

        double total = user1.getSpecialties().size();
        return (similarity / total) * 100;
    }


    public static double universityLocationPercent(User user1, User user2) {

        double similarity = 0;

        if (user1.getUniversityLocation().equals(user2.getUniversityLocation()))
            similarity += 0.5;

        return similarity * 100;
    }


    public static double fieldPercent(User user1, User user2) {

        double similarity = 0;

        if (user1.getField().equals(user2.getField()))
            similarity += 0.5;

        return similarity * 100;
    }

    public static double workplacePercent(User user1, User user2) {

        double similarity = 0;

        if (user1.getWorkplace().equals(user2.getWorkplace()))
            similarity += 0.5;

        return similarity * 100;
    }

}
