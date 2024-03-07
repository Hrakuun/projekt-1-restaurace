package hrakuun.ja.projekt.restaurace;

import java.math.BigDecimal;

public class MainRestaurant {

    public static void main(String[] args) throws RestaurantException {

        CookBook cookBook = new CookBook();
        OrderManager orderManager = new OrderManager();
        RestaurantManager restaurantManager = new RestaurantManager();
        Dish water = new Dish("Minerální voda", BigDecimal.valueOf(34),1);
        Dish juice = new Dish("džus", BigDecimal.valueOf(50),1);
        Order order = new Order(water,2,4,true);

        try{
            loadFiles();
            cookBook.addDish(water);
            cookBook.addDish(juice);
            cookBook.removeDish(water);
            cookBook.addDish(water);
            orderManager.addOrder(order);
            orderManager.completeOrder(order);
        } catch (RestaurantException e) {
            System.err.println(e.getLocalizedMessage());
        }
        System.out.println(restaurantManager.ordersOnTableReport(4));

    }
    public static void loadFiles() throws RestaurantException {
        CookBook.loadCookBookFile(Settings.getCookBookFilePath());
        OrderManager.loadOrdersFile(Settings.getOrdersFilePath());
    }

}
