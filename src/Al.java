import Entity.Account;
import Entity.Food;
import Jama.Matrix;
import Jama.SingularValueDecomposition;

import java.util.List;
import java.util.Random;

import static Tools.StaticMethod.DietaryAdvice;


public class Al {
    public static void main(String[] arg){
        List<Food> foods=DietaryAdvice(new Account(120),2);
        System.out.println("推荐食物及其热量信息如下");
        int sum_heat=0;
        for(int i=0;i<foods.size();i++){
            System.out.println("食物： "+foods.get(i).getName());
            System.out.println("热量(千卡/100g) "+foods.get(i).getHeat());
            System.out.println("推荐用量:   "+foods.get(i).getQuantity()+"g\n");
            sum_heat+=foods.get(i).getHeat();
        }
        System.out.print("总数量是");
        System.out.print(sum_heat);
        System.out.println("千卡");


    }
}
