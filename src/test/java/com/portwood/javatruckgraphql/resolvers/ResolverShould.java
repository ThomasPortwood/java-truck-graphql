package com.portwood.javatruckgraphql.resolvers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.graphql.spring.boot.test.GraphQLResponse;
import com.graphql.spring.boot.test.GraphQLTestTemplate;
import com.portwood.javatruckgraphql.datacontracts.request.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ResolverShould {

    @Autowired
    private GraphQLTestTemplate graphQLTestTemplate;

    @Test
    public void runTests() throws IOException {

        createNewTrucks();
        createNewBeanTypes();
        createNewPreparationTypes();
        createNewStatuses();
        createOrderForNewCustomerAndExistingCustomerWithCorrectStatus();
        createNewOrderAndUpdateItemStatusInThatOrder();
        createsOrderAndDeletesItem();
    }

    public void createNewTrucks() throws IOException {

        // arrange
        ObjectMapper mapper = new ObjectMapper();
        NewTruck newTruck = new NewTruck();
        newTruck.setName("Test Truck");
        ObjectNode variablesNode = mapper.createObjectNode();
        variablesNode.putPOJO("input", newTruck);

        // act
        GraphQLResponse response = graphQLTestTemplate.perform("CreateTruck.graphql", variablesNode);

        // assert
        assertNotNull(response);
        assertTrue(response.isOk());
        assertEquals("Test Truck", response.get("$.data.createTruck.name"));
    }

    public void createNewStatuses() throws IOException {

        // arrange
        ObjectMapper mapper = new ObjectMapper();
        NewStatus createdStatus = new NewStatus();
        createdStatus.setName("Created");
        ObjectNode variables1 = mapper.createObjectNode();
        variables1.putPOJO("input", createdStatus);

        // act
        GraphQLResponse response1 = graphQLTestTemplate.perform("CreateStatus.graphql", variables1);

        // assert
        assertNotNull(response1);
        assertTrue(response1.isOk());
        assertEquals("Created", response1.get("$.data.createStatus.name"));

        // arrange
        NewStatus completedStatus = new NewStatus();
        completedStatus.setName("Completed");
        ObjectNode variables2 = mapper.createObjectNode();
        variables2.putPOJO("input", completedStatus);

        // act
        GraphQLResponse response2 = graphQLTestTemplate.perform("CreateStatus.graphql", variables2);

        // assert
        assertNotNull(response2);
        assertTrue(response2.isOk());
        assertEquals("Completed", response2.get("$.data.createStatus.name"));
    }

    public void createNewBeanTypes() throws IOException {

        // arrange
        ObjectMapper mapper = new ObjectMapper();
        NewBeanType newBeanType = new NewBeanType();
        newBeanType.setName("Kona");
        newBeanType.setPriceMultiplier(1.0);
        ObjectNode variablesNode = mapper.createObjectNode();
        variablesNode.putPOJO("input", newBeanType);

        // act
        GraphQLResponse response = graphQLTestTemplate.perform("CreateBeanType.graphql", variablesNode);

        // assert
        assertNotNull(response);
        assertTrue(response.isOk());
        assertEquals("Kona", response.get("$.data.createBeanType.name", String.class));
    }

    public void createNewPreparationTypes() throws IOException {

        // arrange
        ObjectMapper mapper = new ObjectMapper();
        NewPreparationType newPreparationType = new NewPreparationType();
        newPreparationType.setName("Espresso");
        newPreparationType.setPrice(4.2);
        ObjectNode variablesNode = mapper.createObjectNode();
        variablesNode.putPOJO("input", newPreparationType);

        // act
        GraphQLResponse response = graphQLTestTemplate.perform("CreatePreparationType.graphql", variablesNode);

        // assert
        assertNotNull(response);
        assertTrue(response.isOk());
        assertEquals("Espresso", response.get("$.data.createPreparationType.name", String.class));
    }

    @Test
    public void createOrderForNewCustomerAndExistingCustomerWithCorrectStatus() throws IOException {

        // arrange
        ObjectMapper mapper = new ObjectMapper();
        NewOrder newOrder = new NewOrder();
        newOrder.setTruckId(1L);
        newOrder.setCustomerPhone("555-555-5555");
        newOrder.setItems(List.of(new NewItem() {{
            setBeanTypeId(1L);
            setPreparationTypeId(1L);
        }}));
        ObjectNode variablesNode = mapper.createObjectNode();
        variablesNode.putPOJO("input", newOrder);

        // act
        GraphQLResponse response1 = graphQLTestTemplate.perform("CreateOrder.graphql", variablesNode);

        // assert
        assertNotNull(response1);
        assertTrue(response1.isOk());
        assertEquals(1L, response1.get("$.data.createOrder.items[0].statusId", Long.class));

        // arrange
        Integer customerId = response1.get("$.data.createOrder.customer.id", Integer.class);

        // act
        GraphQLResponse response2 = graphQLTestTemplate.perform("CreateOrder.graphql", variablesNode);

        // assert
        assertNotNull(response2);
        assertTrue(response2.isOk());
        assertEquals(customerId, response2.get("$.data.createOrder.customer.id", Integer.class));
        assertEquals(1L, response2.get("$.data.createOrder.items[0].statusId", Long.class));
    }

    public void createNewOrderAndUpdateItemStatusInThatOrder() throws IOException {

        // arrange
        ObjectMapper mapper = new ObjectMapper();
        NewOrder newOrder = new NewOrder();
        newOrder.setTruckId(1L);
        newOrder.setCustomerPhone("555-555-5555");
        newOrder.setItems(List.of(new NewItem() {{
            setBeanTypeId(1L);
            setPreparationTypeId(1L);
        }}));
        ObjectNode variables1 = mapper.createObjectNode();
        variables1.putPOJO("input", newOrder);

        // act
        GraphQLResponse response1 = graphQLTestTemplate.perform("CreateOrder.graphql", variables1);

        // assert
        assertNotNull(response1);
        assertTrue(response1.isOk());
        assertEquals(1L, response1.get("$.data.createOrder.items[0].statusId", Long.class));

        // arrange
        Integer itemId = response1.get("$.data.createOrder.items[0].id", Integer.class);
        ObjectNode variables2 = mapper.createObjectNode();
        variables2.put("id", itemId);
        variables2.put("statusId", 2);

        // act
        GraphQLResponse response2 = graphQLTestTemplate.perform("UpdateItemStatus.graphql", variables2);

        // assert
        assertNotNull(response1);
        assertTrue(response1.isOk());
        assertEquals(2L, response2.get("$.data.updateItemStatus.statusId", Long.class));
    }

    public void createsOrderAndDeletesItem() throws IOException {

        // arrange
        ObjectMapper mapper = new ObjectMapper();
        NewOrder newOrder = new NewOrder();
        newOrder.setTruckId(1L);
        newOrder.setCustomerPhone("555-555-5555");
        newOrder.setItems(List.of(new NewItem() {{
            setBeanTypeId(1L);
            setPreparationTypeId(1L);
        }}));
        ObjectNode variables1 = mapper.createObjectNode();
        variables1.putPOJO("input", newOrder);

        // act
        GraphQLResponse response1 = graphQLTestTemplate.perform("CreateOrder.graphql", variables1);

        // assert
        assertNotNull(response1);
        assertTrue(response1.isOk());
        assertEquals(1L, response1.get("$.data.createOrder.items[0].statusId", Long.class));

        // arrange
        Long itemId = response1.get("$.data.createOrder.items[0].id", Long.class);
        ObjectNode variables2 = mapper.createObjectNode();
        variables2.put("id", itemId);

        // act
        GraphQLResponse response2 = graphQLTestTemplate.perform("DeleteItem.graphql", variables2);

        // assert
        assertNotNull(response2);
        assertTrue(response2.isOk());
    }
}
