package mercadoNavarro;

import java.util.List;

public class Seller extends User {
    private List<Item> itemList;
    private Integer stars;
    private String category;
    //agregar foto y id


    public Seller(String name, String password, String surname, String eMail, String country, String province,
                  String city, String street, Integer number, Integer zipCode, String telephone, String docNumber,
                  String telphoneType, String docType) {
        super(name, password, surname, eMail, country, province, city, street, number, zipCode, telephone, docNumber,
                telphoneType, docType);
    }



}
