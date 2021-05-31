package com.epam.winter_java_lab.task_13.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "usr")
@EqualsAndHashCode(of = {"id"})
public class User implements UserDetails {
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Getter
    @Setter
    @NotBlank(message = "Username cannot be empty")
    @Length(max = 15, message = "Username too long")
    @Pattern(regexp = "^[a-zA-Z0-9_]*$", message = "Only english letters, numbers or underscore")
    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Getter
    @Setter
    @Length(max = 15, message = "Password too long")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Only english letters or numbers")
    @Column(name = "password", nullable = false)
    private String password;

    @Getter
    @Setter
    @NotBlank(message = "Password confirmation cannot be empty")
    @Transient
    private String passwordConf;

    @Getter
    @Setter
    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Email isn't correct")
    @Column(name = "email", nullable = false)
    private String email;

    @Getter
    @CreatedDate
    @Column(name = "registration_date_time", nullable = false)
    private LocalDateTime registrationDateTime;

    @Setter
    @Column(name = "active", nullable = false)
    private boolean active;

    @Getter
    @Setter
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @Getter
    @Setter
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Message> messages;

    public boolean isAdmin(){
        return roles.contains(Role.ADMIN);
    }

    public void setRegistrationDateTime(Long registrationDateTime) {
        this.registrationDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(registrationDateTime), ZoneId.systemDefault());
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

}














