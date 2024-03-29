package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Table(name = "projects")
@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@NamedEntityGraph(
      name = "project-with-assigned-users",
      attributeNodes = {
            @NamedAttributeNode("assignedUsers"),
      }
)
public class Project {
    @Id
    @SequenceGenerator(name = "pk_project_sequence", sequenceName = "project_id_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_project_sequence")
    private Long id;

    @Column(name = "name", length = 125)
    private String name;
    @Column(name = "description", length = 512)
    private String description;

    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "project")
    private List<Desk> desks;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", nullable = false, referencedColumnName = "id")
    private User creator;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_projects",
          joinColumns = @JoinColumn(name = "project_id", referencedColumnName = "id"),
          inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private Set<User> assignedUsers;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Project project = (Project) o;
        return Objects.equals(id, project.id) &&
              Objects.equals(name, project.name) &&
              Objects.equals(description, project.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
    }

    public void removeAssignedUsersWithIds(List<Long> userIds) {
        assignedUsers.removeIf(user -> userIds.contains(user.getId()));
    }

    public void addAssignUsers(List<User> users) {
        if (Objects.isNull(assignedUsers)) {
            assignedUsers = new HashSet<>();
        }
        assignedUsers.addAll(users);
    }
}
