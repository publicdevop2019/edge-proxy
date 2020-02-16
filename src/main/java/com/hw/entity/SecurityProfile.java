package com.hw.entity;

import com.hw.shared.Auditable;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "security_profile_list")
@SequenceGenerator(name = "security_profile_list_gen", sequenceName = "security_profile_list_gen", initialValue = 100)
@Data
public class SecurityProfile extends Auditable {
    /**
     * spring security style expression e.g. "hasRole('ROLE_USER') and #oauth2.hasScope('trust') and #oauth2.isUser()"
     * for public access this filed can be null
     */
    private String expression;

    @NotBlank
    private String resourceID;

    @NotBlank
    private String path;

    @NotBlank
    private String method;

    /**
     * for dynamic routing
     */
    private String url;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "security_profile_list_gen")
    @Setter(AccessLevel.NONE)
    private Long id;
}
