package hrakuun.ja.projekt.restaurace;

import java.math.BigDecimal;

public class Dish {
// region variables
    private String title;
    private BigDecimal price;
    private int preparationTime;
    private String image;
//    endregion
//    region methods



//    endregion
// region constructors
    public Dish(String title, BigDecimal price, int preparationTime, String image) {
        this.title = title;
        this.price = price;
        this.preparationTime = preparationTime;
        this.image = image;
    }

    public Dish(String title, BigDecimal price, int preparationTime) {
        this(title,price,preparationTime, "blank");
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

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(int preparationTime) throws RestaurantException {
        this.preparationTime = preparationTime;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
//    endregion
}
