package com.elearn.app.start_learn_back.dtos;

import jakarta.persistence.Column;
import lombok.Data;

import java.util.Date;
@Data
public class UserDTO {
    private String userId;

    private String name;

    // username=email
    private String email;

    private String phoneNumber;

    private String password;

    private String about;

    private boolean active;

    private boolean emailVerified;

    private boolean smsVerified;

    private Date createAt;

    private String profilePath;

    private String recentOTP;
    @Override
    public String toString() {
        return "UserDTO{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", about='" + about + '\'' +

                '}';
    }
}