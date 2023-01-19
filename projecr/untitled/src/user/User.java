package user;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class User {

    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("dateOfBirth")
    private String dateOfBirth;
    @JsonProperty("email")
    private String email;

    @JsonProperty("universityLocation")
    private String universityLocation;
    @JsonProperty("field")
    private String field;
    @JsonProperty("workplace")
    private String workplace;

    @JsonProperty("specialties")
    private ArrayList<String> specialties;

    @JsonProperty("connectionId")
    private ArrayList<String> connectionId;


    public User(){}

    public User(String id, String name, String dateOfBirth, String email,
                String universityLocation, String field, String workplace,
                ArrayList<String> specialties, ArrayList<String> connectionId) {

        this.id = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.universityLocation = universityLocation;
        this.field = field;
        this.workplace = workplace;
        this.specialties = specialties;
        this.connectionId = connectionId;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUniversityLocation() {
        return universityLocation;
    }

    public void setUniversityLocation(String universityLocation) {
        this.universityLocation = universityLocation;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getWorkplace() {
        return workplace;
    }

    public void setWorkplace(String workplace) {
        this.workplace = workplace;
    }

    public ArrayList<String> getSpecialties() {
        return specialties;
    }

    public void setSpecialties(ArrayList<String> specialties) {
        this.specialties = specialties;
    }

    public ArrayList<String> getConnectionId() {
        return connectionId;
    }

    public void setConnectionId(ArrayList<String> connectionId) {
        this.connectionId = connectionId;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", email='" + email + '\'' +
                ", universityLocation='" + universityLocation + '\'' +
                ", field='" + field + '\'' +
                ", workplace='" + workplace + '\'' +
                ", specialties=" + specialties.toString() +
                ", connectionId=" + connectionId.toString() +
                '}';
    }

}
