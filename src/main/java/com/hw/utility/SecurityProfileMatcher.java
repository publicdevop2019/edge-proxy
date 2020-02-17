package com.hw.utility;

import com.hw.entity.SecurityProfile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SecurityProfileMatcher {

    public static Optional<SecurityProfile> getMostSpecificSecurityProfile(List<SecurityProfile> collect1) {
        if (collect1.size() == 1)
            return Optional.of(collect1.get(0));
        List<SecurityProfile> exactMatch = collect1.stream().filter(e -> !e.getPath().contains("/**")).collect(Collectors.toList());
        if (exactMatch.size() == 1)
            return Optional.of(exactMatch.get(0));
        List<SecurityProfile> collect2 = collect1.stream().filter(e -> !e.getPath().endsWith("/**")).collect(Collectors.toList());
        if (collect2.size() == 1)
            return Optional.of(collect2.get(0));
        return Optional.empty();
    }
}
