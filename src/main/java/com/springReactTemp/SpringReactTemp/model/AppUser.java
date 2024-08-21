package com.springReactTemp.SpringReactTemp.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import model.Role;


@Getter
@Setter
@RequiredArgsConstructor
@Entity(name = "app_user")
public class AppUser implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    //email = username
    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private LocalDate dateOfBirth;

    @Enumerated(value = EnumType.STRING)
    private Role role;
}
