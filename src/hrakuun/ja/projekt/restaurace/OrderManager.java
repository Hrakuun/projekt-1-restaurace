package hrakuun.ja.projekt.restaurace;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OrderManager {

    private static List<Order> currentOrders = new ArrayList<>();


    public void addOrder(Order order) {
        currentOrders.add(order);
    }
    public void completeOrder(Order order){
        order.setFulfilmentTime(LocalDateTime.now());
    }

    private boolean doesntFileExist(String fileName){;
        return Files.notExists(Path.of(fileName));

    }
    public void loadCurrentOrdersFile (String fileName) throws RestaurantException {
        if(doesntFileExist(fileName)){

        }
        int lineCounter = 0;
        try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(fileName)))){
            while (scanner.hasNextLine()){
                lineCounter++;
                String line = scanner.nextLine();
                String[] parts = line.split(Settings.getDelimiter());
                int orderId = Integer.parseInt(parts[0]);
                int dishId = Integer.parseInt(parts[1]);
                int quantity = Integer.parseInt(parts[2]);
                int tableNumber = Integer.parseInt(parts[3]);
                LocalDateTime orderedTime = LocalDateTime.parse(parts[4]);
                boolean isPaid = Boolean.parseBoolean(parts[5]);
                Order order = new Order(orderId,dishId,quantity,tableNumber,orderedTime,isPaid);
                currentOrders.add(order);
            }
        } catch (FileNotFoundException e) {
            throw new RestaurantException("Soubor "+fileName+" nenalezen.");
        }
    }
    public void saveCurrentOrdersFile (String fileName) throws RestaurantException {
        String delimiter = Settings.getDelimiter();
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(fileName)))){
            for (Order order : currentOrders){
                writer.println(
                        order.getOrderId() + delimiter
                        + order.getDishId() + delimiter
                        + order.getQuantity() + delimiter
                        + order.getTableNumber() + delimiter
                        + order.getOrderedTime() + delimiter
                        + order.isPaid()
                );
            }
        } catch (IOException e) {
            throw new RestaurantException("Chyba při zápisu do souboru: "+fileName+":\n" +e.getLocalizedMessage());
        }
    }

}
