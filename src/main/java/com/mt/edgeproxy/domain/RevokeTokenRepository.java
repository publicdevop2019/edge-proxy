package com.mt.edgeproxy.domain;

import com.mt.common.sql.SumPagedRep;
import com.mt.edgeproxy.application.QueryConfig;
import com.mt.edgeproxy.application.RevokeTokenPaging;
import com.mt.edgeproxy.application.RevokeTokenQuery;

public interface RevokeTokenRepository {
    SumPagedRep<RevokeToken> revokeTokensOfQuery(RevokeTokenQuery revokeTokenQuery, RevokeTokenPaging revokeTokenPaging, QueryConfig queryConfig);
}
