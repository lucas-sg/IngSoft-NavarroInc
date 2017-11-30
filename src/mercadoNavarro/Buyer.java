package mercadoNavarro;

import mercadoNavarro.enums.DocumentType;
import mercadoNavarro.enums.PhoneType;

public class Buyer extends User {

    public Buyer(String name, String password, String surname, String eMail, String country, String province, String
            city, String street, Integer number, Integer zipCode, String telephone, String docNumber,
                 PhoneType telphoneType, DocumentType docType) {
        super(name, password, surname, eMail, country, province, city, street, number, zipCode, telephone, docNumber,
                telphoneType, docType);
    }
}
