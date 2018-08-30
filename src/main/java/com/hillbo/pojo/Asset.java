package com.hillbo.pojo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Asset {

    @Id
    private String id;

    private String f_name;

    public Asset() {
    }

    public Asset(String id, String f_name) {
        this.id = id;
        this.f_name = f_name;
    }
}
