package com.hw.entity;

import lombok.Data;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Data
public class RevokeTokenInfo {
    String name;
    Long issuedAt;
}
