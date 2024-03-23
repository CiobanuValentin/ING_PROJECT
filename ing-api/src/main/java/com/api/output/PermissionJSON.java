package com.api.output;

import lombok.*;

import java.io.Serializable;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PermissionJSON implements Serializable {

    private static final long serialVersionUID = 1L;

    private String permissionKey;
    private String description;
    private String action;
    private String subject;
}
