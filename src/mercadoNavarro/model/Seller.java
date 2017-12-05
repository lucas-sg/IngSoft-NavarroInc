package mercadoNavarro.model;

import mercadoNavarro.enums.DocumentType;
import mercadoNavarro.enums.PhoneType;

import java.util.LinkedList;
import java.util.List;

public class Seller extends User {
    private List<Item> itemList;
    private Integer stars;
    private String category;
    private String photo;


    public Seller(String name, String password, String surname, String eMail, String country, String province,
                  String city, String street, Integer number, String zipCode, String telephone, String docNumber,
                  PhoneType telephoneType, DocumentType docType) {
        super(name, password, surname, eMail, country, province, city, street, number, zipCode, telephone, docNumber,
                telephoneType, docType);
        itemList = new LinkedList<>();
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    public void addItem(Item item) {
        itemList.add(item);
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
    
    public void addItemForSale(Item item) {
    	itemList.add(item);
    }
    
    public void removeItemForSale(Item item) {
    	itemList.remove(item);
    }
}
