package interfaceNew.parameter;

import java.util.Date;

public class BusinessParam {
    private String orderId;
    private Date time;
    private int amount;

    public BusinessParam(String orderId, Date time, int amount) {
        this.orderId = orderId;
        this.time = time;
        this.amount = amount;
    }

    public BusinessParam() {
    }

    public String getOrderId() {
        return orderId;
    }

    public BusinessParam setOrderId(String orderId) {
        this.orderId = orderId;
        return this;
    }

    public Date getTime() {
        return time;
    }

    public BusinessParam setTime(Date time) {
        this.time = time;
        return this;
    }

    public int getAmount() {
        return amount;
    }

    public BusinessParam setAmount(int amount) {
        this.amount = amount;
        return this;
    }
}
