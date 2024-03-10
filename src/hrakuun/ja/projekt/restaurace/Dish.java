package hrakuun.ja.projekt.restaurace;

import java.math.BigDecimal;

public class Dish {
    // region variables
    private static int nextId = 1;
    private int id;
    private String title;
    private BigDecimal price;
    private int preparationTime;
    private String image;
//    endregion
//    region methods


    //    endregion
// region constructors
    public Dish(String title, BigDecimal price, int preparationTime, String image) throws RestaurantException {
        this(nextId, title, price, preparationTime, image);
        nextId++;
    }

    public Dish(int id, String title, BigDecimal price, int preparationTime, String image) throws RestaurantException {
        this.title = title;
        setPrice(price);
        setPreparationTime(preparationTime);
        setImage(image);
        setId(id);
    }

    public Dish(String title, BigDecimal price, int preparationTime) throws RestaurantException {
        this(title, price, preparationTime, "blank");
    }

    //    endregion
// region get/set
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) throws RestaurantException {
        if (price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RestaurantException("Cena musí být větší než nula!");
        }
        this.price = price;
    }

    public int getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(int preparationTime) throws RestaurantException {
        if (preparationTime <= 0) {
            throw new RestaurantException("Čas přípravy pokrmu musí být větší než nula!");
        }
        this.preparationTime = preparationTime;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) throws RestaurantException {
        if (image.isBlank()) {
            throw new RestaurantException("Položka musí mít zadán název obrázku!");
        }
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
//    endregion

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
