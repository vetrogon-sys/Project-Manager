package com.example.entity.security;

import com.example.entity.Project;
import com.example.entity.User;
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(length = 512, nullable = false)
    private String value;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "authorities")
    private List<SecurityOpportunity> opportunities;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_user_id", referencedColumnName = "id")
    private User assigned;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "related_project_id", referencedColumnName = "id")
    private Project relatedProject;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Authority)) {
            return false;
        }
        Authority authority = (Authority) o;
        return Objects.equals(id, authority.id) && Objects.equals(value, authority.value)
              && Objects.equals(opportunities, authority.opportunities)
              && Objects.equals(assigned, authority.assigned)
              && Objects.equals(relatedProject, authority.relatedProject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, value, opportunities, assigned, relatedProject);
    }
}
