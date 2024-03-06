package hrakuun.ja.projekt.restaurace;

import java.time.LocalDateTime;

public class Order {
    // region variables
    private static int nextId = 1;
    private int orderId;
    private int dishId;
    private int quantity;
    private int tableNumber;
    private LocalDateTime orderedTime;
    private LocalDateTime fulfilmentTime = null;
    private boolean isPaid;

//    endregion

    //    region constructors
    public Order(int dishId, int quantity, int tableNumber, LocalDateTime orderedTime, boolean isPaid) {
        this.dishId = dishId;
        this.quantity = quantity;
        this.tableNumber = tableNumber;
        this.orderedTime = orderedTime;
        this.isPaid = isPaid;
        this.orderId = nextId;
        nextId++;
    }

    public Order(Dish dish, int quantity, int tableNumber, LocalDateTime orderedTime, boolean isPaid) {
        this.dishId = dish.getId();
        this.quantity = quantity;
        this.tableNumber = tableNumber;
        this.orderedTime = orderedTime;
        this.isPaid = isPaid;
        this.orderId = nextId;
        nextId++;
    }

    public Order(int orderId, int dishId, int quantity, int tableNumber, LocalDateTime orderedTime, boolean isPaid) {
        this.orderId = orderId;
        this.dishId = dishId;
        this.quantity = quantity;
        this.tableNumber = tableNumber;
        this.orderedTime = orderedTime;
        this.isPaid = isPaid;
    }
    //    endregion

    //    region methods
    public boolean isCompleted() {
        return fulfilmentTime.equals(null);
    }
//    endregion

    //    region set/get
    public int getDishId() {
        return dishId;
    }

    public void setDishId(int dishId) {
        this.dishId = dishId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public LocalDateTime getOrderedTime() {
        return orderedTime;
    }

    public void setOrderedTime(LocalDateTime orderedTime) {
        this.orderedTime = orderedTime;
    }

    public LocalDateTime getFulfilmentTime() {
        return fulfilmentTime;
    }

    public void setFulfilmentTime(LocalDateTime fulfilmentTime) {
        this.fulfilmentTime = fulfilmentTime;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
    //    endregion

}
