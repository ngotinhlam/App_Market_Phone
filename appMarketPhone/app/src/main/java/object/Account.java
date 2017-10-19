package object;

/**
 * Created by Admin on 10/19/2017.
 */

public class Account {
    String uid, email, address, phone, avatar;

    public Account(String uid, String email, String address, String phone, String avatar) {
        this.uid = uid;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.avatar = avatar;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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
