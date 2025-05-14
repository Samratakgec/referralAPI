package com.samrat.referralAPI.models;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class User {
    @Id
    private ObjectId id; // MongoDB ObjectId

    @Field("email")
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    private String refGain;
    private String refGen;

    private ObjectId profileId;  // Reference to Profile's ObjectId

    private Role role;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRefGain() {
        return refGain;
    }

    public void setRefGain(String refGain) {
        this.refGain = refGain;
    }

    public String getRefGen() {
        return refGen;
    }

    public void setRefGen(String refGen) {
        this.refGen = refGen;
    }

    public ObjectId getProfileId() {
        return profileId;
    }

    public void setProfileId(ObjectId profileId) {
        this.profileId = profileId;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
