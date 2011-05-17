package org.iglootools.commons.generator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RandomNumbersGenerator {
    public static List<Integer> randomIndexOrder(int fromInclusive, int toInclusive) {
        List<Integer> indexes = new ArrayList<Integer>();
        for (int i = fromInclusive; i <= toInclusive; i++) {
            indexes.add(i);
        }
        Collections.shuffle(indexes);
        return indexes;
    }

    public static List<Integer> randomIndexOrderFrom0To9() {
        return randomIndexOrder(0, 9);
    }
}
