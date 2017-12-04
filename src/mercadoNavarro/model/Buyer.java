package mercadoNavarro.model;

import mercadoNavarro.enums.DocumentType;
import mercadoNavarro.enums.PhoneType;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Buyer extends User {

	private Map<Item, Integer> cart;

    public Buyer(String name, String password, String surname, String eMail, String country, String province, String
            city, String street, Integer number, String zipCode, String telephone, String docNumber,
                 PhoneType telephoneType, DocumentType docType) {
        super(name, password, surname, eMail, country, province, city, street, number, zipCode, telephone, docNumber,
                telephoneType, docType);
        cart = new HashMap<>();
    }

    public void addItemToCart(Item item, int count) {
    	cart.put(item, count);
    }
    
    public void removeItemFromCart(Item item) {
    	cart.remove(item);
    }
    
    public int confirmBuy() {
    	List<Sale> sales = new LinkedList<>();
    	for (Map.Entry<Item, Integer> entry : cart.entrySet()) {
    		if (entry.getKey().getStock()-entry.getValue() < 0)
    			return 1;
    		sales.add(new Sale(entry.getKey().getPrice() * entry.getValue(), entry.getKey(), this));
    		entry.getKey().setStock(entry.getKey().getStock() - entry.getValue());
    	}
    	
    	return 0;
    }
    
    public void postComment(Item item, String comment) {
    	List<String> comments = item.getComments();
    	comments.add(this.getName() + " " + this.getSurname() + " - " + comment);
    	item.setComments(comments);
    }
    
    public double getCartTotalPrice() {
    	double total = 0;
    	
    	for (Map.Entry<Item, Integer> entry : cart.entrySet()) {
    		total += entry.getKey().getPrice() * entry.getValue();
    	}
    	
    	return total;
    }
    
    public Set<Map.Entry<Item, Integer>> getCart() {
    	return cart.entrySet();
    }
    
    public boolean cartContainsItem(Item item) {
    	return cart.containsKey(item);
    }
}
