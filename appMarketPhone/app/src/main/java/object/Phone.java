package object;


import java.io.Serializable;

public class Phone implements Serializable{
    String id, userid, title, price, description, urlimage1, urlimage2, urlimage3, date;

    public Phone() {
    }

    public Phone(String id, String userid, String title, String price, String description, String urlimage1, String urlimage2, String urlimage3, String date) {
        this.id = id;
        this.userid = userid;
        this.title = title;
        this.price = price;
        this.description = description;
        this.urlimage1 = urlimage1;
        this.urlimage2 = urlimage2;
        this.urlimage3 = urlimage3;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrlimage1() {
        return urlimage1;
    }

    public void setUrlimage1(String urlimage1) {
        this.urlimage1 = urlimage1;
    }

    public String getUrlimage2() {
        return urlimage2;
    }

    public void setUrlimage2(String urlimage2) {
        this.urlimage2 = urlimage2;
    }

    public String getUrlimage3() {
        return urlimage3;
    }

    public void setUrlimage3(String urlimage3) {
        this.urlimage3 = urlimage3;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
