package pl.teamsix.competenceproject.domain.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Document
public class User extends UserBackup {

    /*------------------------ FIELDS REGION ------------------------*/
    private String hashedId;
    private boolean isAlreadyAnonymizated;

    /*------------------------ METHODS REGION ------------------------*/
    public User(String firstName, String lastName, int age, char gender,
                ArrayList interests, String profile, String phoneNumber) {
        super(firstName, lastName, age, gender, interests, profile, phoneNumber);
        this.isAlreadyAnonymizated = false;
    }

    public String getHashedId() {
        return hashedId;
    }

    public void setHashedId(String hashedId) {
        this.hashedId = hashedId;
    }

    public boolean isAlreadyAnonymizated() {
        return isAlreadyAnonymizated;
    }

    public void setAlreadyAnonymizated(boolean alreadyAnonymizated) {
        isAlreadyAnonymizated = alreadyAnonymizated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        User user = (User) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(isAlreadyAnonymizated, user.isAlreadyAnonymizated)
                .append(hashedId, user.hashedId)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(hashedId)
                .append(isAlreadyAnonymizated)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("hashedId", hashedId)
                .append("isAlreadyAnomizated", isAlreadyAnonymizated)
                .toString();
    }
}
