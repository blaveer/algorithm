package Entity;

public class FoodRecommend {
    private int id;
    private Food food;       //id 就是FoodBank中的id,可能我是觉得会用的到把
    private int recommendationRanking;     //1是最靠前，这个类就是用来存储要推荐的食品的。

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public int getRecommendationRanking() {
        return recommendationRanking;
    }

    public void setRecommendationRanking(int recommendationRanking) {
        this.recommendationRanking = recommendationRanking;
    }

}
