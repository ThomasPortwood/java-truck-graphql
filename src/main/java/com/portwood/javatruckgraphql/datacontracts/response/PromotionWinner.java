package com.portwood.javatruckgraphql.datacontracts.response;

public class PromotionWinner {

    private String phone;

    private CouponType couponType;

    public PromotionWinner(String phone, CouponType couponType) {
        this.phone = phone;
        this.couponType = couponType;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public CouponType getCouponType() {
        return couponType;
    }

    public void setCouponType(CouponType couponType) {
        this.couponType = couponType;
    }
}
