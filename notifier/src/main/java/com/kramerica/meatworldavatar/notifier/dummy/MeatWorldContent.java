package com.kramerica.meatworldavatar.notifier.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 */
public class MeatWorldContent {

    /**
     * An array of sample (dummy) items.
     */
    public static List<MeatWorldItem> ITEMS = new ArrayList<MeatWorldItem>();

    /**
     * A map of meat world items.
     */
    public static Map<String, MeatWorldItem> ITEM_MAP = new HashMap<String, MeatWorldItem>();

    static {
        // Add 3 sample items.
        addItem(new MeatWorldItem("M", "Your personal meat"));
        addItem(new MeatWorldItem("S", "Your meatspace"));
        addItem(new MeatWorldItem("C", "Your meatcoin"));
    }

    private static void addItem(MeatWorldItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class MeatWorldItem {
        public String id;
        public String content;

        public MeatWorldItem(String id, String content) {
            this.id = id;
            this.content = content;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
