package Tools;

import Entity.Account;
import Entity.Food;
import com.csvreader.CsvReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.sql.ResultSet;
import java.util.*;

public class StaticMethod {

    public static int SizeOfLabel=10;       /**这个值随手写的，到时候根据真实的标签数量来改,就是标签的总数量*/
    //TODO 上面这个值要改，最终要跟标签总数一致。
    public static double[] AccountLabel;
    public static List<Food> foodMain=new ArrayList<>();
    public static List<Food> DietaryAdvice(Account account,int group){   //group是选择早中晚餐的，这在定为123分别标识
        init_foodMain();
        boolean[] sign=new boolean[4];//四个布尔值，分别代表四个筛选条件是否可用。
        double[] sigeIm=new double[4];
        /**
        *布尔值的顺序是
        * 过往饮食记录，  .........0
        * 自己的标签，      .........1
        * 相似用户的饮食记录..........2
        * 尝试新推荐.................3
        **/

        List<Food> foods=new ArrayList<>();

        Account similar_account;     //最相近的用户
        List<Integer> diet_id;
        List<Integer> similar_account_diet_id;
        double[] LargerLabels;
        try{
            //获取其相应的
            diet_id=getDiedId(account,group);       //用户的食谱id，最近的几个。
            if(diet_id!=null){
                sign[0]=true;
                sigeIm[0]=15;                 //这个值的确定主要是通过其最近食谱的平稳度来设定的
            }

            similar_account=getSimilarAccount(account);
            similar_account_diet_id=getDiedId(similar_account,group);      //相似用户的
            if((similar_account_diet_id)!=null){
                sign[2]=true;
                sigeIm[2]=similar_account.getImpactRate();
            }
            //在上面的函数里面会顺带把LargerLabels这个东西给初始化了
            int[] labels=getLabelsHigh(AccountLabel);//获取到权重最高的三个标签的id---标签的id
            LargerLabels=avg(AccountLabel);    //权重大于平均值的标签，，，，//这个要改一下，代码太奇怪
            while(LargerLabels.length>2){
                LargerLabels=avg(LargerLabels);
            }
            sign[1]=true;
            sigeIm[1]=avgArray(LargerLabels);   //

            sign[3]=true;
            sigeIm[3]=((int)System.currentTimeMillis()%9)*1.1+10;

            int Recommendation=Max(sigeIm,sign);
            switch(Recommendation){
                case 0:
                    foods=getFoodByDiet(diet_id);
                    break;
                case 1:
                    foods=getFoodByLabel(labels,group);
                    break;
                case 2:
                    foods=getFoodByDiet(similar_account_diet_id);
                    break;
                case 3:
                    foods=getFoodRandom(group);
                    break;
                    default:break;
            }
        }catch (Exception e){
            e.printStackTrace();

        }
        return foods;
    }

    public static List<Food> getFoodByLabel(int[] a,int group)throws Exception{
        if(group==1){
            return getBreakfastRandom();
        }
        else{
            List<Food> foods=new ArrayList<>();
            foods.add(foodMain.get(((new Random()).nextInt(foodMain.size()))));
            ResultSet set=null;
            /**
             * 填充set
             * 通过数组a中存储的标签中筛选出事物，从地方菜中筛选出符合要求的三个
             * */
            int _id;
            int _group;
            String _name;
            int _heat;
            while(set.next()){
                _id=set.getInt(1);
                _group=set.getInt(2);
                _name=set.getString(3);
                _heat=set.getInt(4);
                foods.add(new Food(_id,_group,_name,_heat,1));  //这里的1个人是当作份数来使用的
            }//这里将筛选后两个
            return foods;
        }
    }

    public static int[] getLabelsHigh(double[] a){
        int[] arr={0,0,0};
        for(int i=0;i<a.length;i++){
            if(a[i]>arr[0]){
                arr[2]=arr[1];
                arr[1]=arr[0];
                arr[0]=i;
            }
            else if(a[i]>arr[1]){
                arr[2]=arr[1];
                arr[1]=i;
            }
            else if(a[i]>arr[2]){
                arr[2]=i;
            }
        }
        return arr;
    }
    public static List<Food> getFoodByDiet(List<Integer> a)throws Exception{
        int t=(int)System.currentTimeMillis()%(a.size());
        int diet=a.get(t);
        List<Food> foods=new ArrayList<>();
        ResultSet set=null;
        /**
         * 填充set
         * 获取到diet对应的全部食品的id，name，heat，quantity
         * */
        int _id;
        String _name;
        int _heat;
        int _quantity;
        while(set.next()){
            _id=set.getInt(1);
            _name=set.getString(2);
            _heat=set.getInt(3);
            _quantity=set.getInt(4);
            foods.add(new Food(_id,-1,_name,_heat,_quantity));
        }
        return foods;
    }


    public static List<Food> getFoodRandom(int group)throws Exception{
        if(group==1){
            return getBreakfastRandom();
        }
        else{
            return getFoodRandom();
        }
    }
    public static List<Food> getBreakfastRandom()throws Exception{
        List<Food> foods1=new ArrayList<>();
        List<Food> foods2=new ArrayList<>();
        List<Food> foods=new ArrayList<>();
        ResultSet set=null;
        //平均而言，正常而言，早餐的热量摄入是500大卡
        //推荐两种食物，各50
        /**
        *
        * 填充set值，
        * 从谷薯芋、杂豆、主食这个里面筛选，筛选出id，group，name，heat这四个值
        * */
        int num=0;
        int _id;
        int _group;
        String _name;
        int _heat;
        while(set.next()){
            _id=set.getInt(1);
            _group=set.getInt(2);
            _name=set.getString(3);
            _heat=set.getInt(4);
            foods1.add(new Food(_id,_group,_name,_heat,0));
            num++;
        }
        Random random=new Random();
        int i1=random.nextInt(num)+1;
        int i2=random.nextInt(num)+1;
        while(i1==i2){
            i2=random.nextInt(num)+1;
        }
        foods1.get(i1).setQuantity(250/foods1.get(i1).getHeat()*100);
        foods1.get(i2).setQuantity(250/foods1.get(i2).getHeat()*100);
        foods.add(foods1.get(i1));
        foods.add(foods1.get(i2));
        /**
         * 重新填写set
         * 这次从奶类及其制品中筛选全部，筛选信息同上
         * */
        num=0;
        while(set.next()){
            _id=set.getInt(1);
            _group=set.getInt(2);
            _name=set.getString(3);
            _heat=set.getInt(4);
            foods2.add(new Food(_id,_group,_name,_heat,0));
            num++;
        }
        Random random1=new Random();
        int i3=random1.nextInt(num)+1;
        foods2.get(i3).setQuantity(150/foods2.get(i3).getHeat()*100);
        foods.add(foods2.get(i3));
        return foods;
    }
    public static List<Food> getFoodRandom()throws Exception{
       List<Food> foods=getCSV();
       Random random=new Random();
       int _size=foodMain.size();
       if(-_size>0){
           int i=random.nextInt(_size);
           foods.add(foodMain.get(i));
       }

       //ResultSet set=null;
       /**
        * 全部地方菜中筛选3个推荐，从数据集中筛选3个放在set中
        * */
       /* int _id;
        int _group;
        String _name;
        int _heat;
        while(set.next()){
            _id=set.getInt(1);
            _group=set.getInt(2);
            _name=set.getString(3);
            _heat=set.getInt(4);
            foods.add(new Food(_id,_group,_name,_heat,1));
        }//这里将筛选后两个*/
        return foods;
    }


    public static double[] avg(double[] a){
        double avg=0;
        for(int i=0;i< a.length;i++){
            avg+=a[i];
        }
        avg=avg/a.length;
        int num=0;
        for(int i=0;i<a.length;i++){
            if(a[i]>avg)num++;
        }
        double[] larger=new double[num];
        num=0;
        for(int i=0;i<a.length;i++){
            if(a[i]>avg){
                larger[num]=a[i];
                num++;
            }
        }
        return larger;
    }

    public static List<Integer> getDiedId(Account account,int group)throws Exception{
        List<Integer> diet_id=new ArrayList<>();
        ResultSet set=null;
        int account_id=account.getId();
        //读取数据库，填充set数据集，
        //只筛选出int值，也就是饮食记录中的id，筛选条件是时间是前一周的，用户id就是account_id，group值就是传进来的参数
        /**
         *
         * 读数据库
         *
         * */
        while(set.next()){
            diet_id.add(set.getInt(1));
        }
        return diet_id;
    }




    public static Account getSimilarAccount(Account account)throws Exception{
        double BMI=account.getWeight()/(account.getHeight()/100*account.getHeight()/100);      //获取体重身高比  kg/m/m
        int age=account.getAge();
        int ageMin=age-3;
        int ageMax=age+3;
        List<Account> accountList=new ArrayList<>();
        ResultSet set=null;        //按照id，年龄，身高，体重获得数据
        /*
        *
        * 读数据库
        *
        * */
        //set的获取，条件是年龄在  ageMin----ageMax之间的,但是要排除自身
       while(set.next()){
           int _id=set.getInt(1);
           int _age=set.getInt(2);
           double _height=set.getDouble(3);
           double _weight=set.getDouble(4);
           accountList.add(new Account(_id,_age,_height,_weight,1));    //关于影响率这个值，最开始都是1
       }

       //double RimpactRate=1;        //最初的影响都设为1
       double DBMI=10;              //初始的BMI的差值设为10，后面逐步筛选，逐步减少List中的数量，直至小于二十
       while((accountList.size()>20)){   //选取年龄和BMI最相近的几个
           DBMI=DBMI/1.5;
           for(int i=0;i<accountList.size();i++){
               double _BMI=accountList.get(i).getWeight()/(accountList.get(i).getHeight()/100*accountList.get(i).getHeight()/100);
               if(Math.abs(BMI-_BMI)>DBMI){
                   accountList.remove(i);
               }
           }
       }
        //到这已经筛选人数至20以下，现在进一步通过标签的向量值筛选
        AccountLabel=QueryLabel(account.getId());      //函数在下面
        int _accoun_id_in_list=-1;    //这个用来存储标签权重点乘最小的那个用户id
        double _sim=0;//这个是初始sim值，用来保存最大的sim值 ，
        double Rsim;//这个将用来标识两个之间的sim值
        double[] _labelList;
        for(int i=0;i<accountList.size();i++){
            _labelList=QueryLabel(accountList.get(i).getId());          //获取到第i个人的标签数组
            Rsim=multiplication(AccountLabel,_labelList)/(EuclideanDistance(AccountLabel)*EuclideanDistance(_labelList));  //计算Rsim值//这两个函数都在下面
            if(Rsim>_sim){          //_sim是用来记录最大的那个
                _sim=Rsim;          //如果有更大就记录下这个值，
                _accoun_id_in_list=i;//accountList.get(i).getId();     //并记录下其账号id值
            }
        }
        //到这里筛选出最相近的那个人。
        System.out.println("最大的向量余弦值："+_sim);//就输出一下
        accountList.get(_accoun_id_in_list).setImpactRate(20*_sim);    //设置其影响值
        return accountList.get(_accoun_id_in_list);
    }
    public static double[] QueryLabel(int account_id)throws Exception{
        ResultSet set=null;
        /*
        *
        * 读数据库
        *
        * */
        //set就是依据传进来的值用户id来查询出来的标签和相应的权重
        //set获取该用户的标签和权重，通过该用户的id来获取，label_id，weight这样的顺序来
        int label_id;
        double[] label_weight=new double[SizeOfLabel];
        while(set.next()){
            label_id=set.getInt(1);
            label_weight[label_id-1]=set.getDouble(2);   //这里假设标签的值是从1开始的
        }
        return label_weight;
    }
    public static double EuclideanDistance(double[] a){
        double distance=0;
        for(int i=0;i<a.length;i++){
            distance=distance+a[i]*a[i];
        }
        distance=Math.sqrt(distance);
        return distance;
    }
    public static double multiplication(double[] a,double[] b){
        if(a.length!=b.length){
            return 0;
        }
        double product=0;
        for(int i=0;i<a.length;i++){
            product=a[i]*b[i]+product;
        }
        return product;
    }
    public static double avgArray(double[] a){
        double sum=0;
        for(int i=0;i<a.length;i++){
            sum+=a[i];
        }
        return (sum/a.length);
    }

    public static void init_foodMain(){
        foodMain.add(new Food(-1,-1,"大米",346,100));
        foodMain.add(new Food(-1,-1,"面条",283,150));
        foodMain.add(new Food(-1,-1,"饺子",231,180));
        /**
         * 一般常见的主食
         * 后续再加
         * */
    }

    public static int Max(double[] a,boolean[] b){
        double t=0;
        int r=-1;
        for(int i=0;i<a.length;i++){
            if((a[i]>t)&&b[i]){
                r=i;
            }
        }
        return r;
    }

    public static List<Food> getCSV()throws Exception{
        List<Food> foods=new ArrayList<>();
        String path="E:/AndroidS/file/cvs/foodcare/dishes/"+getDish();
        File kkFile = new File(path);
        InputStream in = new FileInputStream(kkFile);
        CsvReader cr = new CsvReader(in, ',', Charset.forName("UTF-8"));
        cr.readHeaders();
        /*if(cr.getHeaders().length!=3){
            System.out.println("错误");
            return null;
        }*/
        String _name;
        int _heat;
        int _quantity;
        while(cr.readRecord()){
            _name=cr.get(0);
            _heat=Integer.parseInt(cr.get(5).replaceAll("\\s*", ""));
            _quantity=100;
            foods.add(new Food(-1,-1,_name,_heat,_quantity));
            if(foods.size()>=2)break;
        }
        return foods;
    }
    public static String getDish(){
        List<String> dish=new ArrayList<>();
        dish.add("安徽菜 .csv");
        dish.add("北京菜 .csv");
        dish.add("东北菜 .csv");
        dish.add("广东菜 .csv");
        dish.add("河南菜 .csv");
        dish.add("浙江菜 .csv");
        dish.add("新疆菜 .csv");
        dish.add("上海菜 .csv");
        dish.add("台湾菜 .csv");
        dish.add("福建菜 .csv");
        int r=Math.abs((int)System.currentTimeMillis())%dish.size();
        System.out.println(r);
        System.out.println(dish.size());
        return dish.get(r);
    }


   /* private int id;
    private int group;
    private String name;
    private int heat;
    private int quantity;
*/


}
