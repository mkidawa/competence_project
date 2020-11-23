package pl.teamsix.competenceproject.domain.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.UUID;

@Document
public class User extends BaseEntity {
    private String id;
    private String firstName;
    private String lastName;
    private int age;
    private char gender; // 'F' or 'M'
    private ArrayList interests;
    private String profile;
    private String phoneNumber;


    public User(String firstName, String lastName, int age, char gender, ArrayList interests, String profile, String phoneNumber) {
        id = UUID.randomUUID().toString();
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
        this.interests = interests;
        this.profile = profile;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", gender=" + gender +
                ", interests=" + interests +
                ", profile='" + profile + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String getId() {
        return id;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public ArrayList getInterests() {
        return interests;
    }

    public void setInterests(ArrayList interest) {
        this.interests = interest;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
