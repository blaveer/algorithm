package Entity;

public class Lable {
    private int id;
    private String name;

    public Lable(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Lable() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
