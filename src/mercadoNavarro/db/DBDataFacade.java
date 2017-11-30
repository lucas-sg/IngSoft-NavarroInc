package mercadoNavarro.db;

import mercadoNavarro.enums.DocumentType;
import mercadoNavarro.enums.Ordering;
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
                    "password, email, phone_type, enabled) values(" + user.getDocNumber() + ",'" + user.getName() + "','" + user.getSurname() + "','" + user.getDocType() +
                    "','" + user.getCountry() + "','" + user.getProvince() + "','" + user.getCity() + "','" + user.getStreet() + "'," + user.getNumber() + ",'" +
                    user.getZipCode() + "','" + user.getTelephone() + "','" + user.getPassword() + "','" + user.geteMail() + "','" + user.getTelephoneType() + "'," +
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
                result.next();
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
                String telphoneType = result.getString("phone_type");
                String docType = result.getString("document_type");
                boolean isEnabled = result.getBoolean("enabled");
                ResultSet sellerData = db.query("select * from sellers where sellerid = " + id);
                if (sellerData.next()) {
                    String category = sellerData.getString("category");
                    int stars = sellerData.getInt("stars");
                    String photo = sellerData.getString("image");
                    user = new Seller(name, password, surname, eMail, country, province, city, street, number, zipCode, telephone, docNumber,
                            PhoneType.valueOf(telphoneType.toUpperCase()), DocumentType.valueOf(docType.toUpperCase()));
                    ResultSet articles = db.query("select articleid from articles where sellerid = " + id);
                    List<Item> items = new LinkedList<>();
                    while (articles.next()) {
                        items.add(getItem(articles.getInt("articleid"), (Seller)user));
                        ((Seller) user).setItemList(items);
                    }
                    ((Seller) user).setCategory(category);
                    ((Seller) user).setPhoto(ImageManager.createImage(photo));
                    ((Seller) user).setStars(stars);
                } else
                    user = new Buyer(name, password, surname, eMail, country, province, city, street, number, zipCode, telephone, docNumber,
                            PhoneType.valueOf(telphoneType.toUpperCase()), DocumentType.valueOf(docType.toUpperCase()));
                user.setEnabled(isEnabled);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            db.disconnect();
        }
        return user;
    }

    public static boolean modifyUser(User user) {

        boolean ret = false;
        if (db.connect()) {
            ResultSet result = db.query("select * from users where email = '" + user.geteMail() + "'");
            try {
                result.next();
                int id = result.getInt("userid");
                ret = db.update("users set first_name = '" + user.getName() + "', lastname = '" + user.getSurname() + "', document_type = '" + user.getDocType() +
                        "', country = '" + user.getCountry() + "', province = '" + user.getProvince() + "', city = '" + user.getCity() + "', street = '" +
                        user.getStreet() + "', street_number = " + user.getNumber() + ", zip_code = '" + user.getZipCode() + "', phone = '" + user.getTelephone() +
                        "', phone_type = '" + user.getTelephoneType() + "', enabled = " + user.isEnabled() +" where userid =" + id);
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
                while(items.next()) {
                    int itemId = items.getInt("articleid");
                    Item i = getItem(itemId);
                    LinkedList<Item> list = (LinkedList<Item>) seller.getItemList();
                    list.contains(i);
                    if(seller.getItemList().contains(i))
                        toAdd.remove(i);
                    else
                        ret &= db.delete("from articles where articleid =" + itemId);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            db.disconnect();
            for (Item item: toAdd) {
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

    private static Item createItem(Seller seller, ResultSet result) throws SQLException{

        Item item = null;
        int itemId = result.getInt("articleid");
        int sellerId = result.getInt("sellerid");
        if(seller == null) {
            String sellerEmail = null;
            ResultSet sellerData = db.query("select email from users where userid = " + sellerId);
            if (sellerData.next())
                sellerEmail = sellerData.getString("email");
            seller = (Seller) getUser(sellerEmail);
        }
        String name = result.getString("item_name");
        String description = result.getString("description");
        String pickup = result.getString("pickup");
        int stock = result.getInt("stock");
        double price = result.getDouble("price");
        item = new Item(seller, name, description, stock, pickup, price, getPictures(itemId));
        item.setComments(getComments(itemId));
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
            ret &= modifyComments((LinkedList<String>) item.getComments(), item.getItemid());
            ret &= modifyPictures((LinkedList<String>) item.getGallery(), item.getItemid());
        }
        return ret;
    }

    public static boolean deleteItem(Item item) {

        boolean ret = false;
        if (db.connect()) {
            ret = db.delete("from articles where articleid = " + item.getItemid());
            db.disconnect();
        }
        return ret;
    }

    private static boolean addComments(List<String> comments, int itemId) {

        boolean ret = false;
        if (comments != null) {
            if (db.connect()) {
                for (String comment : comments) {
                    ret &= db.insert("into comments(comment, articleid) values ('" + comment + "'," + itemId + ")");
                }
                db.disconnect();
            }
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

    private static boolean modifyComments(LinkedList<String> comments, int articleId) {

        boolean ret = false;
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
                        }
                        else {
                            ret &= db.delete("from comments where commentid =" + id);
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                db.disconnect();
            }
            ret &= addComments(toAdd, articleId);
        } else ret = true;
        return ret;
    }

    private static boolean addPictures(List<String> photos, int itemId) {

        boolean ret = false;
        if (photos != null) {
            if (db.connect()) {
                for (String photo : photos) {
                    ret &= db.insert("into pictures(picture, articleid) values ('" + ImageManager.encodeImage(photo) + "'," + itemId + ")");
                }
                db.disconnect();
            }
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

    private static boolean modifyPictures(LinkedList<String> pictures, int articleId) {

        boolean ret = false;
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
                        }
                        else {
                            ret &= db.delete("from pictures where pictureid =" + id);
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                db.disconnect();
            }
            ret &= addPictures(toAdd, articleId);
        } else ret = true;
        return ret;
    }

    public static List<Item> getSearch(String search, Ordering orderBy, String pickup) {

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
                sorting = "and pickup = '" + pickup + "'";
                break;
        }

        if (db.connect()) {
            ResultSet result = db.query("select articleid, sellerid, item_name, description, stock, pickup, price, stars from articles natural join sellers where item_name " +
                    "like '%" + search + "%' " + sorting);
            try {
                while(result.next()) {
                    if(!db.isConnected())
                        db.connect();
                    items.add(createItem(null, result));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        db.disconnect();
        return items;
    }

}
