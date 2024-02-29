package hrakuun.ja.projekt.restaurace;

import java.util.ArrayList;
import java.util.List;

public class CookBook {
    private List<Dish> cookBook = new ArrayList();

    public void addDish(Dish dish){
        cookBook.add(dish);
    }
    public void removeDish(Dish dish){
        cookBook.remove(dish);
    }
    public Dish getDishOnIndex(int index){
        return cookBook.get(index);
    }

}
