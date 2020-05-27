package com.hw.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "blacklist_client_list")
@Data
public class RevokeClient extends RevokeTokenInfo {
    @Id
    private Long id;
}
