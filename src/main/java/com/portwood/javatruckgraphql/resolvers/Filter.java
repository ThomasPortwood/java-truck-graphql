package com.portwood.javatruckgraphql.resolvers;

import java.util.List;

public class Filter {

    private List<Long> ids;
    private String q;

    public Filter(List<Long> ids, String q) {
        this.ids = ids;
        this.q = q;
    }

    public Filter() {
    }

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }
}
