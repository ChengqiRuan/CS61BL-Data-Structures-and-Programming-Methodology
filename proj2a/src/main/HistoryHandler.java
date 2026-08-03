package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngrams.TimeSeries;
import plotting.Plotter;
import java.util.ArrayList;
import java.util.List;

public class HistoryHandler extends NgordnetQueryHandler {
    // 存放NGramMap，禁止static
    private final NGramMap wordMap;

    // 规定构造器
    public HistoryHandler(NGramMap map) {
        this.wordMap = map;
    }

    @Override
    public String handle(NgordnetQuery query) {
        // 提取查询参数
        int start = query.startYear();
        int end = query.endYear();
        List<String> inputWords = query.words();

        // 两个同步列表：单词图例、对应时序数据
        List<String> labels = new ArrayList<>();
        List<TimeSeries> tsData = new ArrayList<>();

        for (String word : inputWords) {
            // 获取该单词指定区间的权重时序
            TimeSeries wordTs = wordMap.weightHistory(word, start, end);
            labels.add(word);
            tsData.add(wordTs);
        }

        // 生成图表对象
        var chart = Plotter.generateTimeSeriesChart(labels, tsData);
        // 转为base64字符串返回网页
        return Plotter.encodeChartAsString(chart);
    }
}
