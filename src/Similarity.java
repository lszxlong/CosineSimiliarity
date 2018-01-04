

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.*;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

public class Similarity {
    Map<String, int[]> vectorMap = new HashMap<String, int[]>();

    int[] tempArray = null;

    public Similarity(String content1, String content2){
        List<String> words1 = segStr(content1);
        List<String> words2 = segStr(content2);
        for (int i = 0; i < words1.size(); i++) {
            if (vectorMap.containsKey(words1.get(i))) {
                vectorMap.get(words1.get(i))[0]++;
            } else {
                tempArray = new int[2];
                tempArray[0] = 1;
                tempArray[1] = 0;
                vectorMap.put(words1.get(i), tempArray);
            }
        }

        for (int i = 0; i < words2.size(); i++) {
            if (vectorMap.containsKey(words2.get(i))) {
                vectorMap.get(words2.get(i))[1]++;
            } else {
                tempArray = new int[2];
                tempArray[0] = 0;
                tempArray[1] = 1;
                vectorMap.put(words2.get(i), tempArray);
            }
        }

    }

    // 求余弦相似度
    public double sim() {
        double result = 0;
        result = pointMulti(vectorMap) / sqrtMulti(vectorMap);
        return result;
    }

    private double sqrtMulti(Map<String, int[]> paramMap) {
        double result = 0;
        result = squares(paramMap);
        result = Math.sqrt(result);
        return result;
    }

    // 求平方和
    private double squares(Map<String, int[]> paramMap) {
        double result1 = 0;
        double result2 = 0;
        Set<String> keySet = paramMap.keySet();
        for (String key : keySet) {
            int temp[] = paramMap.get(key);
            result1 += (temp[0] * temp[0]);
            result2 += (temp[1] * temp[1]);
        }
        return result1 * result2;
    }
    //分词
    public static List<String> segStr(String content){
        // 分词
        Reader input = new StringReader(content);
        // 智能分词关闭（对分词的精度影响很大）
        IKSegmenter iks = new IKSegmenter(input, true);
        Lexeme lexeme = null;
        List<String> list = new ArrayList<String>();

        try {
            while ((lexeme = iks.next()) != null) {
                list.add(lexeme.getLexemeText());
            }
        }catch(IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    // 点乘法
    private double pointMulti(Map<String, int[]> paramMap) {
        double result = 0;
        for (String key :paramMap.keySet()) {
            int temp[] = paramMap.get(key);
            result += (temp[0] * temp[1]);
        }
        return result;
    }


    public static void main(String[] args) {
        String s1 = "苹果公司是美国的一家高科技公司";
        String s2 = "小米公司是中国的一家高科技公司";
        Similarity similarity = new Similarity(s1, s2);
        double sim_value = similarity.sim();
        System.out.println(sim_value);
    }

}