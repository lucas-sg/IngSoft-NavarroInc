package mercadoNavarro;

public class Buyer extends User {

    public Buyer(String name, String password, String surname, String eMail, String country, String province, String
            city, String street, Integer number, Integer zipCode, String telephone, String docNumber,
                 String telphoneType, String docType) {
        super(name, password, surname, eMail, country, province, city, street, number, zipCode, telephone, docNumber,
                telphoneType, docType);
    }
}
