package com.hw.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hw.shared.Auditable;
import com.hw.shared.BadRequestException;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

@Entity
@Table(name = "security_profile_list")
@SequenceGenerator(name = "security_profile_list_gen", sequenceName = "security_profile_list_gen", initialValue = 100)
@Data
public class SecurityProfile extends Auditable {
    /**
     * spring security style expression e.g. "hasRole('ROLE_USER') and #oauth2.hasScope('trust') and #oauth2.isUser()"
     * for public access this filed can be null
     */
    @Column
    private String expression;

    @NotBlank
    @Column
    private String resourceId;

    @NotBlank
    @Column
    private String lookupPath;

    @NotBlank
    @Column
    private String method;

    /**
     * for dynamic routing
     */
    public String retrieveURIString() {
        try {
            return new URI(scheme, userInfo, host, port, path, query, fragment).toURL().toString();
        } catch (URISyntaxException | MalformedURLException e) {
            e.printStackTrace();
            throw new BadRequestException("invalid uri syntax");
        }
    }
    public void convertToURIFromString(String url) {
        URI uri = URI.create(url);
        scheme = uri.getScheme();
        userInfo = uri.getUserInfo();
        host = uri.getHost();
        port = uri.getPort();
        path = uri.getPath();
        query = uri.getQuery();
        fragment = uri.getFragment();
        if (scheme == null || host == null || port == -1 || path == null)
            throw new BadRequestException("invalid url found");
    }
    @Column
    private String scheme;
    @Column
    private String userInfo;
    @Column
    private String host;
    @Column
    private Integer port;
    @Column
    private String path;
    @Column
    private String query;
    @Column
    private String fragment;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "security_profile_list_gen")
    @Setter(AccessLevel.NONE)
    private Long id;
}
