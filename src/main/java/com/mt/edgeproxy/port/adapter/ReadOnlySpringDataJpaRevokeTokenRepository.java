package com.mt.edgeproxy.port.adapter;

import com.mt.common.sql.SumPagedRep;
import com.mt.edgeproxy.application.QueryConfig;
import com.mt.edgeproxy.application.RevokeTokenPaging;
import com.mt.edgeproxy.application.RevokeTokenQuery;
import com.mt.edgeproxy.domain.RevokeToken;
import com.mt.edgeproxy.domain.RevokeTokenRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReadOnlySpringDataJpaRevokeTokenRepository extends JpaRepository<RevokeToken, Long>, RevokeTokenRepository {
    default SumPagedRep<RevokeToken> revokeTokensOfQuery(RevokeTokenQuery revokeTokenQuery, RevokeTokenPaging revokeTokenPaging, QueryConfig queryConfig) {
        return getSumPagedRep(revokeTokenQuery.getValue(), revokeTokenPaging.value(), queryConfig.value());
    }

    private SumPagedRep<RevokeToken> getSumPagedRep(String query, String page, String config) {
        RevokeTokenQueryBuilder userQueryBuilder = QueryBuilderRegistry.revokeTokenQueryBuilder();
        List<RevokeToken> select = userQueryBuilder.select(query, page, RevokeToken.class);
        Long aLong = null;
        if (!skipCount(config)) {
            aLong = userQueryBuilder.selectCount(query, RevokeToken.class);
        }
        return new SumPagedRep<>(select, aLong);
    }

    private boolean skipCount(String config) {
        return config != null && config.contains("sc:1");
    }
}
