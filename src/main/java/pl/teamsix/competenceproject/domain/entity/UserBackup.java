package pl.teamsix.competenceproject.domain.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Document
public class UserBackup {

    /*------------------------ FIELDS REGION ------------------------*/
    private String lastName;
    private String firstName;
    private int age;
    private char gender; // 'F' or 'M'
    private ArrayList interests;
    private String profile;
    private String phoneNumber;

    /*------------------------ METHODS REGION ------------------------*/
    public UserBackup(String lastName, String firstName, int age, char gender,
                      ArrayList interests, String profile, String phoneNumber) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.age = age;
        this.gender = gender;
        this.interests = interests;
        this.profile = profile;
        this.phoneNumber = phoneNumber;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserBackup that = (UserBackup) o;

        return new EqualsBuilder()
                .append(age, that.age)
                .append(gender, that.gender)
                .append(lastName, that.lastName)
                .append(interests, that.interests)
                .append(profile, that.profile)
                .append(phoneNumber, that.phoneNumber)
                .append(firstName, that.firstName)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(lastName)
                .append(age)
                .append(gender)
                .append(interests)
                .append(profile)
                .append(phoneNumber)
                .append(firstName)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("lastName", lastName)
                .append("age", age)
                .append("gender", gender)
                .append("interests", interests)
                .append("profile", profile)
                .append("phoneNumber", phoneNumber)
                .append("firstName", firstName)
                .toString();
    }
}
