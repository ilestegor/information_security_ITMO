package org.ilestegor.lab1.model;

import jakarta.persistence.*;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private User user;

    @Column(name = "task_name", nullable = false)
    private String taskName;

    @Column(nullable = false)
    private LocalDateTime deadline;

    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Priority priority;

    private Instant created;

    private Instant completed;

    private boolean isCompleted;

    public void setUser(User user) {
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(Instant completed) {
        this.completed = completed;
    }

    public Instant getCreated() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "Todo{" +
                "id=" + id +
                ", user=" + user +
                ", taskName='" + taskName + '\'' +
                ", deadline=" + deadline +
                ", description='" + description + '\'' +
                ", priority=" + priority +
                ", created=" + created +
                ", completed=" + completed +
                '}';
    }
}
