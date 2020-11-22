package com.hw.config;

import com.hw.aggregate.endpoint.model.BizEndpoint;
import com.hw.aggregate.endpoint.representation.AppBizEndpointCardRep;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SecurityProfileMatcher {

    public static Optional<AppBizEndpointCardRep> getMostSpecificSecurityProfile(List<AppBizEndpointCardRep> collect1) {
        if (collect1.size() == 1)
            return Optional.of(collect1.get(0));
        List<AppBizEndpointCardRep> exactMatch = collect1.stream().filter(e -> !e.getPath().contains("/**")).collect(Collectors.toList());
        if (exactMatch.size() == 1)
            return Optional.of(exactMatch.get(0));
        List<AppBizEndpointCardRep> collect2 = collect1.stream().filter(e -> !e.getPath().endsWith("/**")).collect(Collectors.toList());
        if (collect2.size() == 1)
            return Optional.of(collect2.get(0));
        return Optional.empty();
    }
}
