package e.kailina.bukley;

public class downloadBooks {
    String Bookname, imageUrl;
    String price;
    String author;
    String edition;
    String  S_name;
    String S_phone;
    String S_mail;
    public downloadBooks(){ }
    public downloadBooks(String bookname,String imageUrl,String price, String author, String edition,String S_name,String S_phone,String S_mail){
        this.Bookname=bookname;
        this.imageUrl=imageUrl;
        this.price=price;
        this.author=author;
        this.edition=edition;
        this.S_name=S_name;
        this.S_phone=S_phone;
        this.S_mail=S_mail;
    }
    public void setBookname(String bookname) {
        this.Bookname = bookname;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setPrice(String price) { this.price = price; }

    public void setAuthor(String author) { this.author = author; }

    public void setEdition(String edition) { this.edition = edition; }

    public String getBookname() {
        return Bookname;
    }

    public String getAuthor() { return author; }

    public String getEdition() { return edition; }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getPrice() { return price;
    }

    public String getS_name() {
        return S_name;
    }

    public void setS_name(String s_name) {
        S_name = s_name;
    }

    public String getS_phone() {
        return S_phone;
    }

    public void setS_phone(String s_phone) {
        S_phone = s_phone;
    }

    public String getS_mail() {
        return S_mail;
    }

    public void setS_mail(String s_mail) {
        S_mail = s_mail;
    }
}
