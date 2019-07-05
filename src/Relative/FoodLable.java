package Relative;

import Entity.Food;
import Entity.Lable;

public class FoodLable {
    private Food food;
    private Lable lable;

    public FoodLable(){ }

    public FoodLable(Food food, Lable lable) {
        this.food = food;
        this.lable = lable;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public Lable getLable() {
        return lable;
    }

    public void setLable(Lable lable) {
        this.lable = lable;
    }
}
