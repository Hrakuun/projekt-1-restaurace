package hrakuun.ja.projekt.restaurace;

import java.util.ArrayList;
import java.util.List;

public class OrderManager {

    private List<Order> currentOrders = new ArrayList<>();

    public void addOrder(Order order) {
        currentOrders.add(order);
    }

}
