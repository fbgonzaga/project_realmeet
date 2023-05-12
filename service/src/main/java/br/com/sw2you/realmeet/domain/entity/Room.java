package br.com.sw2you.realmeet.domain.entity;

import static java.util.Objects.isNull;

import java.util.Objects;
import javax.persistence.*;

@Entity
@Table(name = "room")
public class Room {
    /**
     * todos os atributos foram
     * definidos com classes wrapper.
     * A ausencia de metodos setters
     * tambem eh visando a imutabilidade.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "seats", nullable = false)
    private Integer seats;

    @Column(name = "active", nullable = false)
    private Boolean active;

    /**
     * Metodo necessario para que a JPA consiga instanciar o
     * objeto e settar os valores internamente.
     */
    public Room() {}

    /**
     * Esta classe gerara objetos imutaveis,
     * evitando problemas de concorrencia.
     * Por isso o construtor privado.
     */
    private Room(Builder builder) {
        id = builder.id;
        name = builder.name;
        seats = builder.seats;
        active = builder.active;
    }

    @PrePersist
    public void prePersist() {
        if (isNull(active)) {
            active = true;
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getSeats() {
        return seats;
    }

    public Boolean getActive() {
        return active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return id.equals(room.id) && name.equals(room.name) && seats.equals(room.seats) && active.equals(room.active);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, seats, active);
    }

    @Override
    public String toString() {
        return "Room{" + "id=" + id + ", name='" + name + '\'' + ", seats=" + seats + ", active=" + active + '}';
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private Long id;
        private String name;
        private Integer seats;
        private Boolean active;

        private Builder() {}

        public Builder withId(Long id) {
            this.id = id;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withSeats(Integer seats) {
            this.seats = seats;
            return this;
        }

        public Builder withActive(Boolean active) {
            this.active = active;
            return this;
        }

        public Room build() {
            return new Room(this);
        }
    }
}
