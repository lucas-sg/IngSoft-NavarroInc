package mercadoNavarro.db;

import mercadoNavarro.enums.DocumentType;
import mercadoNavarro.enums.Ordering;
import mercadoNavarro.enums.PaymentMethod;
import mercadoNavarro.enums.PhoneType;
import mercadoNavarro.model.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class DBDataFacade {

    private static DBPersistance db = DBPersistance.getInstance();

    public static boolean addAdmin(Admin admin) {

        boolean ret = false;
        if (db.connect()) {
            ret = db.insert("into admins(email, password) values('" + admin.getEmail() + "','" + admin.getPassword() + "')");
            db.disconnect();
        }
        return ret;
    }

    public static Admin getAdmin(String email) {

        Admin admin = null;
        if (db.connect()) {
            ResultSet result = db.query("select * from admins where email = '" + email + "'");
            try {
                if (result.next()) {
                    String mail = result.getString("email");
                    String password = result.getString("password");
                    admin = new Admin(mail, password);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            db.disconnect();
        }
        return admin;
    }

    public static boolean deleteAdmin(Admin admin) {

        boolean ret = false;
        if (db.connect()) {
            ret = db.delete("from admins where email = '" + admin.getEmail() + "'");
            db.disconnect();
        }
        return ret;
    }

    public static boolean addUser(User user) {

        boolean ret = false;
        if (db.connect()) {
            ret = db.insert("into users(document, first_name, lastname, document_type, country, province, city, street, street_number, zip_code, phone, " +
                    "password, email, phone_type, enabled) values(" + user.getDocNumber() + ",'" + user.getName() + "','" + user.getSurname() + "','" + user.getDocType().toLowerCase() +
                    "','" + user.getCountry() + "','" + user.getProvince() + "','" + user.getCity() + "','" + user.getStreet() + "'," + user.getNumber() + ",'" +
                    user.getZipCode() + "','" + user.getTelephone() + "','" + user.getPassword() + "','" + user.geteMail() + "','" + user.getTelephoneType().toLowerCase() + "'," +
                    user.isEnabled() + ")");
            db.disconnect();
        }
        return ret;
    }

    public static boolean addSeller(Seller seller) {

        boolean ret = false;
        ret = addUser(seller);

        if (db.connect()) {
            ResultSet result = db.query("select userid from users where email = '" + seller.geteMail() + "'");
            try {
                result.next();
                int id = result.getInt("userid");
                ret &= db.insert("into sellers(sellerid, category, stars, image) values ('" + id + "','" + seller.getCategory() + "'," + seller.getStars() + ",'" +
                        ImageManager.encodeImage(seller.getPhoto()) + "')");
                for (Item item : seller.getItemList()) {
                    addItem(item);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            db.disconnect();
        }
        return ret;
    }

    public static User getUser(String email) {

        User user = null;
        if (db.connect()) {
            ResultSet result = db.query("select * from users where email = '" + email + "'");
            try {
                if(result.next()) {
                    int id = result.getInt("userid");
                    String name = result.getString("first_name");
                    String password = result.getString("password");
                    String surname = result.getString("lastname");
                    String eMail = result.getString("email");
                    String country = result.getString("country");
                    String province = result.getString("province");
                    String city = result.getString("city");
                    String street = result.getString("street");
                    int number = result.getInt("street_number");
                    String zipCode = result.getString("zip_code");
                    String telephone = result.getString("phone");
                    String docNumber = result.getString("document");
                    String telephoneType = result.getString("phone_type");
                    String docType = result.getString("document_type");
                    boolean isEnabled = result.getBoolean("enabled");
                    ResultSet sellerData = db.query("select * from sellers where sellerid = " + id);
                    if (sellerData.next()) {
                        String category = sellerData.getString("category");
                        int stars = sellerData.getInt("stars");
                        String photo = sellerData.getString("image");
                        user = new Seller(name, password, surname, eMail, country, province, city, street, number, zipCode, telephone, docNumber,
                                PhoneType.valueOf(telephoneType.toUpperCase()), DocumentType.valueOf(docType.toUpperCase()));
                        ((Seller) user).setCategory(category);
                        ((Seller) user).setStars(stars);
                    } else
                        user = new Buyer(name, password, surname, eMail, country, province, city, street, number, zipCode, telephone, docNumber,
                                PhoneType.valueOf(telephoneType.toUpperCase()), DocumentType.valueOf(docType.toUpperCase()));
                    user.setEnabled(isEnabled);
                    user.setId(id);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            db.disconnect();
        }
        return user;
    }

    public static Seller getFullSeller(String email) {
    	Seller seller = (Seller) getUser(email);

    	if (db.connect()) {
    		ResultSet sellerData = db.query("select * from sellers where sellerid = " + seller.getId());
    		try {
    			if (sellerData.next()) {
    				String photo = sellerData.getString("image");
    				ResultSet articles = db.query("select articleid from articles where is_active = true and sellerid = " + seller.getId());
    				List<Item> items = new LinkedList<>();
    				while (articles.next()) {
    					items.add(getItem(articles.getInt("articleid"), seller));
    					seller.setItemList(items);
    				}
    				seller.setPhoto(ImageManager.createImage(photo));
    			}
    		} catch (SQLException e) {
    			e.printStackTrace();
    		}
    		db.disconnect();
    	}
    	return seller;
    }

    public static boolean modifyUser(User user) {

        boolean ret = false;
        if (db.connect()) {
            ResultSet result = db.query("select * from users where email = '" + user.geteMail() + "'");
            try {
                result.next();
                int id = result.getInt("userid");
                ret = db.update("users set first_name = '" + user.getName() + "', lastname = '" + user.getSurname() + "', document_type = '" + user.getDocType().toLowerCase() +
                        "', country = '" + user.getCountry() + "', province = '" + user.getProvince() + "', city = '" + user.getCity() + "', street = '" +
                        user.getStreet() + "', street_number = " + user.getNumber() + ", zip_code = '" + user.getZipCode() + "', phone = '" + user.getTelephone() +
                        "', phone_type = '" + user.getTelephoneType().toLowerCase() + "', enabled = " + user.isEnabled() + " where userid =" + id);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            db.disconnect();
        }
        return ret;
    }

    public static boolean modifySeller(Seller seller) {

        boolean ret = false;
        ret = modifyUser(seller);

        LinkedList<Item> aux = (LinkedList<Item>) seller.getItemList();

        if (db.connect()) {
            LinkedList<Item> toAdd = (LinkedList<Item>) aux.clone();
            ResultSet result = db.query("select userid from users where email = '" + seller.geteMail() + "'");
            try {
                result.next();
                int id = result.getInt("userid");
                ResultSet items = db.query("select * from articles where sellerid = " + id);
                ret &= db.update("sellers set category = '" + seller.getCategory() + "', stars = " + seller.getStars() + ", image = '" +
                        ImageManager.encodeImage(seller.getPhoto()) + "' where sellerid = " + id);
                while (items.next()) {
                    int itemId = items.getInt("articleid");
                    Item i = getItem(itemId);
                    if (i.getItemid() == null) {
                        boolean removed = false;
                        for (Item item : seller.getItemList()) {
                            if (i.getName() == item.getName()) {
                                toAdd.remove(i);
                                removed = true;
                                break;
                            }
                        }
                        if (!removed)
                            ret &= db.delete("from articles where item_name =" + i.getName());
                    } else if (seller.getItemList().contains(i))
                        toAdd.remove(i);
                    else
                        ret &= db.delete("from articles where articleid =" + itemId);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            db.disconnect();
            for (Item item : toAdd) {
                ret &= addItem(item);
            }
        }
        return ret;

    }

    public static boolean deleteUser(User user) {

        boolean ret = false;
        if (db.connect()) {
            ret = db.delete("from users where email = '" + user.geteMail() + "'");
            db.disconnect();
        }
        return ret;
    }

    public static boolean addItem(Item item) {

        boolean ret = false;
        int sellerid = 0;
        if (db.connect()) {
            ResultSet result = db.query("select userid from users where email = '" + item.getSeller().geteMail() + "'");
            try {
                if (result.next())
                    sellerid = result.getInt("userid");
            } catch (SQLException e) {
                e.printStackTrace();
            }

            ret = db.insert("into articles(item_name, description, stock, pickup, price, sellerid) values ('" + item.getName() + "','" + item.getDescription() +
                    "'," + item.getStock() + ",'" + item.getPickup() + "'," + item.getPrice() + "," + sellerid + ")");

            int id = 0;

            result = db.query("select currval('articles_seq')");
            try {
                if (result.next()) {
                    id = result.getInt(1);
                    item.setItemid(id);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            if (ret && id != 0) {
                ret &= addComments(item.getComments(), id);
                ret &= addPictures(item.getGallery(), id);
            }
            db.disconnect();
        }
        return ret;
    }

    public static Item getItem(int itemId) {
        return getItem(itemId, null);
    }

    private static Item getItem(int itemId, Seller seller) {

        Item item = null;
        if (db.connect()) {
            ResultSet result = db.query("select * from articles where articleid = " + itemId);
            try {
                if (result.next()) {
                    item = createItem(seller, result);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            db.disconnect();
        }
        return item;
    }

    public static Item getPartialItem(int itemId) {
        Item item = null;
        if (db.connect()) {
            ResultSet result = db.query("select * from articles, users where sellerid = userid and articleid = " + itemId);
            try {
                if (result.next()) {
                    item = createPartialItem(result);
                    String seller = result.getString("email");
                    item.setSeller((Seller)getUser(seller));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            db.disconnect();
        }
        return item;
    }

    private static Item createItem(Seller seller, ResultSet result) throws SQLException {

        Item item = createPartialItem(result);
        int sellerId = result.getInt("sellerid");
        if (seller == null) {
            String sellerEmail = null;
            ResultSet sellerData = db.query("select email from users where userid = " + sellerId);
            if (sellerData.next())
                sellerEmail = sellerData.getString("email");
            seller = (Seller) getUser(sellerEmail);
        }
        item.setSeller(seller);
        item.setGallery(getPictures(item.getItemid()));
        item.setComments(getComments(item.getItemid()));
        return item;
    }

    private static Item createPartialItem(ResultSet result) throws SQLException {

        Item item = null;
        int itemId = result.getInt("articleid");
        String name = result.getString("item_name");
        String description = result.getString("description");
        String pickup = result.getString("pickup");
        int stock = result.getInt("stock");
        double price = result.getDouble("price");
        ResultSet picture = db.query("select picture from pictures where articleid = " + itemId);
        LinkedList<String> pictures = new LinkedList<>();
        if (picture.next())
            pictures.add(ImageManager.createImage(picture.getString("picture")));
        item = new Item(null, name, description, stock, pickup, price, pictures);
        item.setItemid(itemId);
        return item;
    }

    public static boolean modifyItem(Item item) {

        boolean ret = false;
        if (db.connect()) {
            ResultSet result = db.query("select * from articles where articleid = " + item.getItemid());
            try {
                result.next();
                ret = db.update("articles set item_name = '" + item.getName() + "', description = '" + item.getDescription() + "', stock = " + item.getStock() +
                        ", pickup = '" + item.getPickup() + "', price = " + item.getPrice() + " where articleid =" + item.getItemid());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            db.disconnect();
        }
        return ret;
    }

    public static boolean deleteItem(Item item) {

        boolean ret = false;
        if (db.connect()) {
            ResultSet result = db.query("select * from sales where articleid = " + item.getItemid());
            try {
                if(result.next()) {
                    ret = db.update("articles set is_active = false where articleid = " + item.getItemid());
                }
                else
                    ret = db.delete("from articles where articleid = " + item.getItemid());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            db.disconnect();
        }
        return ret;
    }

    private static boolean addComments(List<String> comments, int itemId) {

        boolean ret = true;
        if (comments != null) {
            if (db.connect()) {
                for (String comment : comments) {
                    ret &= db.insert("into comments(comment, articleid) values ('" + comment + "'," + itemId + ")");
                }
                db.disconnect();
            }
            else ret = false;
        } else ret = true;
        return ret;
    }

    public static boolean addComment(String comment, int itemId) {
        List<String> list = new LinkedList<>();
        list.add(comment);
        return addComments(list, itemId);
    }

    private static List<String> getComments(int articleId) {

        List<String> comments = new LinkedList<>();
        if (db.connect()) {
            ResultSet result = db.query("select comment from comments where articleid = " + articleId);
            try {
                while (result.next())
                    comments.add(result.getString("comment"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            db.disconnect();
        }
        return comments;
    }

    public static boolean modifyComments(LinkedList<String> comments, int articleId) {

        boolean ret = true;
        if (comments != null) {
            LinkedList<String> toAdd = (LinkedList<String>) comments.clone();
            if (db.connect()) {
                ResultSet result = db.query("select * from comments where articleid = " + articleId);
                try {
                    while (result.next()) {
                        String comment = result.getString("comment");
                        int id = result.getInt("commentid");
                        if (comments.contains(comment)) {
                            toAdd.remove(comment);
                        } else {
                            ret &= db.delete("from comments where commentid =" + id);
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                db.disconnect();
            } else ret = false;
            ret &= addComments(toAdd, articleId);
        } else ret = true;
        return ret;
    }

    private static boolean addPictures(List<String> photos, int itemId) {

        boolean ret = true;
        if (photos != null) {
            if (db.connect()) {
                for (String photo : photos) {
                    ret &= db.insert("into pictures(picture, articleid) values ('" + ImageManager.encodeImage(photo) + "'," + itemId + ")");
                }
                db.disconnect();
            } else ret = false;
        } else ret = true;
        return ret;
    }

    private static List<String> getPictures(int articleId) {

        List<String> pictures = new LinkedList<>();
        if (db.connect()) {
            ResultSet result = db.query("select picture from pictures where articleid = " + articleId);
            try {
                while (result.next())
                    pictures.add(ImageManager.createImage(result.getString("picture")));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            db.disconnect();
        }
        return pictures;
    }

    public static boolean modifyPictures(LinkedList<String> pictures, int articleId) {

        boolean ret = true;
        if (pictures != null) {
            LinkedList<String> toAdd = (LinkedList<String>) pictures.clone();
            if (db.connect()) {
                ResultSet result = db.query("select * from pictures where articleid = " + articleId);
                try {
                    while (result.next()) {
                        String image = ImageManager.createImage(result.getString("picture"));
                        int id = result.getInt("pictureid");
                        if (pictures.contains(image)) {
                            toAdd.remove(image);
                        } else {
                            ret &= db.delete("from pictures where pictureid =" + id);
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                db.disconnect();
            } else ret = false;
            ret &= addPictures(toAdd, articleId);
        } else ret = true;
        return ret;
    }

    public static List<Item> getSearch(String search, Ordering orderBy) {

        List<Item> items = new LinkedList<>();
        String sorting = "";

        switch (orderBy) {
            case LOWEST_PRICE:
                sorting = "order by price asc";
                break;
            case STARS:
                sorting = "order by stars desc";
                break;
            case PICKUP:
                sorting = "order by pickup";
                break;
        }

        if (db.connect()) {
            ResultSet result = db.query("select articleid, sellerid, item_name, description, stock, pickup, price, is_active ,stars from articles natural join sellers where stock <> 0" +
                    " and is_active = true and lower(item_name) like lower('%" + search + "%') " + sorting);
            try {
                while (result.next()) {
                    items.add(createPartialItem(result));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        db.disconnect();
        return items;
    }

    public static List<User> getUsers() {

        List<User> users = new LinkedList<>();
        User user = null;
        if (db.connect()) {
            ResultSet result = db.query("select * from users");
            try {
                while(result.next()) {
                    int id = result.getInt("userid");
                    String name = result.getString("first_name");
                    String password = result.getString("password");
                    String surname = result.getString("lastname");
                    String eMail = result.getString("email");
                    String country = result.getString("country");
                    String province = result.getString("province");
                    String city = result.getString("city");
                    String street = result.getString("street");
                    int number = result.getInt("street_number");
                    String zipCode = result.getString("zip_code");
                    String telephone = result.getString("phone");
                    String docNumber = result.getString("document");
                    String telephoneType = result.getString("phone_type");
                    String docType = result.getString("document_type");
                    boolean isEnabled = result.getBoolean("enabled");
                    user = new Buyer(name, password, surname, eMail, country, province, city, street, number, zipCode, telephone, docNumber,
                            PhoneType.valueOf(telephoneType.toUpperCase()), DocumentType.valueOf(docType.toUpperCase()));
                    user.setEnabled(isEnabled);
                    user.setId(id);
                    users.add(user);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            db.disconnect();
        }
        return users;
    }

    public static boolean addSale(Sale sale){
        boolean ret = false;
        if (db.connect()) {
            ret = db.insert("into sales(articleid, buyerid, quantity, total, method) values(" + sale.getArticle().getItemid() + ", " + sale.getBuyer().getId() + ", " +
            sale.getQuantity() + ", " + sale.getTotal() + ", '" +sale.getMethod().toLowerCase() + "')");
            db.disconnect();
        }
        return ret;
    }

    private static List<Sale> getSales(int id, boolean isBuyer) {

        List<Sale> sales = new LinkedList<>();
        String searchId = isBuyer? "buyerid" : "sellerid";
        if (db.connect()) {
            ResultSet result = db.query("select * from sales natural join articles join users on userid = " + searchId +", users as seller where seller.userid = sellerid and " +
                    searchId + " = " + id);
            try {
                while (result.next()) {
                    String method= result.getString("method");
                    int itemId = result.getInt("articleid");
                    int saleId = result.getInt("saleid");
                    String buyer = result.getString("email");
                    int sellerId = result.getInt("sellerid");
                    String seller = result.getString(43);
                    int quantity = result.getInt("quantity");
                    if(!db.isConnected())
                        db.connect();
                    Item article = createPartialItem(result);
                    article.setSeller((Seller)getUser(seller));
                    Sale sale = new Sale(quantity,article, (Buyer)getUser(buyer) , PaymentMethod.valueOf(method.toUpperCase()));
                    sale.setId(saleId);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            db.disconnect();
        }
        return sales;

    }

    public static List<Sale> getPurchases(int buyerId) {
        return getSales(buyerId, true);
    }

    public static List<Sale> getSales(int sellerId) {
        return getSales(sellerId, false);
    }

}
