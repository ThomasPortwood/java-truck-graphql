package com.portwood.javatruckgraphql.datasources.mysql.repositories;

import com.portwood.javatruckgraphql.datasources.mysql.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByTruckId(Long truckId);
}

