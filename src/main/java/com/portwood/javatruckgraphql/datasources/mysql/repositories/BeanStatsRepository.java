package com.portwood.javatruckgraphql.datasources.mysql.repositories;

import com.portwood.javatruckgraphql.datacontracts.response.BeanStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BeanStatsRepository extends JpaRepository<BeanStats, Long> {

    @Query(
            nativeQuery = true,
            value = "select bt.name as name, count(*) as count from item " +
                    "inner join bean_type bt on item.bean_type_id = bt.id " +
                    "where item.created_at between date_sub(now(), interval 30 day) and now() " +
                    "group by item.bean_type_id " +
                    "order by count(*) asc"
    )
    List<BeanStats> getBeanStats();
}