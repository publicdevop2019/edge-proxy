package com.hw.aggregate.endpoint.model;

import com.hw.aggregate.endpoint.AppBizEndpointApplicationService;
import com.hw.aggregate.endpoint.AppBizEndpointCardRep;
import com.hw.aggregate.endpoint.RootBizEndpointApplicationService;
import com.hw.aggregate.endpoint.command.CreateBizEndpointCommand;
import com.hw.aggregate.endpoint.command.UpdateBizEndpointCommand;
import com.hw.aggregate.endpoint.exception.DuplicateEndpointException;
import com.hw.aggregate.endpoint.representation.RootBizEndpointCardRep;
import com.hw.shared.Auditable;
import com.hw.shared.rest.IdBasedEntity;
import com.hw.shared.sql.SumPagedRep;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table
@Data
@Slf4j
@NoArgsConstructor
public class BizEndpoint extends Auditable implements IdBasedEntity {
    /**
     * spring security style expression e.g. "hasRole('ROLE_USER') and #oauth2.hasScope('trust') and #oauth2.isUser()"
     * for public access this filed can be null
     */
    @Column
    private String expression;

    @NotBlank
    @Column
    private String resourceId;
    public static final String ENTITY_RESOURCE_ID="resourceId";

    @NotBlank
    @Column
    private String path;
    public static final String ENTITY_PATH="path";

    @NotBlank
    @Column
    private String method;
    public static final String ENTITY_METHOD="method";

    @Id
    private Long id;

    /**
     * duplicated profiles are not allow bcz find most specific profile will fail
     * check if same profile exist, reject if exist
     */
    public static BizEndpoint create(long id, CreateBizEndpointCommand command, AppBizEndpointApplicationService service) {
        String s = "resourceId:" + command.getResourceId() + ",path:" + command.getPath() + ",method:" + command.getMethod();
        SumPagedRep<AppBizEndpointCardRep> appBizEndpointCardRepSumPagedRep = service.readByQuery(s, null, null);
        if (appBizEndpointCardRepSumPagedRep.getData().size() != 0)
            throw new DuplicateEndpointException();
        return new BizEndpoint(id, command);
    }

    private BizEndpoint(Long id, CreateBizEndpointCommand command) {
        this.id = id;
        this.expression = command.getExpression();
        this.resourceId = command.getResourceId();
        this.path = command.getPath();
        this.method = command.getMethod();
    }

    public BizEndpoint replace(UpdateBizEndpointCommand command) {
        BeanUtils.copyProperties(command, this);
        return this;
    }
}
