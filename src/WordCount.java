import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class WordCount extends Thread{


    public  Map<String, Integer> counting(List<String> strings) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        for (String s:strings) {

            if (!map.containsKey(s)) {  // first time we've seen this string
                map.put(s, 1);
            }
            else {
                int count = map.get(s);
                map.put(s, count + 1);
            }
        }
        return map;
    }


    public void run() {
        try{

        }catch(Exception ex){

        }
    }


}
