package Entity;

public class Food {
    private int id;
    private int group;
    private String name;
    private int heat;
    private int quantity;

    public int getHeat() {
        return heat;
    }

    public void setHeat(int heat) {
        this.heat = heat;
    }



    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
//其他信息似乎暂时都用不到,主要是能通过id能找到相应的食品和确定其标签

    public Food(int id, int group, String name,int heat,int quantity) {
        this.id = id;
        this.group = group;
        this.name = name;
        this.heat=heat;
        this.quantity=quantity;
    }

    public Food() {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
