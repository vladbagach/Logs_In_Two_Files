package com.example.wamitest.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.ZonedDateTime;
import java.util.Objects;

@Data
@AllArgsConstructor
public class Singular {

    @JsonProperty("Number")
    private String id;
    @JsonFormat(pattern = "yy-MM-dd HH:mm:ss z")
    @JsonProperty("Date")
    private ZonedDateTime time;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Singular singular = (Singular) o;
        return id.equals(singular.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
