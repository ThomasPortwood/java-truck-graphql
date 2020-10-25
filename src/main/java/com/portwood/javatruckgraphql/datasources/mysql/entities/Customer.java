package com.portwood.javatruckgraphql.datasources.mysql.entities;

import io.leangen.graphql.annotations.types.GraphQLType;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(name = "customer")
@GraphQLType(description = "Simple reference to a customer by phone only")
public class Customer {

    @CreationTimestamp
    private ZonedDateTime createdAt;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String phone;

    public Customer() {
    }

    public Customer(String phone) {
        setPhone(phone);
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
