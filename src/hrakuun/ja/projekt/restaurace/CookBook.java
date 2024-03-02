package hrakuun.ja.projekt.restaurace;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CookBook {
    //    region variables
    private List<Dish> cookBook = new ArrayList();
    private String fileName = Settings.getCookBookFilePath();

    // endregion
//    region List handling methods
    public void addDish(Dish dish) {
        cookBook.add(dish);
    }

    public void removeDish(Dish dish) {
        cookBook.remove(dish);
    }

    public Dish getDishOnIndex(int index) {
        return cookBook.get(index);
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

            }
        } catch (FileNotFoundException e) {
            throw new RestaurantException(String.format("Soubor %s nebyl nalezen!\n%s", fileName, e.getLocalizedMessage()));
        }
    }

    public void saveToFile(String fileName) throws RestaurantException {
        String delimiter = Settings.getDelimiter();
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(fileName)))) {
            for (Dish dish : cookBook) {
                writer.println(
                        dish.getId() + delimiter
                                + dish.getTitle() + delimiter
                                + dish.getPrice() + delimiter
                                + dish.getPreparationTime() + delimiter
                                + dish.getImage()
                );
            }
        } catch (FileNotFoundException e){
            throw new RestaurantException("Soubor "+fileName+" nenalezen!\n"+e.getLocalizedMessage());
        } catch (IOException e) {
            throw new RestaurantException("Chyba při zápisu do souboru: "+fileName+":\n" +e.getLocalizedMessage());
        }
    }


//    endregion
}
