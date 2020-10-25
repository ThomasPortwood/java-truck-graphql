package com.portwood.javatruckgraphql.logic;

import com.portwood.javatruckgraphql.datasources.mysql.entities.Order;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PromotionCalculator {

    /**
     * The company’s having an anniversary special, and have partnered with a local sweet shop for a special promotion.
     *
     * In a language of your choosing, write a method that iterates over all historical orders, and
     *
     * sends the customer for every 11th order a coupon for a free chocolate (coupon A),
     *
     * the customer for every 17th order a coupon for a free pastry (coupon B ).
     *
     * For an order that matches both of these criteria (i.e. is an 11th order as well as a 17th order),
     * they receive a special gold coupon for a 12-pack of bonbons (coupon C).
     *
     * You can define the format of the input (for example, it could be order numbers, tuples, order objects, JSON strings - whatever you prefer),
     * and the output should be a single value that indicates which coupon the customer should get. Submit the code.
     */
    public void calculatePromotion(List<Order> orders) {

        List<Order> ordersSortedByAscendingCreationDate =
                orders.stream().sorted(Comparator.comparing(Order::getCreatedAt)).collect(Collectors.toList());


        for (Order o : ordersSortedByAscendingCreationDate) {



        }


    }

}
