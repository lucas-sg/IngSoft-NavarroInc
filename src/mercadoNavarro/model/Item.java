package mercadoNavarro.model;

import java.util.LinkedList;
import java.util.List;

import mercadoNavarro.db.DBDataFacade;

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
        if(gallery != null)
            this.gallery = gallery;
        else this.gallery = new LinkedList<>();
        comments = new LinkedList<>();
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
        DBDataFacade.modifyItem(this);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        return itemid != null ? itemid.equals(item.itemid) : item.itemid == null;
    }

    @Override
    public int hashCode() {
        return itemid != null ? itemid.hashCode() : 0;
    }
}
