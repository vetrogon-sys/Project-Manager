package com.example.entity.security;

import com.example.entity.Project;
import com.example.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Authority implements GrantedAuthority {
    private static final String USER_VALUE = "user_";
    private static final String PROJECT_VALUE = "_project_";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "authorities")
    private List<SecurityOpportunity> opportunities;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_user_id", referencedColumnName = "id")
    private User assigned;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "related_project_id", referencedColumnName = "id")
    private Project relatedProject;

    @Override
    public String getAuthority() {
        return USER_VALUE
              + assigned.getId()
              + PROJECT_VALUE
              + relatedProject.getId()
              + opportunities.stream()
              .map(SecurityOpportunity::getOpportunity)
              .collect(Collectors.joining("_"));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Authority)) {
            return false;
        }
        Authority authority = (Authority) o;
        return Objects.equals(id, authority.id)
              && Objects.equals(opportunities, authority.opportunities)
              && Objects.equals(assigned, authority.assigned)
              && Objects.equals(relatedProject, authority.relatedProject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, opportunities, assigned, relatedProject);
    }
}
