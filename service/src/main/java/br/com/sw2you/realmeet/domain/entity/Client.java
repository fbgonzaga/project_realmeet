package br.com.sw2you.realmeet.domain.entity;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Client {
    @Id
    @Column(name = "api_key")
    private String apiKey;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "active", nullable = false)
    private Boolean active;

    public Client() {}

    private Client(Builder builder) {
        this.apiKey = builder.apiKey;
        this.description = builder.description;
        this.active = builder.active;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getActive() {
        return active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return apiKey.equals(client.apiKey) && description.equals(client.description) && active.equals(client.active);
    }

    @Override
    public int hashCode() {
        return Objects.hash(apiKey, description, active);
    }

    @Override
    public String toString() {
        return "Client{apiKey='" + apiKey + "', description='" + description + "', active=" + active + "}";
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private String apiKey;
        private String description;
        private Boolean active;

        private Builder() {}

        public Builder withApiKey(String apiKey) {
            this.apiKey = apiKey;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withActive(Boolean active) {
            this.active = active;
            return this;
        }

        public Client build() {
            return new Client(this);
        }
    }
}
