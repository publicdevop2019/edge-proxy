package com.hw.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "blacklist_resource_owner_list")
@Data
public class RevokeResourceOwner extends RevokeTokenInfo {
    @Id
    private Long id;
}
