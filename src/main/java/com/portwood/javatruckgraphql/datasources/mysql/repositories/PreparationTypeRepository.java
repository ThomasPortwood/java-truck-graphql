package com.portwood.javatruckgraphql.datasources.mysql.repositories;

import com.portwood.javatruckgraphql.datasources.mysql.entities.PreparationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PreparationTypeRepository extends JpaRepository<PreparationType, Long> {
}

