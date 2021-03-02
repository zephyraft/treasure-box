package zephyr.nlp.hanlp;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.corpus.io.IOUtil;
import com.hankcs.hanlp.dictionary.CoreDictionary;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;


@Slf4j
public class HanLPDemo {

    public static void main(String[] args) throws IOException {
        // 开启debug模式
        HanLP.Config.enableDebug(false);

        // 分词
        log.info("HanLP.segment: {}", HanLP.segment("你好，欢迎使用HanLP汉语处理包"));
        log.info("HanLP.segment: {}", HanLP.segment("就读北京大学"));

        // 加载词典
        TreeMap<String, CoreDictionary.Attribute> dictionary = IOUtil.loadDictionary("/Users/zephyr/IdeaProjects/treasure-box/treasure-box-nlp/src/main/data-for-1.7.5/data/dictionary/CoreNatureDictionary.mini.txt");
        log.info("词典大小: {}个词条", dictionary.size());
        log.info("首个单词: {}", dictionary.keySet().iterator().next());

        // 词典分词
        log.info("=====================完全切分=====================");
        log.info("完全切分: {}", segmentFully("你好，欢迎使用HanLP汉语处理包", dictionary));
        log.info("完全切分: {}", segmentFully("就读北京大学", dictionary));

        log.info("=====================正向最长匹配=====================");
        log.info("正向最长匹配正例: {}", segmentForwardLongest("就读北京大学", dictionary));
        log.info("正向最长匹配反例: {}", segmentForwardLongest("研究生命起源", dictionary));

        log.info("=====================逆向最长匹配=====================");
        log.info("逆向最长匹配正例: {}", segmentBackwardLongest("研究生命起源", dictionary));
        log.info("逆向最长匹配反例: {}", segmentBackwardLongest("项目的研究", dictionary));

        log.info("=====================双向最长匹配=====================");
        log.info("双向最长匹配反例: {}", segmentBidirectional("项目的研究", dictionary));
        log.info("双向最长匹配正例: {}", segmentBidirectional("商品和服务", dictionary));
        log.info("双向最长匹配正例: {}", segmentBidirectional("研究生命起源", dictionary));
        log.info("双向最长匹配反例: {}", segmentBidirectional("当下雨天地面积水", dictionary));
        log.info("双向最长匹配正例: {}", segmentBidirectional("结婚的和尚未结婚的", dictionary));
        log.info("双向最长匹配反例: {}", segmentBidirectional("欢迎新老师生前来就餐", dictionary));

    }

    /**
     * 完全切分算法
     * @param text 待分词文本
     * @param dictionary 词典
     * @return 单词列表
     */
    private static List<String> segmentFully(String text, TreeMap<String, CoreDictionary.Attribute> dictionary) {
        List<String> wordList = new LinkedList<>();
        for (int i = 0; i < text.length(); ++i) {
            for (int j = i + 1; j <= text.length(); ++j) {
                String word = text.substring(i, j);
                if (dictionary.containsKey(word)) {
                    wordList.add(word);
                }
            }
        }
        return wordList;
    }

    /**
     * 正向最长匹配的中文分词算法
     * @param text 待分词文本
     * @param dictionary 词典
     * @return 单词列表
     */
    private static List<String> segmentForwardLongest(String text, TreeMap<String, CoreDictionary.Attribute> dictionary) {
        List<String> wordList = new LinkedList<>();
        for (int i = 0; i < text.length();) {
            String longestWord = text.substring(i, i + 1);
            for (int j = i + 1; j <= text.length(); ++j) {
                String word = text.substring(i, j);
                if (dictionary.containsKey(word)) {
                    if (word.length() > longestWord.length()) {
                        longestWord = word;
                    }
                }
            }
            wordList.add(longestWord);
            i += longestWord.length();
        }
        return wordList;
    }

    /**
     * 逆向最长匹配的中文分词算法
     * @param text 待分词文本
     * @param dictionary 词典
     * @return 单词列表
     */
    private static List<String> segmentBackwardLongest(String text, TreeMap<String, CoreDictionary.Attribute> dictionary) {
        List<String> wordList = new LinkedList<>();
        for (int i = text.length() - 1; i >= 0;) {
            String longestWord = text.substring(i, i + 1);
            for (int j = 0; j <= i; ++j) {
                String word = text.substring(j, i + 1);
                if (dictionary.containsKey(word)) {
                    if (word.length() > longestWord.length()) {
                        longestWord = word;
                    }
                }
            }
            wordList.add(0, longestWord);
            i -= longestWord.length();
        }
        return wordList;
    }

    /**
     * 统计分词结果中的单字数量
     * @param wordList 分词结果
     * @return 单字数量
     */
    private static int countSingleChar(List<String> wordList) {
        int size = 0;
        for (String word : wordList) {
            if (word.length() == 1) {
                ++ size;
            }
        }
        return size;
    }

    /**
     * 双向最长匹配的中文分词算法
     * @param text 待分词文本
     * @param dictionary 词典
     * @return 单词列表
     */
    private static List<String> segmentBidirectional(String text, TreeMap<String, CoreDictionary.Attribute> dictionary) {
        List<String> forwardLongest = segmentForwardLongest(text, dictionary);
        List<String> backwardLongest = segmentBackwardLongest(text, dictionary);
        if (forwardLongest.size() < backwardLongest.size()) {
            return forwardLongest;
        } else if (forwardLongest.size() > backwardLongest.size()) {
            return backwardLongest;
        } else {
            if (countSingleChar(forwardLongest) < countSingleChar(backwardLongest)) {
                return forwardLongest;
            } else {
                return backwardLongest;
            }
        }
    }
}
