# Java Truck!

### Design a database schema for the business

DBeaver 'Entity Relationship Diagram':

![Schema Diagram](/images/dbeaver_ER_diagram.png)

Tables were designed in a simple way without complex data types. In production, we would probably add JSON columns 
to each major table called 'attributes' or something similar to allow flexibility. Table definitions can be found 
[here](https://github.com/ThomasPortwood/java-truck-graphql/blob/main/src/main/resources/db/migration/V0.0.1__initial_tables.sql).

### Design an API for the applications

The GraphQL schema can be viewed [here](https://github.com/ThomasPortwood/java-truck-graphql/blob/main/src/test/resources/schema.graphql)
or browsed using the following clients:
 
Altair GraphQL client - [https://graphql.thomasportwood.com/altair](https://graphql.thomasportwood.com/altair)

Voyager GraphQL Browser - [https://graphql.thomasportwood.com/voyager](https://graphql.thomasportwood.com/voyager)

GraphQL operations were included to support:
    - Adding new trucks, beans (Kona, Arabica, etc), preparation types (espresso, french press, etc), and item statuses
    - Creating orders for new or existing customers (distinguished only by phone number in this simple schema)
    - Updating the status of individual items in an order
    - Deleting items from orders
    - Viewing orders for a specific truck
    
A GraphQL subscription was included to support the development of the barista app. It can be tested by (work in progress).
    
### Write a query to figure out the least popular bean over the last 30 days

This query reads all items from the previous 30 days for a given truck, groups the items by bean type, and sorts the 
groups by ascending total count. This will give us a running metric to consider which bean to replace:

[Bean Stats!](https://github.com/ThomasPortwood/java-truck-graphql/blob/main/src/main/java/com/portwood/javatruckgraphql/datasources/mysql/repositories/BeanStatsRepository.java#L15)

### Special Promotion

A 'Promotion Calculator' was added to efficiently produce usable data for distribution of promotional coupons. A group 
of orders is sorted by creation date to determine every 11th and every 17th order, and which ones are common. An enum 
is used to represent the type of coupon the customer will receive:

[Promotion Calculation!](https://github.com/ThomasPortwood/java-truck-graphql/blob/main/src/main/java/com/portwood/javatruckgraphql/logic/PromotionCalculator.java#L22)

#### Assumptions you’ve made

#### Any system description you feel is helpful or necessary

#### Other approaches you considered on any part of the exercise 

#### What you’d do differently or additionally if you had more time 

#### Instructions on how to run the code from Part 4

#### Time spent 