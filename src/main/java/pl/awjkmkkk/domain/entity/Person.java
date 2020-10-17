package pl.awjkmkkk.domain.entity;

import pl.awjkmkkk.domain.entity.type.PointType;

public class Person extends BaseEntity {

    public enum Profile {

        STUDENT("student"),
        TEACHER("teacher"),
        SERVICE_STAFF("service staff");

        private final String name;

        Profile(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

    }

    private String phoneNumber;
    private Profile personProfile;

    public Person(String phoneNumber, Profile personProfile) {
        this.phoneNumber = phoneNumber;
        this.personProfile = personProfile;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Profile getPersonProfile() {
        return personProfile;
    }

    public void setPersonProfile(Profile personProfile) {
        this.personProfile = personProfile;
    }

}
