package com.portwood.javatruckgraphql.resolvers;

import com.portwood.javatruckgraphql.datacontracts.response.PromotionWinner;
import com.portwood.javatruckgraphql.datasources.mysql.entities.Customer;
import com.portwood.javatruckgraphql.datasources.mysql.entities.Item;
import com.portwood.javatruckgraphql.datasources.mysql.entities.Order;
import com.portwood.javatruckgraphql.datasources.mysql.entities.Truck;
import com.portwood.javatruckgraphql.logic.PromotionCalculator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PromotionCalculatorShould {

    @Autowired
    PromotionCalculator promotionCalculator;

    @Test
    public void returnCorrectWinners() {

        // arrange
        Truck truck = new Truck("Some Truck");
        Customer customer = new Customer("123");
        List<Order> orders = new ArrayList<>();
        for (int i = 0; i < 22; i++) {
            Set<Item> items = Set.of();
            Order order = new Order(truck, customer, items);
            order.setCreatedAt(ZonedDateTime.now());
            orders.add(order);
        }

        // act
        List<PromotionWinner> winners = promotionCalculator.calculatePromotion(orders);

        // assert
        assertEquals(3, winners.size());
    }
}
