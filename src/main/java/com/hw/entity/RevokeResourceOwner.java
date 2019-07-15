package com.hw.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "blacklist_resource_owner_list")
@SequenceGenerator(name = "blacklist_resource_owner_gen", sequenceName = "blacklist_resource_owner_gen", initialValue = 100)
@Data
public class RevokeResourceOwner extends RevokeTokenInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "blacklist_resource_owner_gen")
    @Setter(AccessLevel.NONE)
    private Long id;
}
