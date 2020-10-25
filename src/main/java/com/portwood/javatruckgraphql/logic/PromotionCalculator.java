package com.portwood.javatruckgraphql.logic;

import com.portwood.javatruckgraphql.datacontracts.response.CouponType;
import com.portwood.javatruckgraphql.datacontracts.response.PromotionWinner;
import com.portwood.javatruckgraphql.datasources.mysql.entities.Order;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class PromotionCalculator {

    /**
     * every 11th order = free chocolate (coupon A)
     * every 17th order = free pastry (coupon B)
     * both = 12-pack of bonbons (coupon C)
     */
    public List<PromotionWinner> calculatePromotion(List<Order> orders) {

        // sort orders by creation date
        List<Order> sortedOrders =
                orders.stream().sorted(Comparator.comparing(Order::getCreatedAt)).collect(Collectors.toList());

        // get all indices
        List<Integer> indices = IntStream.range(1, sortedOrders.size()).boxed().collect(Collectors.toList());

        // filter indices for each coupon
        List<Integer> couponA = indices.stream().filter(i -> i % 11 == 0).collect(Collectors.toList());
        List<Integer> couponB = indices.stream().filter(i -> i % 17 == 0).collect(Collectors.toList());
        List<Integer> couponC = couponA.stream().filter(couponB::contains).collect(Collectors.toList());

        // remove indices common to both A and B
        couponA.removeAll(couponC);
        couponB.removeAll(couponC);

        // create meaningful results
        List<PromotionWinner> couponAWinners = couponA
                .stream()
                .map(i -> new PromotionWinner(sortedOrders.get(i).getCustomer().getPhone(), CouponType.Chocolate))
                .collect(Collectors.toList());

        List<PromotionWinner> couponBWinners = couponB
                .stream()
                .map(i -> new PromotionWinner(sortedOrders.get(i).getCustomer().getPhone(), CouponType.Pastry))
                .collect(Collectors.toList());

        List<PromotionWinner> couponCWinners = couponC
                .stream()
                .map(i -> new PromotionWinner(sortedOrders.get(i).getCustomer().getPhone(), CouponType.Bonbons))
                .collect(Collectors.toList());

        List<PromotionWinner> winners = new ArrayList<>();

        winners.addAll(couponAWinners);
        winners.addAll(couponBWinners);
        winners.addAll(couponCWinners);

        return winners;
    }
}
