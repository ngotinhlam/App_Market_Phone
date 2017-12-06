package object;

/**
 * Created by Admin on 10/19/2017.
 */

public class Account {
    String userid, username, email, address, phone, avatar;

    public Account() {}

    public Account(String userid, String username, String email, String address, String phone, String avatar) {
        this.userid = userid;
        this.username = username;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.avatar = avatar;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
