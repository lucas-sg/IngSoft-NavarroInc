package mercadoNavarro.model;

import mercadoNavarro.enums.DocumentType;
import mercadoNavarro.enums.PhoneType;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Buyer extends User {

	private Map<Item, Integer> cart;

    public Buyer(String name, String password, String surname, String eMail, String country, String province, String
            city, String street, Integer number, Integer zipCode, String telephone, String docNumber,
                 PhoneType telephoneType, DocumentType docType) {
        super(name, password, surname, eMail, country, province, city, street, number, zipCode, telephone, docNumber,
                telephoneType, docType);
    }

    public void addItemToCart(Item item) {
    	if (!cart.containsKey(item))
    		cart.put(item, 1);
    	else
    		cart.put(item, cart.get(item)+1);
    	item.setStock(item.getStock()-1);
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
}
