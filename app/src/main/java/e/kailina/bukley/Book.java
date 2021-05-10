package e.kailina.bukley;

public class Book {
    String B_name, B_author,B_edition,B_price,B_category;
    String image_path;
    String S_name;
    String S_mail,S_phone;

    public  Book(){}
    public Book(String B_name, String B_author, String B_edition, String B_price, String B_category){
        this.B_name=B_name;
        this.B_author=B_author;
        this.B_edition=B_edition;
        this.B_price=B_price;
        this.B_category=B_category;
    }
    public void setUri(String imagePath){
        this.image_path=imagePath;
    }

    public String getB_name() {
        return B_name;
    }

    public void setB_name(String b_name) {
        B_name = b_name;
    }

    public String getB_author() {
        return B_author;
    }

    public void setB_author(String b_author) {
        B_author = b_author;
    }

    public String getB_edition() {
        return B_edition;
    }

    public void setB_edition(String b_edition) {
        B_edition = b_edition;
    }

    public String getB_price() {
        return B_price;
    }

    public void setB_price(String b_price) {
        B_price = b_price;
    }

    public String getB_category() {
        return B_category;
    }

    public void setB_category(String b_category) {
        B_category = b_category;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public String getS_name() {
        return S_name;
    }

    public void setS_name(String s_name) {
        S_name = s_name;
    }

    public String getS_mail() {
        return S_mail;
    }

    public void setS_mail(String s_mail) {
        S_mail = s_mail;
    }

    public String getS_phone() {
        return S_phone;
    }

    public void setS_phone(String s_phone) {
        S_phone = s_phone;
    }
}
