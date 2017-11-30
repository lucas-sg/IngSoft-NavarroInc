package mercadoNavarro;

import java.util.List;

public class Item {
    private Seller seller;
    private String name;
    private String description;
    private Integer stock;
    private String pickup;
    private Double price;
    private Integer itemid;
    private List<String> gallery;
    private List<String> comments;


    public Item(Seller seller, String name, String description, Integer stock, String pickup, Double price, List<String> gallery) {
        this.seller = seller;
        this.name = name;
        this.description = description;
        this.stock = stock;
        this.pickup = pickup;
        this.price = price;
        this.gallery = gallery;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getPickup() {
        return pickup;
    }

    public void setPickup(String pickup) {
        this.pickup = pickup;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getItemid() {
        return itemid;
    }

    public void setItemid(Integer itemid) {
        this.itemid = itemid;
    }

    public List<String> getGallery() {
        return gallery;
    }

    public void setGallery(List<String> gallery) {
        this.gallery = gallery;
    }

    public List<String> getComments() {
        return comments;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }
}
