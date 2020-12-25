package com.hw.aggregate.endpoint.model;

import com.hw.aggregate.endpoint.AppBizEndpointApplicationService;
import com.hw.aggregate.endpoint.command.RootCreateBizEndpointCommand;
import com.hw.aggregate.endpoint.command.RootUpdateBizEndpointCommand;
import com.hw.aggregate.endpoint.exception.DuplicateEndpointException;
import com.hw.aggregate.endpoint.representation.AppBizEndpointCardRep;
import com.mt.common.Auditable;
import com.mt.common.rest.Aggregate;
import com.mt.common.sql.SumPagedRep;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table
@Data
@Slf4j
@NoArgsConstructor
public class BizEndpoint extends Auditable implements Aggregate {
    public static final String ENTITY_RESOURCE_ID = "resourceId";
    public static final String ENTITY_PATH = "path";
    public static final String ENTITY_METHOD = "method";
    public static final String ENTITY_EXPRESSION = "expression";
    /**
     * spring security style expression e.g. "hasRole('ROLE_USER') and #oauth2.hasScope('trust') and #oauth2.isUser()"
     * for public access this filed can be null
     */
    @Column
    private String expression;
    @Column
    private String description;
    @NotBlank
    @Column
    private String resourceId;
    @NotBlank
    @Column
    private String path;
    @NotBlank
    @Column
    private String method;
    @Id
    private Long id;
    @Version
    @Setter(AccessLevel.NONE)
    private Integer version;

    private BizEndpoint(Long id, RootCreateBizEndpointCommand command) {
        this.id = id;
        this.expression = command.getExpression();
        this.resourceId = command.getResourceId();
        this.path = command.getPath();
        this.method = command.getMethod();
        this.description = command.getDescription();
    }

    /**
     * duplicated profiles are not allow bcz find most specific profile will fail
     * check if same profile exist, reject if exist
     */
    public static BizEndpoint create(long id, RootCreateBizEndpointCommand command, AppBizEndpointApplicationService service) {
        String s = "resourceId:" + command.getResourceId() + ",path:" + command.getPath() + ",method:" + command.getMethod();
        SumPagedRep<AppBizEndpointCardRep> appBizEndpointCardRepSumPagedRep = service.readByQuery(s, null, null);
        if (!appBizEndpointCardRepSumPagedRep.getData().isEmpty())
            throw new DuplicateEndpointException();
        return new BizEndpoint(id, command);
    }

    public BizEndpoint replace(RootUpdateBizEndpointCommand command) {
        BeanUtils.copyProperties(command, this, "version");
        return this;
    }
}
