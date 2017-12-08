package mercadoNavarro.model;

import mercadoNavarro.db.DBDataFacade;
import mercadoNavarro.enums.DocumentType;
import mercadoNavarro.enums.PaymentMethod;
import mercadoNavarro.enums.PhoneType;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Buyer extends User {

	private Map<Integer, Integer> cart;

    public Buyer(String name, String password, String surname, String eMail, String country, String province, String
            city, String street, Integer number, String zipCode, String telephone, String docNumber,
                 PhoneType telephoneType, DocumentType docType) {
        super(name, password, surname, eMail, country, province, city, street, number, zipCode, telephone, docNumber,
                telephoneType, docType);
        cart = new HashMap<>();
    }

    public void addItemToCart(int itemid, int count) {
    	cart.put(itemid, count);
    }
    
    public void removeItemFromCart(int itemid) {
    	cart.remove(itemid);
    }
    
    public int confirmBuy(PaymentMethod method) {
    	LinkedList<Sale> sales = new LinkedList<>();
    	
    	HashMap<Item, Integer> itemcart = new HashMap<>();
    	for (Map.Entry<Integer, Integer> entry : getCart()) 
			itemcart.put(DBDataFacade.getPartialItem(entry.getKey()), entry.getValue());
    	
    	//check quantity
    	for (Map.Entry<Item, Integer> entry : itemcart.entrySet()) {
    		if (entry.getKey().getStock()- entry.getValue() < 0)
    			return 1;
    		
    		entry.getKey().setStock(entry.getKey().getStock()- entry.getValue());
    		sales.add(new Sale(entry.getValue(), entry.getKey(), this, method));
    	}
    	
    	for (Sale sale : sales) {
    		//add sale
    		if (DBDataFacade.addSale(sale)) {
    			//modify item
    			DBDataFacade.modifyItem(sale.getArticle());
    			cart.remove(sale.getArticle().getItemid());
    		}
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
    	
    	for (Map.Entry<Integer, Integer> entry : cart.entrySet()) {
    		Item aux = DBDataFacade.getPartialItem(entry.getKey());
    		total += aux.getPrice() * entry.getValue();
    	}
    	
    	return total;
    }
    
    public Set<Map.Entry<Integer, Integer>> getCart() {
    	return cart.entrySet();
    }
    
    public boolean cartContainsItem(Item item) {
    	return cart.containsKey(item);
    }
}
