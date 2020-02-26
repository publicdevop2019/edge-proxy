package com.hw.entity;

import com.hw.shared.Auditable;
import lombok.Data;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Data
public class RevokeTokenInfo extends Auditable {
    String globalId;
    Long issuedAt;
}
