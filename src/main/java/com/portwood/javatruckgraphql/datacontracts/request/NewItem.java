package com.portwood.javatruckgraphql.datacontracts.request;

public class NewItem {

    private Long beanTypeId;

    private Long preparationTypeId;

    public Long getBeanTypeId() {
        return beanTypeId;
    }

    public void setBeanTypeId(Long beanTypeId) {
        this.beanTypeId = beanTypeId;
    }

    public Long getPreparationTypeId() {
        return preparationTypeId;
    }

    public void setPreparationTypeId(Long preparationTypeId) {
        this.preparationTypeId = preparationTypeId;
    }
}
