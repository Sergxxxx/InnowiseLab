package com.innowise.task.domain;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "usr")
@Builder(setterPrefix = "with")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id", "username", "password", "email", "active"})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "Username cannot be empty")
    @Length(max = 15, message = "Username too long")
    @Pattern(regexp = "^[a-zA-Z0-9_]*$", message = "Only english letters, numbers or underscore")
    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Length(max = 15, message = "Password too long")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Only english letters or numbers")
    @Column(name = "password", nullable = false)
    private String password;

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Email isn't correct")
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @CreatedDate
    @Column(name = "registration_date_time", updatable = false, nullable = false)
    private LocalDateTime registrationDateTime;

    @Column(name = "active", nullable = false)
    private boolean active = true;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    public boolean isAdmin(){
        return roles.contains(Role.ADMIN);
    }

    public boolean isActive() {
        return active;
    }

}