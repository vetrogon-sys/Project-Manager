package com.example.entity;

import com.example.dto.TaskDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.Objects;

@Table(name = "tasks")
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@NamedEntityGraph(
      name = "task-with-desk",
      attributeNodes = {@NamedAttributeNode("desk")}
)
public class Task {
    @Id
    @SequenceGenerator(name = "pk_task_sequence", sequenceName = "task_id_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_task_sequence")
    private Long id;

    private String title;
    private String description;

    private LocalDate creationDate;
    private LocalDate reqResolutionDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_id", referencedColumnName = "id")
    private User assignedUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "desk_id", nullable = false, referencedColumnName = "id")
    private Desk desk;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Task task = (Task) o;
        return Objects.equals(id, task.id) &&
              Objects.equals(title, task.title) &&
              Objects.equals(description, task.description) &&
              Objects.equals(creationDate, task.creationDate) &&
              Objects.equals(reqResolutionDate, task.reqResolutionDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, creationDate, reqResolutionDate);
    }

    public void fillRequiredFields(TaskDto from) {
        this.setTitle(from.getTitle());
        this.setDescription(from.getDescription());
        this.setReqResolutionDate(from.getReqResolutionDate());
    }

}
