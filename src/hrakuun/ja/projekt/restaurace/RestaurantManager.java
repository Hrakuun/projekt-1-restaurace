package hrakuun.ja.projekt.restaurace;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class RestaurantManager {

    public int currentOrdersCount() {
        int currentOrdersCount = 0;
        for (Order order : OrderManager.getAllOrders()) {
            if (order.isCompleted()) {
                currentOrdersCount++;
            }
        }
        return currentOrdersCount;
    }

    public List<Order> ordersSortedByOrderedTime() {
        List<Order> orders = new ArrayList<>(OrderManager.getAllOrders());
        orders.sort(Comparator.comparing(Order::getOrderedTime));
        return orders;
    }

    public int averageTimeOfOrderFulfilmentInMinutes() {
        int averageTime = 0;
        List<Order> orders = new ArrayList<>(OrderManager.getCompletedOrders());
        long totalTime = 0;
        for (Order order : orders) {
            totalTime += order.getOrderedTime().until(order.getFulfilmentTime(), ChronoUnit.MINUTES);
        }
        if (!orders.isEmpty()) {
            averageTime = (int) (totalTime / orders.size());
        }
        return averageTime;
    }

    public List<Dish> dishesOrderedToday() {
        List<Order> orders = OrderManager.getTodayOrders();
        return getDishesFromCookBook(getDishIdsFromOrders(orders));
    }

    private List<Dish> getDishesFromCookBook(Set<Integer> dishIds) {
        List<Dish> dishesToday = new ArrayList<>();
        for (Integer id : dishIds) {
            dishesToday.add(CookBook.getDishById(id));
        }
        return dishesToday;
    }
    private Set<Integer> getDishIdsFromOrders(List<Order> orders){
        Set<Integer> dishIds = new HashSet<>();
        for (Order order : orders) {
            dishIds.add(order.getDishId());
        }
        return dishIds;
    }

}
