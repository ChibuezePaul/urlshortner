package com.isoft.code.urlshortner.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Setter
@Getter
public class UserUrl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String shortUrl;

    private String longUrl;

    private LocalDateTime dateCreated = LocalDateTime.now();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserUrl userUrl = (UserUrl) o;
        return Objects.equals(id, userUrl.id) &&
                Objects.equals(shortUrl, userUrl.shortUrl) &&
                Objects.equals(longUrl, userUrl.longUrl) &&
                Objects.equals(dateCreated, userUrl.dateCreated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, shortUrl, longUrl, dateCreated);
    }
}
