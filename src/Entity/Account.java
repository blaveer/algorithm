package Entity;

public class Account {
    private int id;  //用户在数据库中的id
    private int age;  //年龄
    private double height;     //cm
    private double weight;      //kg
    private double impactRate;


    public Account(int id, int age, double height, double weight, double impactRate) {
        this.id = id;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.impactRate = impactRate;
    }

    public double getImpactRate() {
        return impactRate;
    }

    public void setImpactRate(double impactRate) {
        this.impactRate = impactRate;
    }


    public Account(int id) {
        this.id=id;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }


}
