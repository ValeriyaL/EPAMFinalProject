package by.liudchyk.audiotracks.entity;

/**
 * Created by Admin on 24.12.2016.
 */
public class User extends Entity {
    private int id;
    private String nickname;
    private String password;
    private int status;
    private double money;
    private int bonus;
    private String cardNumber;
    private String email;


    public User(int id, String nickname, String password, int status, double money, int bonus, String cardNumber, String email) {
        this.id = id;
        this.nickname = nickname;
        this.password = password;
        this.status = status;
        this.money = money;
        this.bonus = bonus;
        this.cardNumber = cardNumber;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPassword() {
        return password;
    }

    public int getStatus() {
        return status;
    }

    public double getMoney() {
        return money;
    }

    public int getBonus() {
        return bonus;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCardNumber(String cardNumber) {

        this.cardNumber = cardNumber;
    }

    public void addMoney(double money){
        this.money += money;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                ", status=" + status +
                ", money=" + money +
                ", bonus=" + bonus +
                ", email=" + email +
                ", cardNumber='" + cardNumber + '\'' +
                '}';
    }
}
