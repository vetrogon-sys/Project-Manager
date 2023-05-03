package com.example.entity.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SecurityOpportunity {
    @Id
    private String opportunity;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "authority_opportunities",
          inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id"),
          joinColumns = @JoinColumn(name = "opportunity_id", referencedColumnName = "opportunity"))
    private List<Authority> authorities;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SecurityOpportunity that = (SecurityOpportunity) o;
        return Objects.equals(opportunity, that.opportunity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(opportunity);
    }
}
