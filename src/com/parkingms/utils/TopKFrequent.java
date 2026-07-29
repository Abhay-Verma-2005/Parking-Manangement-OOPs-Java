package com.parkingms.utils;

import java.util.*;

public class TopKFrequent {
    
    public static List<Map.Entry<String, Integer>> getTopK(List<String> items, int k) {
        Map<String, Integer> frequencyMap = new HashMap<>();
        for (String item : items) {
            frequencyMap.put(item, frequencyMap.getOrDefault(item, 0) + 1);
        }
        
        int limit = Math.min(k, frequencyMap.size());
        if (limit <= 0) {
            return new ArrayList<>();
        }

        PriorityQueue<Map.Entry<String, Integer>> minHeap = 
            new PriorityQueue<>((a, b) -> a.getValue().compareTo(b.getValue()));

        for (Map.Entry<String, Integer> entry : frequencyMap.entrySet()) {
            minHeap.offer(entry);
            if (minHeap.size() > limit) {
                minHeap.poll();
            }
        }

        List<Map.Entry<String, Integer>> result = new ArrayList<>();
        while (!minHeap.isEmpty()) {
            result.add(minHeap.poll());
        }
        
        Collections.reverse(result); // Most frequent first
        return result;
    }
}
