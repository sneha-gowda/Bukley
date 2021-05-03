package e.kailina.bukley;

public class Book {
    String B_name, B_author,B_edition,B_price,B_category;
    String image_path;
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
        return this.B_name;
    }

    public String getB_author() {
        return this.B_author;
    }

    public String getB_edition() {
        return this.B_edition;
    }

    public String getB_price() {
        return this.B_price;
    }

    public String getB_category() {
        return this.B_category;
    }

    public String getImage_path() {
        return this.image_path;
    }
}
