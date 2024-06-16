package joljak.homecoming.util;

import java.util.HashMap;
import java.util.Map;

public class TfIdfResult {

    private Map<String, Double> tfMap = new HashMap<>();
    private Map<String, Double> idfMap = new HashMap<>();
    private Map<String, Double> tfIdfMap = new HashMap<>();
    private double cosineSimilarity;

    public Map<String, Double> getTfMap() {
        return tfMap;
    }

    public void setTfMap(Map<String, Double> tfMap) {
        this.tfMap = tfMap;
    }

    public Map<String, Double> getIdfMap() {
        return idfMap;
    }

    public void setIdfMap(Map<String, Double> idfMap) {
        this.idfMap = idfMap;
    }

    public Map<String, Double> getTfIdfMap() {
        return tfIdfMap;
    }

    public void setTfIdfMap(Map<String, Double> tfIdfMap) {
        this.tfIdfMap = tfIdfMap;
    }

    public double getCosineSimilarity() {
        return cosineSimilarity;
    }

    public void setCosineSimilarity(double cosineSimilarity) {
        this.cosineSimilarity = cosineSimilarity;
    }

    public void printResults() {
        System.out.println("TF:");
        if (tfMap != null) {
            tfMap.forEach((k, v) -> System.out.println(k + ": " + v));
        }
        System.out.println("IDF:");
        if (idfMap != null) {
            idfMap.forEach((k, v) -> System.out.println(k + ": " + v));
        }
        System.out.println("TF-IDF:");
        if (tfIdfMap != null) {
            tfIdfMap.forEach((k, v) -> System.out.println(k + ": " + v));
        }
        System.out.println("Cosine Similarity: " + cosineSimilarity);
    }
}
