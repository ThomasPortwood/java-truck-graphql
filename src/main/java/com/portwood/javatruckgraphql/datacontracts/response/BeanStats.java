package com.portwood.javatruckgraphql.datacontracts.response;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class BeanStats {

    @Id
    private String name;

    private Long count;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
