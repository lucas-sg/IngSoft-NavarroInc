package mercadoNavarro.model;

import mercadoNavarro.enums.DocumentType;
import mercadoNavarro.enums.PhoneType;

public abstract class User {
    private String name;
    private String password;
    private String surname;
    private String eMail;
    private String country;
    private String province;
    private String city;
    private String street;
    private Integer number;
    private String zipCode;
    private String telephone;
    private String docNumber;
    private PhoneType telephoneType;
    private DocumentType docType;
    private boolean isEnabled;
    private Integer id;

    public User(String name, String password, String surname, String eMail, String country, String province,
                String city, String street, Integer number, String zipCode, String telephone, String docNumber,
                PhoneType telephoneType, DocumentType docType) {
        this.name = name;
        this.password = password;
        this.surname = surname;
        this.eMail = eMail;
        this.country = country;
        this.province = province;
        this.city = city;
        this.street = street;
        this.number = number;
        this.zipCode = zipCode;
        this.telephone = telephone;
        this.docNumber = docNumber;
        this.telephoneType = telephoneType;
        this.docType = docType;
        this.isEnabled = true;
    }

    public String getTelephoneType() {
        return telephoneType.toString();
    }

    public void setTelephoneType(PhoneType telephoneType) {
        this.telephoneType = telephoneType;
    }

    public String getDocType() {
        return docType.toString();
    }

    public void setDocType(DocumentType docType) {
        this.docType = docType;
    }

    public String getPassword() {
        return password;
    }

    public String geteMail() {
        return eMail;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getDocNumber() {

        return docNumber;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return eMail != null ? eMail.equals(user.eMail) : user.eMail == null;
    }

    @Override
    public int hashCode() {
        return eMail != null ? eMail.hashCode() : 0;
    }
}
