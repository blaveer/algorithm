package Relative;

import Entity.Account;
import Entity.Lable;

public class UserLable {
    private Account account;
    private Lable lable;
    private double weight;//权重

    public UserLable(Account account, Lable lable, double weight) {
        this.account = account;
        this.lable = lable;
        this.weight = weight;
    }

    public UserLable(){ }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Lable getLable() {
        return lable;
    }

    public void setLable(Lable lable) {
        this.lable = lable;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
