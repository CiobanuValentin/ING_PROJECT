package com.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductInput {

    @NotNull(message = "Name field can not be null!")
    @Size(min = 2, max = 255, message = "Name must be between 2 and 255 characters long!")
    @JsonProperty("name")
    private String name;

    @NotNull(message = "Price field can not be null!")
    @JsonProperty("price")
    private double price;

    @JsonProperty("description")
    private String description;
}
