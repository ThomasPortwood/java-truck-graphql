package com.portwood.javatruckgraphql.resolvers;

import com.portwood.javatruckgraphql.datacontracts.request.*;
import com.portwood.javatruckgraphql.datacontracts.response.BeanStats;
import com.portwood.javatruckgraphql.datasources.mysql.entities.*;
import com.portwood.javatruckgraphql.datasources.mysql.repositories.*;
import io.leangen.graphql.annotations.*;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import io.leangen.graphql.spqr.spring.util.ConcurrentMultiMap;
import org.reactivestreams.Publisher;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@GraphQLApi
public class Resolver {

    private final BeanStatsRepository beanStatsRepository;
    private final BeanTypeRepository beanTypeRepository;
    private final CustomerRepository customerRepository;
    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;
    private final PreparationTypeRepository preparationTypeRepository;
    private final StatusRepository statusRepository;
    private final TruckRepository truckRepository;

    private final ConcurrentMultiMap<String, FluxSink<Order>> subscribers = new ConcurrentMultiMap<>();

    public Resolver(BeanStatsRepository beanStatsRepository,
                    BeanTypeRepository beanTypeRepository,
                    CustomerRepository customerRepository,
                    ItemRepository itemRepository,
                    OrderRepository orderRepository,
                    PreparationTypeRepository preparationTypeRepository,
                    StatusRepository statusRepository,
                    TruckRepository truckRepository) {

        this.beanStatsRepository = beanStatsRepository;
        this.beanTypeRepository = beanTypeRepository;
        this.customerRepository = customerRepository;
        this.itemRepository = itemRepository;
        this.orderRepository = orderRepository;
        this.preparationTypeRepository = preparationTypeRepository;
        this.statusRepository = statusRepository;
        this.truckRepository = truckRepository;
    }

    @GraphQLMutation(description = "Create a new truck")
    public Truck createTruck(@GraphQLNonNull NewTruck input) {
        Truck truck = new Truck(input.getName());
        return truckRepository.save(truck);
    }

    @GraphQLQuery(description = "Get all trucks in our fleet")
    public List<Truck> getAllTrucks() {
        return truckRepository.findAll();
    }

    @GraphQLMutation(description = "Create a new status")
    public Status createStatus(@GraphQLNonNull NewStatus input) {
        Status status = new Status(input.getName());
        return statusRepository.save(status);
    }

    @GraphQLQuery(description = "Get all available statuses of items in orders")
    public List<Status> getAllStatuses() {
        return statusRepository.findAll();
    }

    @GraphQLMutation(description = "Create a new bean type")
    public BeanType createBeanType(@GraphQLNonNull NewBeanType input) {
        BeanType beanType = new BeanType();
        beanType.setName(input.getName());
        beanType.setPriceMultiplier(input.getPriceMultiplier());
        return beanTypeRepository.save(beanType);
    }

    @GraphQLQuery(description = "Get all item bean types")
    public List<BeanType> getAllBeanTypes() {
        return beanTypeRepository.findAll();
    }

    @GraphQLMutation(description = "Create a new preparation type")
    public PreparationType createPreparationType(@GraphQLNonNull NewPreparationType input) {
        PreparationType preparationType = new PreparationType();
        preparationType.setName(input.getName());
        preparationType.setPrice(input.getPrice());
        return preparationTypeRepository.save(preparationType);
    }

    @GraphQLQuery(description = "Get all item preparation types")
    public List<PreparationType> getAllPreparationTypes() {
        return preparationTypeRepository.findAll();
    }

    @GraphQLQuery(description = "Get all orders for a given truck")
    public List<Order> getAllOrders(@GraphQLNonNull Long truckId) {
        return orderRepository.findByTruckId(truckId);
    }

    @GraphQLMutation(name = "createOrder", description = "Create a new order")
    public Order createOrder(@GraphQLNonNull NewOrder input) {

        Truck truck = truckRepository
                .findById(input.getTruckId())
                .orElseThrow(ResourceNotFoundException::new);

        Customer customer = customerRepository.findByPhone(input.getCustomerPhone()).orElseGet(() -> {
            Customer newCustomer = new Customer(input.getCustomerPhone());
            return customerRepository.save(newCustomer);
        });

        Set<Item> items = input.getItems()
                .stream()
                .map(i -> new Item(i.getBeanTypeId(), i.getPreparationTypeId()))
                .collect(Collectors.toSet());

        Order order = new Order(truck, customer, items);

        orderRepository.save(order);

        subscribers.get("order").forEach(subscriber -> subscriber.next(order));

        return order;
    }

    @GraphQLMutation(description = "Update the status of a given item")
    public Item updateItemStatus(@GraphQLNonNull @GraphQLId Long id, @GraphQLNonNull @GraphQLId Long statusId) {
        Item item = itemRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        item.setStatusId(statusId);
        itemRepository.save(item);
        return item;
    }

    @GraphQLMutation(description = "Delete a given item")
    public boolean deleteItem(@GraphQLNonNull @GraphQLId Long id) {
        itemRepository.deleteById(id);
        return true;
    }

    @GraphQLSubscription
    public Publisher<Order> orderSubscription() {
        return Flux.create(
                subscriber -> subscribers.add("order", subscriber.onDispose(() -> subscribers.remove("order", subscriber))),
                FluxSink.OverflowStrategy.LATEST);
    }

    @GraphQLQuery(description = "Get bean stats for previous 30 days")
    public List<BeanStats> beanStats() {
        return beanStatsRepository.getBeanStats();
    }
}
