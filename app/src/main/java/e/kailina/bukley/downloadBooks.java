package e.kailina.bukley;

public class downloadBooks {
    String Bookname, imageUrl;
    int price;
    public downloadBooks(){

    }
    public downloadBooks(String bookname,String imageUrl,int price){
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

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }
}
