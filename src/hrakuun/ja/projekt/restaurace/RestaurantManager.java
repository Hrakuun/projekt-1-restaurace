package hrakuun.ja.projekt.restaurace;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class RestaurantManager {

    public static int currentOrdersCount() {
        int currentOrdersCount = 0;
        for (Order order : OrderManager.getAllOrders()) {
            if (order.isCompleted()) {
                currentOrdersCount++;
            }
        }
        return currentOrdersCount;
    }

    public static List<Order> ordersSortedByOrderedTime() {
        List<Order> orders = new ArrayList<>(OrderManager.getAllOrders());
        orders.sort(Comparator.comparing(Order::getOrderedTime));
        return orders;
    }

    public static double averageTimeOfOrderFulfilmentInMinutes() {
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

    public static List<Dish> dishesOrderedToday() {
        List<Order> orders = OrderManager.getTodayOrders();
        return getDishesFromCookBook(getDishIdsFromOrders(orders));
    }

    private static List<Dish> getDishesFromCookBook(Set<Integer> dishIds) {
        List<Dish> dishesToday = new ArrayList<>();
        for (Integer id : dishIds) {
            dishesToday.add(CookBook.getDishById(id));
        }
        return dishesToday;
    }

    private static Set<Integer> getDishIdsFromOrders(List<Order> orders) {
        Set<Integer> dishIds = new HashSet<>();
        for (Order order : orders) {
            dishIds.add(order.getDishId());
        }
        return dishIds;
    }

    private static String tableNoToString(int tableNo) {
        String tableNoString = String.valueOf(tableNo);
        if (tableNoString.length() < 2) {
            tableNoString = " " + (tableNo);
        }
        return tableNoString;
    }

    private static String headlineOfReport(int tableNo) {
        return String.format("** Objednávky pro stůl č. %s **", tableNoToString(tableNo));
    }

    private static String lineOfReport(Order order, int lineCounter) {
        return lineCounter + ". " + order.stringOutput() + "\n";
    }

    private static String linesOrdersReport(List<Order> orders) {
        int lineCounter = 1;
        StringBuilder report = new StringBuilder();
        for (Order order : orders) {
            report.append(lineOfReport(order, lineCounter));
            lineCounter++;
        }
        return report.toString();
    }

    public static String ordersOnTableReport(int tableNo) {
        List<Order> orders = OrderManager.getOrdersForTable(tableNo);
        String report;
        report = headlineOfReport(tableNo) + "\n****\n" + linesOrdersReport(orders) + "******";
        return report;
    }

    public static BigDecimal totalPriceForTable(int tableNo) {
        List<Order> orders = OrderManager.getOrdersForTable(tableNo);
        BigDecimal totalPricePerTable = BigDecimal.ZERO;
        for (Order order : orders) {
            totalPricePerTable = totalPricePerTable.add(order.totalPrice());
        }
        return totalPricePerTable;
    }

}
