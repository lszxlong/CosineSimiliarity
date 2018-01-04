# CosineSimiliarity
JAVA版的文本相似度计算：

## 调用方式：
```JAVA
        String s1 = "苹果公司是美国的一家高科技公司";
        String s2 = "小米公司是中国的一家高科技公司";
        Similarity similarity = new Similarity(s1, s2);
        double sim_value = similarity.sim();
        System.out.println(sim_value);
```

## 输出结果:
```java
0.6172133998483676

Process finished with exit code 0

```
