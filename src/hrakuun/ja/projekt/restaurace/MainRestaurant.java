package hrakuun.ja.projekt.restaurace;

import java.math.BigDecimal;

public class MainRestaurant {

    public static void main(String[] args) throws RestaurantException {

        CookBook cookBook = new CookBook();
        OrderManager orderManager = new OrderManager();
        Dish water = new Dish("Minerální voda", BigDecimal.valueOf(34),1);
        Dish juice = new Dish("džus", BigDecimal.valueOf(50),1);


        try{
            loadFiles();
            cookBook.addDish(water);
            cookBook.addDish(juice);
            cookBook.removeDish(water);
            cookBook.addDish(water);
//            orderManager.addOrder(new Order(water,2,4));
        } catch (RestaurantException e) {
            System.err.println(e.getLocalizedMessage());
        }

    }
    public static void loadFiles() throws RestaurantException {
        CookBook.loadCookBookFile(Settings.getCookBookFilePath());
        OrderManager.loadOrdersFile(Settings.getOrdersFilePath());
    }

}
