package mercadoNavarro;

import mercadoNavarro.enums.DocumentType;
import mercadoNavarro.enums.PhoneType;

import java.util.List;

public class Seller extends User {
    private List<Item> itemList;
    private Integer stars;
    private String category;
    private String photo;


    public Seller(String name, String password, String surname, String eMail, String country, String province,
                  String city, String street, Integer number, Integer zipCode, String telephone, String docNumber,
                  PhoneType telphoneType, DocumentType docType) {
        super(name, password, surname, eMail, country, province, city, street, number, zipCode, telephone, docNumber,
                telphoneType, docType);
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
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
}
