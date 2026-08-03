package main;
import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import java.util.*;

public class HyponymsHandler extends NgordnetQueryHandler {
    private WordNet wordNet;
    private NGramMap nGramMap;

    public HyponymsHandler(WordNet wordNet, NGramMap ngMap) {
        this.wordNet = wordNet;
        this.nGramMap = ngMap;
    }

    public String handle(NgordnetQuery query) {
        List<String> words = query.words();
        int start = query.startYear();
        int end = query.endYear();
        int k = query.k();
        //提取信息从网站
        Set<String> common = wordNet.getCommonHyponyms(words);

        if (k == 0) {
            Set<String> res = common;
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            boolean first = true; // 标记是否为第一个单词
            for (String s : res) {
                if (!first) { // 非首个元素，先拼接逗号空格
                    sb.append(", ");
                }
                sb.append(s);
                first = false; // 遍历过一次后标记置为false
            }
            sb.append("]");
            return sb.toString();
        } else {
            Map<String, Double> wordTotalCount = new HashMap<>();
            for (String word : common) {
                // 获取年份数据
                TimeSeries ts = nGramMap.countHistory(word, start, end);
                double total = 0;
                // 累加所有年份计数
                for (double d : ts.data()) {
                    total += d;
                }
                //System.out.println(word + total);
                // 只保留区间总频次大于0的单词
                if (total > 0) {
                    wordTotalCount.put(word, total);
                }
            }
            // 只拿有频次的单词排序
            List<String> valid = new ArrayList<>(wordTotalCount.keySet());
            // 按频次从高到低
            valid.sort((a, b) -> Double.compare(wordTotalCount.get(b), wordTotalCount.get(a)));

            // 截取前k个
            int take = Math.min(k, valid.size());
            // List<String> topK = valid.subList(0, take);
            //
            // 按字母排序
            //Collections.sort(topK);

            Set<String> topK = new TreeSet<>();
            for (int i = 0; i < take; i++) {
                topK.add(valid.get(i));
            }

            StringBuilder sb = new StringBuilder();
            sb.append("[");
            boolean first = true;
            for (String s : topK) {
                if (!first) {
                    sb.append(", ");
                }
                sb.append(s);
                first = false;
            }
            sb.append("]");
            return sb.toString();
        }
    }
}
