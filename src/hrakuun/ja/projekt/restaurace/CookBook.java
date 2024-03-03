package hrakuun.ja.projekt.restaurace;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

public class CookBook {
    //    region variables
    private Map<Integer, Dish> cookBook = new HashMap<>();
    private String fileName = Settings.getCookBookFilePath();


    // endregion

//    region List handling methods
    public void addDish(Dish dish) throws RestaurantException {
        cookBook.put(dish.getId(), dish);
        saveToFile();
    }

    public void removeDish(Dish dish) throws RestaurantException {
        cookBook.remove(dish.getId());
        saveToFile();
    }

    public Dish getDishById(Integer id) {
        return cookBook.get(id);
    }
// endregion
//    region file handling methods

    public void loadFile(String fileName) throws RestaurantException {
        int lineCounter = 0;
        try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(fileName)))) {
            while (scanner.hasNextLine()) {
                lineCounter++;
                String line = scanner.nextLine();
                String[] parts = line.split(Settings.getDelimiter());
                int dishId = Integer.parseInt(parts[0]);
                String title = parts[1];
                BigDecimal price = BigDecimal.valueOf(Long.parseLong(parts[2]));
                int preparationTime = Integer.parseInt(parts[3]);
                String image = parts[4];
                if (!cookBook.containsKey(dishId)) {
                    cookBook.put(dishId, new Dish(dishId, title, price, preparationTime, image));
                }

            }
        } catch (FileNotFoundException e) {

        }
    }

    public void saveToFile() throws RestaurantException {
        String delimiter = Settings.getDelimiter();
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(fileName)))) {
            for (Dish dish : cookBook.values()) {
                writer.println(
                        dish.getId() + delimiter
                                + dish.getTitle() + delimiter
                                + dish.getPrice() + delimiter
                                + dish.getPreparationTime() + delimiter
                                + dish.getImage()
                );
            }
        } catch (FileNotFoundException e) {
            throw new RestaurantException("Soubor " + fileName + " nenalezen!\n" + e.getLocalizedMessage());
        } catch (IOException e) {
            throw new RestaurantException("Chyba při zápisu do souboru: " + fileName + ":\n" + e.getLocalizedMessage());
        }
    }

    public void updateCookBook() throws RestaurantException {
        cookBook.clear();
        loadFile(fileName);
    }

//    endregion
//  region get/set

    public Map<Integer, Dish> getCookBook() {
        return new HashMap<>(cookBook);
    }

//    endregion
}
