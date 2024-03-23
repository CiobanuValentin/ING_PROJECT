package com.api.output;

import lombok.*;

import java.io.Serializable;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleJSON implements Serializable {

    private static final long serialVersionUID = 1L;

    private String roleKey;
    private String name;
}
