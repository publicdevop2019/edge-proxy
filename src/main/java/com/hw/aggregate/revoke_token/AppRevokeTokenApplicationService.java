package com.hw.aggregate.revoke_token;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hw.aggregate.revoke_token.command.CreateRevokeTokenCommand;
import com.hw.aggregate.revoke_token.model.RevokeToken;
import com.hw.aggregate.revoke_token.model.RevokeTokenQueryRegistry;
import com.hw.shared.IdGenerator;
import com.hw.shared.idempotent.ChangeRepository;
import com.hw.shared.rest.DefaultRoleBasedRestfulService;
import com.hw.shared.rest.VoidTypedClass;
import com.hw.shared.sql.RestfulQueryRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class AppRevokeTokenApplicationService extends DefaultRoleBasedRestfulService<RevokeToken, Void, Void, VoidTypedClass> {
    @Autowired
    private RevokeTokenQueryRegistry registry;
    @Autowired
    private IdGenerator idGenerator2;
    @Autowired
    private ChangeRepository changeRepository2;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private RevokeTokenRepo repo2;

    @PostConstruct
    private void setUp() {
        repo = repo2;
        queryRegistry = registry;
        entityClass = RevokeToken.class;
        role = RestfulQueryRegistry.RoleEnum.ROOT;
        idGenerator = idGenerator2;
        changeRepository = changeRepository2;
        om = objectMapper;
    }

    @Override
    public RevokeToken replaceEntity(RevokeToken revokeToken, Object command) {
        return null;
    }

    @Override
    public Void getEntitySumRepresentation(RevokeToken revokeToken) {
        return null;
    }

    @Override
    public Void getEntityRepresentation(RevokeToken revokeToken) {
        return null;
    }

    @Override
    protected RevokeToken createEntity(long id, Object command) {
        return RevokeToken.create((CreateRevokeTokenCommand) command, repo2);
    }
}
