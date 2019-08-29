package com.cj.spring.common.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFilter;

//This value in annotation should be the same one as in the config class.
@JsonFilter("field-filter")
public class Application {

    //@JsonFilter  NO need here! But on the class's name.
    private Long id;

    private String name;

    private String pswd;

    private Date pruchaseTime;

    private BigDecimal price;

    /**
     * Test if inner entity can be auto-transfer.
     * There is no need to put annotation: @JsonFilter in User class. Just here in this class is good enough.
     */
    private User user;

    public User getUser() {
        return user;
    }

    public Application setUser(User user) {
        this.user = user;
        return this;
    }

    public Long getId() {
        return id;
    }

    public Application setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Application setName(String name) {
        this.name = name;
        return this;
    }

    public String getPswd() {
        return pswd;
    }

    public Application setPswd(String pswd) {
        this.pswd = pswd;
        return this;
    }

    public Date getPruchaseTime() {
        return pruchaseTime;
    }

    public Application setPruchaseTime(Date pruchaseTime) {
        this.pruchaseTime = pruchaseTime;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Application setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }
}
