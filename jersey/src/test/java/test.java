import org.springframework.data.mongodb.core.mapping.TextScore;

public class test {

    public void Test(){
        double x=0.1;
        double y;
        long start = System.currentTimeMillis();
        long end;
        for(int i = 0; i<= 100000; i++){
            y= Math.pow(2.03,x)/10;
            System.out.println(y);
            x = x + 0.0000215;
        }
        end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}
