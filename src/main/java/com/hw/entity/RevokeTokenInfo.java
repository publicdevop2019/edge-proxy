package com.hw.entity;

import com.hw.shared.Auditable;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Data
public class RevokeTokenInfo extends Auditable {
    @Column(unique = true)
    String globalId;
    Long issuedAt;
}
