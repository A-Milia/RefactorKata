package com.gildedrose;

class GildedRose {

    Item[] items;

    public static final String AGED_BRIE = "Aged Brie";
    public static final String CONJURED_MANA_CAKE = "Conjured Mana Cake";
    public static final String SULFURAS_HAND_OF_RAGNAROS = "Sulfuras, Hand of Ragnaros";
    public static final String BACKSTAGE_PASSES_TO_A_TAFKAL = "Backstage passes to a TAFKAL80ETC concert";
    public static final int LIMIT_SELLIN_DAY = 0;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (Item item: items) {
            if (!item.name.equals(BACKSTAGE_PASSES_TO_A_TAFKAL) && !item.name.equals(CONJURED_MANA_CAKE) && !item.name.equals(AGED_BRIE)) {
                degradationQualityNormalItem(item);
            } else {
                degradationQualitySpecialItem(item);
            }
            decreaseExpirationDay(item); // restamos un dia de caducidad salvo para el legendario
        }
    }

    private Item degradationQualityNormalItem(Item item) { //degradación para objetos normales
        if (!item.name.equals(SULFURAS_HAND_OF_RAGNAROS)) {
            if (item.sellIn >= LIMIT_SELLIN_DAY) {
                item.quality = decreaseQuality(item.quality, 1,item);
            } else {
                item.quality = decreaseQuality(item.quality,2, item);
            }
        }
        return item;
    }

    private Item degradationQualitySpecialItem(Item item) { //degradación para objetos con peculiaridades
        if (item.name.equals(AGED_BRIE)) {
            agedBrieItem(item);
        }
        if (item.name.equals(BACKSTAGE_PASSES_TO_A_TAFKAL)) {
            backStageItem(item);
        }
        if (item.name.equals(CONJURED_MANA_CAKE)) {
            conjuredItem(item);
        }
        return item;
    }

    private int decreaseQuality(int currentValue, int quality, Item item){
        if(item.sellIn >= LIMIT_SELLIN_DAY){
            item.quality= currentValue - quality;
        }
        return item.quality;
    }

    private int upgradeQuality(int currentValue, int quality, Item item){
        if(item.sellIn >= LIMIT_SELLIN_DAY){
            item.quality= currentValue + quality;
        }
        return item.quality;
    }

    private Item agedBrieItem(Item item) {
        if (item.sellIn > LIMIT_SELLIN_DAY) {
            item.quality = upgradeQuality(item.quality,1,item);
        }
        return item;
    }

    private Item backStageItem(Item item) {
        if (item.sellIn < 6) {
            item.quality = upgradeQuality(item.quality,3,item);
        }
        if (item.sellIn > 10) {
            item.quality = upgradeQuality(item.quality,1,item);
        }
        if (item.sellIn > 5 && item.sellIn < 11) {
            item.quality = upgradeQuality(item.quality,2,item);
        }
        return item;
    }

    private Item conjuredItem(Item item) {
        if (item.sellIn > LIMIT_SELLIN_DAY) {
            item.quality = decreaseQuality(item.quality,2,item);
        } else {
            item.quality = decreaseQuality(item.quality,4,item);
        }
        return item;
    }

    private void decreaseExpirationDay(Item item) {
        if (!item.name.equals(SULFURAS_HAND_OF_RAGNAROS)) {
            item.sellIn = item.sellIn - 1;
        }
    }
}