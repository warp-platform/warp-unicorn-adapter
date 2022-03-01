package com.warp.unicorn.utils.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;

import java.util.List;

@Slf4j
public class ListUtils {
    public static <T> Pair<List<T>, Integer> pagination(List<T> collection, int rowCount, Integer pageToken) {
        int offset = pageToken != null ? pageToken : 0;
        int from = rowCount * offset;
        int to = from + rowCount;
        int nextPageToken;
        if (to >= collection.size()) {
            nextPageToken = -1;
            to = collection.size();
        } else {
            nextPageToken = offset + 1;
        }
        log.debug("Limiting list, getting sublist from {} to {}", from, to);
        return Pair.of(collection.subList(from, to), nextPageToken);
    }
}
