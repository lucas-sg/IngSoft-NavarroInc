


public class User {
    private String name;
    private String password;
    private String surname;
    private String eMail;
    private String country;
    private String province;
    private String city;
    private String street;
    private Integer number;
    private Integer zipCode;
    private String telephone;
    private String docNumber;
    private String telphoneType;
    private String docType;

    public User(String name, String password, String surname, String eMail, String country, String province,
                String city, String street, Integer number, Integer zipCode, String telephone, String docNumber,
                String telphoneType, String docType) {
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
        this.telphoneType = telphoneType;
        this.docType = docType;
    }

    public String getTelphoneType() {
        return telphoneType;
    }

    public void setTelphoneType(String telphoneType) {
        this.telphoneType = telphoneType;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
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

    public Integer getZipCode() {
        return zipCode;
    }

    public void setZipCode(Integer zipCode) {
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
}