package hrakuun.ja.projekt.restaurace;

// Discord name: hrakuun / Jan Maxa

import java.math.BigDecimal;
import java.util.List;

public class MainRestaurant {

    public static void main(String[] args){
        try{
//            1. úkol
            loadFiles();
        } catch (RestaurantException e) {
            System.err.println(e.getLocalizedMessage());
        }

        try {
//
//            2. úkol
            createTestData();
//            3. úkol
            totalPriceForTablePrint(15);
//            4.1. úkol
            unfinishedOrdersCountPrint();
//            4.2. úkol
            ordersSortedPrint();
//            4.3. úkol
            averageFulfilmentTimePrint();
//            4.5. úkol
            typeOfDishesOrdered();
//            4.6. úkol
            ordersForTableReport(15);
            ordersForTableReport(2);
//            5. úkol
            saveFiles();

        } catch (RestaurantException e) {
            System.err.println(e.getLocalizedMessage());
        } catch (Exception e) {
            System.err.println("Při běhu programu došlo k chybě: " + e.getLocalizedMessage());
        }
    }

    public static void loadFiles() throws RestaurantException {
        CookBook.loadCookBookFile(Settings.getCookBookFilePath());
        OrderManager.loadOrdersFile(Settings.getOrdersFilePath());
    }

    public static void createTestDishes() throws RestaurantException {
        CookBook.addDish(new Dish("Kuřecí řízek obalovaný 150 g", BigDecimal.valueOf(170), 15));
        CookBook.addDish(new Dish("Hranolky 150 g", BigDecimal.valueOf(62), 10));
        CookBook.addDish(new Dish("Pstruh na víně 200 g", BigDecimal.valueOf(213), 21));
        CookBook.addDish(new Dish("Kofola 0,5 l", BigDecimal.valueOf(47), 2));
    }

    public static void createTestOrders() throws RestaurantException {
        createTestOrder(1, 2, 15, false);
        createTestOrder(2, 2, 15, false);
        createTestOrder(4, 2, 15, false);
        completeOrder(3);
        createTestOrder(3, 2, 2, true);
    }

    public static void createTestOrder(int dishId, int quantity, int tableNo, boolean isPaid) throws RestaurantException {
        OrderManager.addOrder(new Order(dishId, quantity, tableNo, isPaid));
    }

    public static void completeOrder(int orderId) throws RestaurantException {
        OrderManager.completeOrderById(orderId);
    }

    public static void createTestData() throws RestaurantException {
        createTestDishes();
        createTestOrders();
    }

    public static void totalPriceForTablePrint(int tableNo) {
        System.out.printf("Celková cena objednávek pro stůl č. %s je: %s\n\n", tableNo, RestaurantManager.totalPriceForTable(tableNo).toString());
    }

    public static void unfinishedOrdersCountPrint() {
        System.out.println("Počet rozpracovaných objednávek: " + RestaurantManager.currentOrdersCount());
        System.out.println();
    }

    public static void ordersSortedPrint() {
        System.out.println("Objednávky seřazené dle času objednání:");
        List<Order> orders = RestaurantManager.ordersSortedByOrderedTime();
        for (Order order : orders) {
            System.out.println(order);
        }
        System.out.println();

    }

    public static void averageFulfilmentTimePrint() {
        double averageTime = RestaurantManager.averageTimeOfOrderFulfilmentInMinutes();
        System.out.println("Průměrná doba dokončení objednávky: " + averageTime);
        System.out.println();
    }

    public static void typeOfDishesOrdered() {
        System.out.println("Jídla objednáná dnes: ");
        List<Dish> dishes = RestaurantManager.dishesOrderedToday();
        for (Dish dish : dishes) {
            System.out.println(dish);
        }
        System.out.println();
    }

    public static void ordersForTableReport(int tableNo) {
        System.out.println(RestaurantManager.ordersOnTableReport(tableNo));
        System.out.println();
    }
    public static void saveFiles() throws RestaurantException {
        CookBook.saveToFile();
        OrderManager.saveOrdersFile();
    }
}
