package com.mt.edgeproxy.application;

import com.mt.common.sql.SumPagedRep;
import com.mt.edgeproxy.domain.DomainRegistry;
import com.mt.edgeproxy.domain.RevokeToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RevokeTokenApplicationService {

    @Transactional(readOnly = true)
    public SumPagedRep<RevokeToken> revokeTokens(String queryParam, String pageParam, String config) {
        return DomainRegistry.revokeTokenRepository().revokeTokensOfQuery(new RevokeTokenQuery(queryParam), new RevokeTokenPaging(pageParam), new QueryConfig(config));
    }
}
