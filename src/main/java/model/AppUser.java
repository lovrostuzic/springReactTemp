package model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class AppUser implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    //email = username
    private String username;

    private String password;

    private String firstName;

    private String lastName;

    private LocalDate dateOfBirth;

    @Enumerated(value = EnumType.STRING)
    private Role role;
}
