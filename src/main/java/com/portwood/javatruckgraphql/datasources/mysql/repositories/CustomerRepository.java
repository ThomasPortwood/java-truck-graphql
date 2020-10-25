package com.portwood.javatruckgraphql.datasources.mysql.repositories;

import com.portwood.javatruckgraphql.datasources.mysql.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByPhone(String phone);
}

