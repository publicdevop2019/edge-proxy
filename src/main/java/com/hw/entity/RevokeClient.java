package com.hw.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "blacklist_client_list")
@SequenceGenerator(name = "blacklist_client_gen", sequenceName = "blacklist_client_gen", initialValue = 100)
@Data
public class RevokeClient extends RevokeTokenInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "blacklist_client_gen")
    @Setter(AccessLevel.NONE)
    private Long id;
}
