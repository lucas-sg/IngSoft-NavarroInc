package mercadoNavarro.model;

public class Sale {
    private Double total;
    private Item article;
    private Buyer buyer;
    
    public Sale(Double total, Item article, Buyer buyer) {
    	this.total = total;
    	this.article = article;
    	this.buyer = buyer;
    }
    
    public Double getTotal() {
    	return this.total;
    }
    
    public void setTotal(double total) {
    	this.total = total;
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

}
