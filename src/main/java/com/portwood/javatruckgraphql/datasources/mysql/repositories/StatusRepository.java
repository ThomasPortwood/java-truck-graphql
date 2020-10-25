package com.portwood.javatruckgraphql.datasources.mysql.repositories;

import com.portwood.javatruckgraphql.datasources.mysql.entities.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {
}

