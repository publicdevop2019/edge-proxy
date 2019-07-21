package com.hw.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "security_profile_list")
@SequenceGenerator(name = "security_profile_list_gen", sequenceName = "security_profile_list_gen", initialValue = 100)
@Data
public class SecurityProfile {
    /**
     * spring security style expression e.g. "hasRole('ROLE_USER') and #oauth2.hasScope('trust') and #oauth2.isUser()"
     */
    @NotBlank
    private String expression;

    @NotBlank
    private String resourceID;

    @NotBlank
    private String endpoint;

    @NotBlank
    private String method;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "security_profile_list_gen")
    @Setter(AccessLevel.NONE)
    private Long id;
}
