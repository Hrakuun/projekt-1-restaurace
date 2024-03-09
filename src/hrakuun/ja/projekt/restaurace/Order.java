package hrakuun.ja.projekt.restaurace;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Order {
    // region variables
    private static int nextId = 1;
    private int orderId;
    private int dishId;
    private int quantity;
    private int tableNumber;
    private LocalDateTime orderedTime;
    private LocalDateTime fulfilmentTime = LocalDateTime.now();
    private boolean isPaid;

//    endregion

    //    region constructors
    public Order(int dishId, int quantity, int tableNumber, boolean isPaid) {
        this(nextId,dishId,quantity,tableNumber, LocalDateTime.now(),null,isPaid);
        nextId++;
    }

    public Order(Dish dish, int quantity, int tableNumber, boolean isPaid) {
        this(nextId,dish.getId(),quantity,tableNumber,LocalDateTime.now(),null,isPaid);
        nextId++;
    }

    public Order(int orderId, int dishId, int quantity, int tableNumber, LocalDateTime orderedTime, LocalDateTime fulfilmentTime, boolean isPaid) {
        this.orderId = orderId;
        this.dishId = dishId;
        this.quantity = quantity;
        this.tableNumber = tableNumber;
        this.orderedTime = orderedTime;
        this.fulfilmentTime = fulfilmentTime;
        this.isPaid = isPaid;
    }
    //    endregion

    //    region methods
    public boolean isCompleted() {
        return fulfilmentTime != null;
    }

    private BigDecimal totalPrice() {
        return CookBook.getDishById(dishId).getPrice().multiply(BigDecimal.valueOf(quantity));
    }

    private String getOrderedTimeInTimeFormat() {
        return orderedTime.format(DateTimeFormatter.ofPattern("hh:mm"));
    }

    private String getFulfilmentTimeInTimeFormat() {
        return fulfilmentTime.format(DateTimeFormatter.ofPattern("hh:mm"));
    }

    private String paidStringFormat() {
        String output = "";
        if (isPaid) {
            output = "zaplaceno";
        }
        return output;
    }

    private String quantityStringFormat() {
        if (quantity > 1) {
            return quantity + "x";
        }
        return "";
    }

    public String stringOutput() {
        char space = ' ';
        String tabulator = "\t";
        Dish dish = CookBook.getDishById(dishId);
        return dish.getTitle() + space + quantityStringFormat() + space + "(" + totalPrice() + " Kč):"
                + tabulator + getOrderedTimeInTimeFormat() + "-"
                + getFulfilmentTimeInTimeFormat() + tabulator + paidStringFormat();
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
