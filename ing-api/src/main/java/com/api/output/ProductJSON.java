package com.api.output;

import lombok.*;

import java.io.Serializable;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductJSON implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;
    private double price;
    private String description;
}
