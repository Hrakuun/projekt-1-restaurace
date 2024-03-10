package hrakuun.ja.projekt.restaurace;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.*;
import java.time.LocalDate;

public class OrderManager {

    private static final Map<Integer, Order> orders = new HashMap<>();
    private static final String ordersFile = Settings.getOrdersFilePath();


    public static void addOrder(Order order) throws RestaurantException {
        orders.put(order.getOrderId(), order);
        saveOrdersFile();
    }

    public static void removeOrder(Order order) throws RestaurantException {
        orders.remove(order.getOrderId());
        saveOrdersFile();
    }


    public static void completeOrderById(int orderId) throws RestaurantException {
        Order order = orders.get(orderId);
        Dish dish = CookBook.getDishById(order.getDishId());
        order.setFulfilmentTime(order.getOrderedTime().plusMinutes(dish.getPreparationTime()));
        saveOrdersFile();
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
                Order order = getOrderFromLine(line, lineCounter);
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

    private static Order getOrderFromLine(String line, int lineCounter) throws RestaurantException {
        String[] parts = line.split(Settings.getDelimiter());
        if (parts.length != 7) {
            throw new RestaurantException("Nesprávný počet parametrů na řádku číslo: " + lineCounter + " v souboru: " + ordersFile + "\n");
        }
        int orderId = Integer.parseInt(parts[0]);
        int dishId = Integer.parseInt(parts[1]);
        int quantity = Integer.parseInt(parts[2]);
        int tableNumber = Integer.parseInt(parts[3]);
        LocalDateTime orderedTime = LocalDateTime.parse(parts[4]);
        LocalDateTime fulfilmentTime = readFulfilmentTime(parts[5]);
        boolean isPaid = Boolean.parseBoolean(parts[6]);
        return new Order(orderId, dishId, quantity, tableNumber, orderedTime, fulfilmentTime, isPaid);
    }

    private static LocalDateTime readFulfilmentTime(String part) {
        LocalDateTime fulfilmentTime = null;
        if (!part.isEmpty()) {
            fulfilmentTime = LocalDateTime.parse(part);
        }
        return fulfilmentTime;
    }

    public static void saveOrdersFile() throws RestaurantException {
        String delimiter = Settings.getDelimiter();
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(ordersFile)))) {
            for (Order order : orders.values()) {
                writer.println(
                        order.getOrderId() + delimiter
                                + order.getDishId() + delimiter
                                + order.getQuantity() + delimiter
                                + order.getTableNumber() + delimiter
                                + order.getOrderedTime() + delimiter
                                + order.fulfilmentTimeString() + delimiter
                                + order.isPaid()
                );
            }
        } catch (FileNotFoundException e) {
            throw new RestaurantException("Soubor " + ordersFile + " nenalezen!\n" + e.getLocalizedMessage());
        } catch (IOException e) {
            throw new RestaurantException("Chyba při zápisu do souboru: " + ordersFile + ":\n" + e.getLocalizedMessage());
        }
    }

    public static List<Order> getTodayOrders() {
        List<Order> todayOrders = new ArrayList<>();
        for (Order order : getAllOrders()) {
            if (order.getOrderedTime().toLocalDate().equals(LocalDate.now())) {
                todayOrders.add(order);
            }
        }
        return todayOrders;
    }

    public static List<Order> getAllOrders() {
        return new ArrayList<>(orders.values());
    }

    public static List<Order> getCompletedOrders() {
        List<Order> completedOrders = new ArrayList<>();
        for (Order order : getAllOrders()) {
            if (order.isCompleted()) {
                completedOrders.add(order);
            }
        }
        return completedOrders;
    }

    public static List<Order> getOrdersForTable(int tableNo) {
        List<Order> ordersOnTableNo = new ArrayList<>();
        for (Order order : getAllOrders()) {
            if (order.getTableNumber() == tableNo) {
                ordersOnTableNo.add(order);
            }
        }
        return ordersOnTableNo;
    }
}
