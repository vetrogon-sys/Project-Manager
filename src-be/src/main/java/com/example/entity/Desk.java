package com.example.entity;

import lombok.AllArgsConstructor;
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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Table(name = "desks")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@NamedEntityGraph(
      name = "desk-with-tasks",
      attributeNodes = {@NamedAttributeNode("tasks")}
)
public class Desk {

    @Id
    @SequenceGenerator(name = "pk_desk_sequence", sequenceName = "desk_id_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_desk_sequence")
    private Long id;

    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "desk")
    private List<Task> tasks;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false, updatable = false)
    private Project project;

    public void addTask(Task task) {
        if (Objects.isNull(tasks)) {
            tasks = new ArrayList<>();
        }
        tasks.add(task);
    }

    public Boolean removeTask(Task task) {
        if (Objects.isNull(tasks) || !tasks.contains(task)) {
            return Boolean.FALSE;
        }
        return tasks.remove(task);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Desk desk = (Desk) o;
        return Objects.equals(id, desk.id) &&
              Objects.equals(name, desk.name) &&
              Objects.equals(project, desk.project);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, project);
    }

    public Boolean removeAllTasks() {
        if (Objects.isNull(tasks)) {
            return Boolean.FALSE;
        }
        tasks.forEach(task -> task.setDesk(null));
        tasks = null;
        return Boolean.TRUE;
    }
}
