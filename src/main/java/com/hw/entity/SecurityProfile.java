package com.hw.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;
import org.springframework.http.HttpMethod;

import javax.persistence.*;

@Data
@AllArgsConstructor
@Entity
@Table(name = "security_profile_list")
@SequenceGenerator(name = "security_profile_list_gen", sequenceName = "security_profile_list_gen", initialValue = 100)
public class SecurityProfile {
    /**
     * spring security style expression e.g. "hasRole('ROLE_USER') and #oauth2.hasScope('trust') and #oauth2.isUser()"
     */
    private String expression;

    private String resourceID;

    private String endpoint;

    private HttpMethod method;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "security_profile_list_gen")
    @Setter(AccessLevel.NONE)
    private Long id;
}
