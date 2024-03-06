package hrakuun.ja.projekt.restaurace;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.*;

public class OrderManager {

    private static Map<Integer, Order> orders = new HashMap<>();
    private static String ordersFile = Settings.getOrdersFilePath();


    public void addOrder(Order order) throws RestaurantException {
        orders.put(order.getOrderId(), order);
        saveOrdersFile(ordersFile);
    }

    public void removeOrder(Order order) throws RestaurantException {
        orders.remove(order.getOrderId());
        saveOrdersFile(ordersFile);
    }

    public void completeOrder(Order order) throws RestaurantException {
        if (order.isPaid()) {
            order.setFulfilmentTime(LocalDateTime.now());
        }
        saveOrdersFile(ordersFile);
    }

    private static boolean doesFileExist(String fileName) {
        return Files.exists(Path.of(fileName));
    }

    public static void loadOrdersFile(String fileName) throws RestaurantException {
        if (doesFileExist(fileName)) {
            loadFile(fileName);
        }
    }

    private static void loadFile(String fileName) throws RestaurantException {
        int lineCounter = 0;
        try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(fileName)))) {
            while (scanner.hasNextLine()) {
                lineCounter++;
                String line = scanner.nextLine();
                Order order = getOrderFromLine(line);
                if (!orders.containsKey(order.getOrderId())) {
                    orders.put(order.getOrderId(), order);
                }
            }
        } catch (FileNotFoundException e) {
            throw new RestaurantException("Soubor " + fileName + " nenalezen.\n" + e.getLocalizedMessage());
        } catch (NumberFormatException e) {
            throw new RestaurantException("Neplatný formát čísel na řádku: " + lineCounter + "\n" + e.getLocalizedMessage());
        } catch (NullPointerException | ClassCastException | UnsupportedOperationException |
                 IllegalArgumentException e) {
            throw new RestaurantException("Chyba při načítání dat ze souboru na řádku: " + lineCounter + "\n" + e.getLocalizedMessage());
        }
    }

    private static Order getOrderFromLine(String line) {
        String[] parts = line.split(Settings.getDelimiter());
        int orderId = Integer.parseInt(parts[0]);
        int dishId = Integer.parseInt(parts[1]);
        int quantity = Integer.parseInt(parts[2]);
        int tableNumber = Integer.parseInt(parts[3]);
        LocalDateTime orderedTime = LocalDateTime.parse(parts[4]);
        boolean isPaid = Boolean.parseBoolean(parts[5]);
        Order order = new Order(orderId, dishId, quantity, tableNumber, orderedTime, isPaid);
        return order;
    }

    public void saveOrdersFile(String fileName) throws RestaurantException {
        String delimiter = Settings.getDelimiter();
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(fileName)))) {
            for (Order order : orders.values()) {
                writer.println(
                        order.getOrderId() + delimiter
                                + order.getDishId() + delimiter
                                + order.getQuantity() + delimiter
                                + order.getTableNumber() + delimiter
                                + order.getOrderedTime() + delimiter
                                + order.isPaid()
                );
            }
        } catch (FileNotFoundException e) {
            throw new RestaurantException("Soubor " + fileName + " nenalezen!\n" + e.getLocalizedMessage());
        } catch (IOException e) {
            throw new RestaurantException("Chyba při zápisu do souboru: " + fileName + ":\n" + e.getLocalizedMessage());
        }
    }

    public static List<Order> getAllOrders(){
        return new ArrayList<>(orders.values());
    }
    public static List<Order> getCompletedOrders(){
        List<Order> completedOrders = new ArrayList<>();
        for(Order order : getAllOrders()){
            if(order.isCompleted()){
                completedOrders.add(order);
            }
        }
        return completedOrders;
    }
}
