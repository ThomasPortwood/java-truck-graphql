package com.portwood.javatruckgraphql.datacontracts.request;

import java.util.List;

public class NewOrder {

    private Long truckId;

    private String customerPhone;

    private List<NewItem> items;

    public Long getTruckId() {
        return truckId;
    }

    public void setTruckId(Long truckId) {
        this.truckId = truckId;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public List<NewItem> getItems() {
        return items;
    }

    public void setItems(List<NewItem> items) {
        this.items = items;
    }
}
