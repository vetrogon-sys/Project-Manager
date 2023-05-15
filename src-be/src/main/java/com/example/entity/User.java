package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Objects;
import java.util.Set;

@Table(name = "users")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @SequenceGenerator(name = "pk_user_sequence", sequenceName = "user_id_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_user_sequence")
    private Long id;
    @Column(name = "email", unique = true)
    private String email;
    @Column(name = "password", unique = true, length = 512)
    private String password;
    @Column(name = "first_name", length = 30)
    private String firstName;
    @Column(name = "last_name", length = 30)
    private String lastName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "creator", orphanRemoval = true)
    private Set<Project> ownProjects;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "assignedUsers")
    private Set<Project> assignedProjects;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "assignedUser")
    private Set<Task> assignedTasks;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(email, user.email) &&
                Objects.equals(password, user.password) &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password, firstName, lastName);
    }

}
