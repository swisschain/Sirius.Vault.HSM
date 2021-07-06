package io.swisschain.crypto.utils.polkadot;

import com.google.common.collect.Lists;
import java.util.List;

public interface Defaults {

    public static List<Integer> allowedDecodedLengths = Lists.newArrayList(1, 2, 4, 8, 32);
    public static List<Integer> allowedEncodedLengths = Lists.newArrayList(3, 4, 6, 10, 35);
    public static List<Integer> allowedPrefix = Lists.newArrayList(0, 1, 3, 42, 43, 68, 69);
}

