# Java Truck!

### Design a database schema for the business

DBeaver 'Entity Relationship Diagram':

![Schema Diagram](/images/dbeaver_ER_diagram.png)

Tables were designed in a simple way without complex data types. In production, we would probably add JSON columns 
to each major table called 'attributes' or something similar to allow flexibility. Table definitions can be found 
[here](https://github.com/ThomasPortwood/java-truck-graphql/blob/main/src/main/resources/db/migration/V0.0.1__initial_tables.sql).

### Design an API for the applications

- [Spring Initializr](https://start.spring.io/) was used to create a [Spring Boot](https://spring.io/guides/gs/spring-boot/) 
Java application with dependencies managed by the [Gradle](https://gradle.org/) build tool.

- [Spring Data Rest](https://spring.io/projects/spring-data-rest) was installed to provide RESTful access if required. 
See the [description](https://graphql.thomasportwood.com/) for navigating the REST portion of the API).

- [GraphQL SPQR](https://github.com/leangen/graphql-spqr) was used to develop a GraphQL API using 'code-first' approach 
in the interest of speed and code simplicity (automatic schema generation). The GraphQL schema can be viewed 
[here](https://github.com/ThomasPortwood/java-truck-graphql/blob/main/src/test/resources/schema.graphql) or browsed 
using the following clients: 
    - Altair GraphQL client - [https://graphql.thomasportwood.com/altair](https://graphql.thomasportwood.com/altair)
    - Voyager GraphQL Browser - [https://graphql.thomasportwood.com/voyager](https://graphql.thomasportwood.com/voyager)

- GraphQL operations were included to support:
    - Adding new trucks, beans (Kona, Arabica, etc), preparation types (espresso, french press, etc), and item statuses
    - Creating orders for new or existing customers (distinguished only by phone number in this simple schema)
    - Updating the status of individual items in an order
    - Deleting items from orders
    - Viewing orders for a specific truck
    
- A GraphQL subscription was included to support the development of the barista app. It works!! It can be tested by:
    - Browsing to [Altair](https://graphql.thomasportwood.com/altair)
    - Ensuring the subscription URL is correct by selecting 'Subscription URL' on the left toolbar and setting it to 
    `wss://graphql.thomasportwood.com/subscriptions`
    - Add a subscription to the request like: 
        ```
        subscription {
          orderSubscription {
            id
            customer {
              phone
            }
            items {
              beanTypeId
              preparationTypeId
            }
          }
        }
        ```
    - Click on 'Send' (you should see a 'Clear' and a 'Stop' button appear)
    - In another window, submit an order like: 
        ```
        mutation {
          createOrder(
            input: {
              truckId: 1
              customerPhone: "425-343-3019"
              items: [{ beanTypeId: 1, preparationTypeId: 1 }]
            }
          ) {
            createdAt
            id
          }
        }
        ```
    - Check the first window for an update! Super powerful!
    
### Write a query to figure out the least popular bean over the last 30 days

- [This native SQL query](https://github.com/ThomasPortwood/java-truck-graphql/blob/main/src/main/java/com/portwood/javatruckgraphql/datasources/mysql/repositories/BeanStatsRepository.java#L15) 
reads all items from the previous 30 days for a given truck, groups the items by bean type, and sorts the 
groups by ascending total count. This will give us a running metric to consider which bean to replace.
- Note that the bean stats for a specific truck are exposed in a GraphQL query 

### Special Promotion

- A [Promotion Calculator](https://github.com/ThomasPortwood/java-truck-graphql/blob/main/src/main/java/com/portwood/javatruckgraphql/logic/PromotionCalculator.java#L22) 
was created to contain logic that should efficiently produce usable data for distribution of promotional coupons. A 
group of orders from a given time range is sorted by ascending creation date, and the customer of every order that is 
eligible for a coupon is included in the response. An enum is used to represent the type of coupon the customer will 
receive. The calculation is exposed in a GraphQL query:
    ```
    query {
      promotionWinners(truckId: 1, days: 7){
        couponType
        phone
      }
    }
    ```

#### Other approaches you considered on any part of the exercise 
- I considered using a simpler REST approach by way of [Spring Data Rest](https://spring.io/projects/spring-data-rest) 
and following the [HATEOAS]((https://spring.io/projects/spring-hateoas)) principle which I find interesting and useful.

#### What you’d do differently or additionally if you had more time 
- The 'resolver' only deals with a single data source at the moment (MYSQL), but I would still break it up in to 
separate classes in anticipation of additional data sources.
- The GraphQL tests only served as a debugging tool. I would reorganize them to be more atomic and test failures and 
edge cases. 
- I didn't include any authentication or authorization. I would probably start with Auth0 or a similar solution.