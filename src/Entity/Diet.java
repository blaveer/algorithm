package Entity;

import java.util.Date;

public class Diet {
    private int id;
    private int group;
    private Date data;
    private int account_id;

    public Diet() {
    }

    public Diet(int id, int group, Date data, int account_id) {
        this.id = id;
        this.group = group;
        this.data = data;
        this.account_id = account_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }
}
