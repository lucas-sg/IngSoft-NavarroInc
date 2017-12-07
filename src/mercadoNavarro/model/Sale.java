package mercadoNavarro.model;

import mercadoNavarro.enums.PaymentMethod;

public class Sale {
    private Item article;
    private int quantity;
    private Buyer buyer;
    private PaymentMethod method;
    private int id;
    
    public Sale(int quantity, Item article, Buyer buyer, PaymentMethod method) {
    	this.article = article;
    	this.buyer = buyer;
    	this.quantity = quantity;
    	this.method = method;
    }
    
    public Double getTotal() {
    	return this.article.getPrice() * this.quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Item getArticle() {
    	return this.article;
    }
    
    public void setArticle(Item article) {
    	this.article = article;
    }
    
    public Buyer getBuyer() {
    	return this.buyer;
    }
    
    public void setBuyer(Buyer buyer) {
    	this.buyer = buyer;
    }

    public String getMethod(){
        return method.toString();
    }

    public void setMethod(PaymentMethod method) {
        this.method = method;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
