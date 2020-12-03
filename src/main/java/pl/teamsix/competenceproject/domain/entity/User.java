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

    /*------------------------ METHODS REGION ------------------------*/
    public User(String lastName, String firstName, int age, char gender,
                ArrayList interests, String profile, String phoneNumber) {
        super(lastName, firstName, age, gender, interests, profile, phoneNumber);
    }

    public String getHashedId() {
        return hashedId;
    }

    public void setHashedId(String hashedId) {
        this.hashedId = hashedId;
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
                .append(hashedId, user.hashedId)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(hashedId)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("hashedId", hashedId)
                .toString();
    }
}
