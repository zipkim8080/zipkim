package com.kb.zipkim.domain.bookMark.dto;

public class BookMarkRequest {
    private String username;
    private String probid;
    private String deposit;
    private String amount;
    private String floor;
    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDeposit() {
        return deposit;
    }

    public void setDeposit(String deposit) {
        this.deposit = deposit;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProbid() {
        return probid;
    }

    public void setProbid(String probid) {
        this.probid = probid;
    }
}