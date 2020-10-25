package com.portwood.javatruckgraphql.datasources.mysql.repositories;

import com.portwood.javatruckgraphql.datasources.mysql.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByTruckId(Long truckId);

    // https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation
    List<Order> findByTruckIdAndCreatedAtBetween(Long truckId, ZonedDateTime start, ZonedDateTime end);
}

