package object;

public class Account {
    String userid, username, email, address, phone, avatar;
    boolean notifications;

    public Account() {}

    public Account(String userid, String username, String email, String address, String phone, String avatar, boolean notifications) {
        this.userid = userid;
        this.username = username;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.avatar = avatar;
        this.notifications = notifications;
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

    public boolean isNotifications() {
        return notifications;
    }

    public void setNotifications(boolean notifications) {
        this.notifications = notifications;
    }
}
