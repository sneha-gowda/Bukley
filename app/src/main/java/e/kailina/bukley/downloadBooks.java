package e.kailina.bukley;

public class downloadBooks {
    String Bookname, imageUrl;
    String price;
    public downloadBooks(){

    }
    public downloadBooks(String bookname,String imageUrl,String price){
        this.Bookname=bookname;
        this.imageUrl=imageUrl;
        this.price=price;
    }
    public String getBookname() {
        return Bookname;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setBookname(String bookname) {
        Bookname = bookname;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setPrice(String price) { this.price = price;
    }

    public String getPrice() { return price;
    }
}
