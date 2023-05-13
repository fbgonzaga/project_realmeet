package br.com.sw2you.realmeet.domain.entity;

import static br.com.sw2you.realmeet.util.DateUtils.*;
import static java.util.Objects.*;

import br.com.sw2you.realmeet.domain.model.Employee;
import br.com.sw2you.realmeet.util.DateUtils;
import java.time.OffsetDateTime;
import java.util.Objects;
import javax.persistence.*;

@Entity
@Table(name = "allocation")
public class Allocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @Embedded
    private Employee employee;

    @Column(name = "subject")
    private String subject;

    @Column(name = "start_at")
    private OffsetDateTime startAt;

    @Column(name = "end_at")
    private OffsetDateTime endAt;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    private Allocation(Builder builder) {
        this.id = builder.id;
        this.room = builder.room;
        this.employee = builder.employee;
        this.subject = builder.subject;
        this.startAt = builder.startAt;
        this.endAt = builder.endAt;
        this.createdAt = builder.createdAt;
        this.updatedAt = builder.updatedAt;
    }

    public Allocation() {}

    @PrePersist
    public void prePersist() {
        if (isNull(createdAt)) {
            createdAt = now();
        }
        updatedAt = createdAt;
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = now();
    }

    public Long getId() {
        return id;
    }

    public Room getRoom() {
        return room;
    }

    public Employee getEmployee() {
        return employee;
    }

    public String getSubject() {
        return subject;
    }

    public OffsetDateTime getStartAt() {
        return startAt;
    }

    public OffsetDateTime getEndAt() {
        return endAt;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Allocation that = (Allocation) o;
        return (
            id.equals(that.id) &&
            room.equals(that.room) &&
            employee.equals(that.employee) &&
            subject.equals(that.subject) &&
            startAt.equals(that.startAt) &&
            endAt.equals(that.endAt) &&
            createdAt.equals(that.createdAt) &&
            updatedAt.equals(that.updatedAt)
        );
    }

    @Override
    public int hashCode() {
        return hash(id, room, employee, subject, startAt, endAt, createdAt, updatedAt);
    }

    @Override
    public String toString() {
        return (
            "Allocation{" +
            "id=" +
            id +
            ", room=" +
            room +
            ", employee=" +
            employee +
            ", subject='" +
            subject +
            '\'' +
            ", startAt=" +
            startAt +
            ", endAt=" +
            endAt +
            ", createdAt=" +
            createdAt +
            ", updatedAt=" +
            updatedAt +
            '}'
        );
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private Long id;
        private Room room;
        private Employee employee;
        private String subject;
        private OffsetDateTime startAt;
        private OffsetDateTime endAt;
        private OffsetDateTime createdAt;
        private OffsetDateTime updatedAt;

        private Builder() {}

        public Builder withId(Long id) {
            this.id = id;
            return this;
        }

        public Builder withRoom(Room room) {
            this.room = room;
            return this;
        }

        public Builder withEmployee(Employee employee) {
            this.employee = employee;
            return this;
        }

        public Builder withSubject(String subject) {
            this.subject = subject;
            return this;
        }

        public Builder withStartAt(OffsetDateTime startAt) {
            this.startAt = startAt;
            return this;
        }

        public Builder withEndAt(OffsetDateTime endAt) {
            this.endAt = endAt;
            return this;
        }

        public Builder withCreatedAt(OffsetDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder withUpdatedAt(OffsetDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public Allocation build() {
            return new Allocation(this);
        }
    }
}
