package pl.teamsix.competenceproject.domain.entity;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class User extends BaseEntity {
    private String firstName;
    private String secondName;
    private int age;
    private String gender;
    private String interest;
    private String profile;
    private String phoneNumber;
}
