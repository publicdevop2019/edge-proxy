package com.hw.config;

import com.hw.config.filter.EndpointFilter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SecurityProfileMatcher {

    public static Optional<EndpointFilter.EndpointCardRep> getMostSpecificSecurityProfile(List<EndpointFilter.EndpointCardRep> collect1) {
        if (collect1.size() == 1)
            return Optional.of(collect1.get(0));
        List<EndpointFilter.EndpointCardRep> exactMatch = collect1.stream().filter(e -> !e.getPath().contains("/**")).collect(Collectors.toList());
        if (exactMatch.size() == 1)
            return Optional.of(exactMatch.get(0));
        List<EndpointFilter.EndpointCardRep> collect2 = collect1.stream().filter(e -> !e.getPath().endsWith("/**")).collect(Collectors.toList());
        if (collect2.size() == 1)
            return Optional.of(collect2.get(0));
        return Optional.empty();
    }
}
