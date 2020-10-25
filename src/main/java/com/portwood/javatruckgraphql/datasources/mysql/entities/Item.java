package com.portwood.javatruckgraphql.datasources.mysql.entities;

import io.leangen.graphql.annotations.types.GraphQLType;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(name = "item")
@GraphQLType(description = "An item belonging to a specific customer order")
public class Item {

    @CreationTimestamp
    private ZonedDateTime createdAt;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private Long beanTypeId;

    private Long preparationTypeId;

    private Long statusId = 1L;

    public Item() {
    }

    public Item(Long beanTypeId, Long preparationTypeId) {
        setBeanTypeId(beanTypeId);
        setPreparationTypeId(preparationTypeId);
        setStatusId(1L);
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

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Long getBeanTypeId() {
        return beanTypeId;
    }

    public void setBeanTypeId(Long beanTypeId) {
        this.beanTypeId = beanTypeId;
    }

    public Long getPreparationTypeId() {
        return preparationTypeId;
    }

    public void setPreparationTypeId(Long preparationTypeId) {
        this.preparationTypeId = preparationTypeId;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }
}
