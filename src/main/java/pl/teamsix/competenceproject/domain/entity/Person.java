package pl.teamsix.competenceproject.domain.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Person extends BaseEntity {

    /*------------------------ FIELDS REGION ------------------------*/
    private String phoneNumber;
    private Profile personProfile;

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

    /*------------------------ METHODS REGION ------------------------*/
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Person person = (Person) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(phoneNumber, person.phoneNumber)
                .append(personProfile, person.personProfile)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(phoneNumber)
                .append(personProfile)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("phoneNumber", phoneNumber)
                .append("personProfile", personProfile)
                .toString();
    }
}
