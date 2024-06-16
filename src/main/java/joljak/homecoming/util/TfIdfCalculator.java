package joljak.homecoming.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public class TfIdfCalculator {

    private static final Logger logger = LoggerFactory.getLogger(TfIdfCalculator.class);

    private final Set<String> stopWords;

    public TfIdfCalculator(Set<String> stopWords) {
        this.stopWords = stopWords;
    }

    public Map<String, Double> computeTf(String text) {
        logger.info("TF 입력 텍스트: {}", text);
        List<String> words = Arrays.stream(text.split("\\s+"))  // 공백을 기준으로 단어 분리
                .map(String::toLowerCase)  // 모든 단어를 소문자로 변환
                .filter(word -> !stopWords.contains(word) && word.matches("\\p{L}+"))  // 불용어 제거 및 문자로만 구성된 단어 확인
                .collect(Collectors.toList());
//        logger.info("TF 입력 텍스트: {}", text);
//        List<String> words = Arrays.stream(text.split("\\W+"))
//                .map(String::toLowerCase)
//                 .filter(word -> !stopWords.contains(word))
//                .collect(Collectors.toList());

        logger.info("필터링된 단어 목록: {}", words);
        Map<String, Double> tfMap = new HashMap<>();
        for (String word : words) {
            tfMap.put(word, tfMap.getOrDefault(word, 0.0) + 1.0);
        }

        int wordCount = words.size();
        if (wordCount > 0) {
            tfMap.replaceAll((k, v) -> v / wordCount);
        }

        logger.info("TF Map: {}", tfMap);
        return tfMap;
    }
    //gpt가 추천해준거
    public Map<String, Double> computeIdf(List<String> texts) {
        logger.info("IDF 입력 텍스트 목록: {}", texts);
        Map<String, Double> idfMap = new HashMap<>();
        int totalDocuments = texts.size();

        for (String text : texts) {
            Set<String> words = Arrays.stream(text.split("\\s+"))
                    .map(String::toLowerCase)
                    .filter(word -> !stopWords.contains(word) && word.matches("\\p{L}+"))
                    .collect(Collectors.toSet());

            for (String word : words) {
                idfMap.put(word, idfMap.getOrDefault(word, 0.0) + 1.0);
            }
        }
//        idfMap.replaceAll((k, v) -> Math.log((double) totalDocuments / (v + 1))); gpt가 추천해준거 이거로 활용하면 값이 변경됌
        idfMap.replaceAll((k, v) -> Math.log((double) totalDocuments / v)); // 기존에 있던거

        logger.info("IDF Map: {}",idfMap);
        return idfMap;
    }
// 기존코드
//    public Map<String, Double> computeIdf(List<String> texts) {
//        logger.info("IDF 입력 텍스트 목록: {}", texts);
//        Map<String, Double> idfMap = new HashMap<>();
//        int totalDocuments = texts.size();
//
//        for (String text : texts) {
//            Set<String> words = Arrays.stream(text.split("\\W+"))
//                    .map(String::toLowerCase)
//                     .filter(word -> !stopWords.contains(word))
//                    .collect(Collectors.toSet());
//
//            for (String word : words) {
//                idfMap.put(word, idfMap.getOrDefault(word, 0.0) + 1.0);
//            }
//        }
//
//        idfMap.replaceAll((k, v) -> Math.log(totalDocuments / (v + 1)));
//
//        logger.info("IDF Map: {}", idfMap);
//        return idfMap;
//    }

    public Map<String, Double> computeTfIdf(String text, Map<String, Double> idfMap) {
        Map<String, Double> tfMap = computeTf(text);
        Map<String, Double> tfIdfMap = new HashMap<>();

        for (Map.Entry<String, Double> entry : tfMap.entrySet()) {
            String word = entry.getKey();
            double tfIdf = entry.getValue() * idfMap.getOrDefault(word, 0.0);
            tfIdfMap.put(word, tfIdf);
        }

        logger.info("TF-IDF Map: {}",tfIdfMap);
        return tfIdfMap;
    }

    // gpt가 추천해준거
    public double cosineSimilarity(Map<String, Double> vectorA, Map<String, Double> vectorB) {
        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;

        Set<String> allWords = new HashSet<>(vectorA.keySet());
        allWords.addAll(vectorB.keySet());

        for (String word : allWords) {
            double a = vectorA.getOrDefault(word, 0.0);
            double b = vectorB.getOrDefault(word, 0.0);
            dotProduct += a * b;
            normA += Math.pow(a, 2);
            normB += Math.pow(b, 2);
        }

        if (normA == 0 || normB == 0) return 0.0;  // 제로 벡터를 처리하여 NaN을 방지

        return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
    }

//기존코드
//    public double cosineSimilarity(Map<String, Double> vectorA, Map<String, Double> vectorB) {
//        Set<String> words = new HashSet<>(vectorA.keySet());
//        words.addAll(vectorB.keySet());
//
//        double dotProduct = 0.0;
//        double normA = 0.0;
//        double normB = 0.0;
//
//        for (String word : words) {
//            double valA = vectorA.getOrDefault(word, 0.0);
//            double valB = vectorB.getOrDefault(word, 0.0);
//
//            dotProduct += valA * valB;
//            normA += valA * valA;
//            normB += valB * valB;
//        }
//
//        double similarity = dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
//        logger.info("Cosine Similarity: {}", similarity);
//        return similarity;
//    }
}
