package hrakuun.ja.projekt.restaurace;

import java.time.LocalDateTime;

public class Order {
// region variables
    private static int orderId = 1;
    private Dish dish;
    private int quantity;
    private int tableNumber;
    private LocalDateTime orderedTime;
    private LocalDateTime fulfilmentTime;
    private boolean isPaid;
//    endregion

//    region constructors
    public Order(Dish dish, int quantity, int tableNumber, LocalDateTime orderedTime, boolean isPaid) {
        this.dish = dish;
        this.quantity = quantity;
        this.tableNumber = tableNumber;
        this.orderedTime = orderedTime;
        this.isPaid = isPaid;
        orderId++;
    }
//    endregion

//    region methods
//    endregion

//    region set/get
    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
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

//    endregion

}
