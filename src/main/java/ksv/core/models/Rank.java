package ksv.core.models;

import com.sun.jdi.Value;

enum Rank {
    mythic(1, "mythic"),
    legendary(2, "legendary"),
    epic(5, "epic"),
    rare(10, "rare"),
    uncommon(20, "uncommon"),
    common(100, "common"),
    trash(100, "trash");
    private int rarity;

    private String name;

    Rank(int rarity, String name) {
        this.rarity = rarity;
        this.name = name;
    }

    @Override
    public String toString() {
        return "{" + name + "}";
    }

    public int getRarity() {
        return rarity;
    }

    public void setRarity(int rarity) {
        this.rarity = rarity;
    }

    public static Rank toRank(String rank) {
        switch (rank) {
            case "common" -> {
                return common;
            }
            case "uncommon" -> {
                return uncommon;
            }
            case "rare" -> {
                return rare;
            }
            case "epic" -> {
                return epic;
            }
            case "legendary" -> {
                return legendary;
            }
            case "mythic" -> {
                return mythic;
            }
        }
        return trash;
    }


}
