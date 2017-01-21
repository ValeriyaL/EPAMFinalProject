package by.liudchyk.audiotracks.entity;

/**
 * Class {@code User} is used to store User entity.
 *
 * @author Liudchyk Valeriya
 * @version 1.0
 * @see Entity
 */
public class User extends Entity {
    /**
     * The user's id.
     */
    private int id;
    /**
     * The user's nickname.
     */
    private String nickname;
    /**
     * The user's password.
     */
    private String password;
    /**
     * The user's status (0 - admin, 1- client).
     */
    private int status;
    /**
     * The user's money.
     */
    private double money;
    /**
     * The user's bonus in percents.
     */
    private int bonus;
    /**
     * The users's card number.
     */
    private String cardNumber;
    /**
     * The user's email.
     */
    private String email;

    /**
     * Creates new object of User entity
     *
     * @param id         is user's id
     * @param nickname   is users's nickname
     * @param password   is user's password
     * @param status     is user's status
     * @param money      is user's money
     * @param bonus      is user's bonus
     * @param cardNumber is user's cardNumber
     * @param email      is iser's email
     */
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

    /**
     * Gets user's id
     *
     * @return user's id
     */
    public int getId() {
        return id;
    }

    /**
     * Gets user's nickname
     *
     * @return user's nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Gets user's password
     *
     * @return user's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Gets user's status
     *
     * @return user's status
     */
    public int getStatus() {
        return status;
    }

    /**
     * Gets user's money
     *
     * @return user's money
     */
    public double getMoney() {
        return money;
    }

    /**
     * Gets user's money
     *
     * @return user's money
     */
    public int getBonus() {
        return bonus;
    }

    /**
     * Gets user's card number
     *
     * @return user's card number
     */
    public String getCardNumber() {
        return cardNumber;
    }

    /**
     * Gets user's email
     *
     * @return user's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets user's email
     *
     * @param email is user's email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Sets user's password
     *
     * @param password is user's password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Sets user's card number
     *
     * @param cardNumber is user's card number
     */
    public void setCardNumber(String cardNumber) {

        this.cardNumber = cardNumber;
    }

    /**
     * Sets user's money
     *
     * @param money is user's money
     */
    public void setMoney(double money) {
        this.money = money;
    }

    /**
     * Sets user's bonus
     *
     * @param bonus is user's bonus
     */
    public void setBonus(int bonus) {
        this.bonus = bonus;
    }

    /**
     * Sets user's nickname
     *
     * @param nickname is user's nickname
     */
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
