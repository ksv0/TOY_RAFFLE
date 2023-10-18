package ksv.core.models;

import java.util.Objects;
import ksv.core.models.Rank;

public class Toy implements Comparable<Toy> {
    private int id;
    private String name;

    private int cost;
    private Rank rank;


    public Toy(int id,
               String name,
               int cost,
               String rank) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.rank = Rank.toRank(rank);
    }

    public Toy(String id,
               String name,
               String cost,
               String rank) {
        this(Integer.parseInt(id),
                name,
                Integer.parseInt(cost),
                rank);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Toy toy = (Toy) o;
        return id == toy.id  && cost == toy.cost && Objects.equals(name, toy.name) && rank == toy.rank;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, cost, rank);
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public Rank getRank() {
        return rank;
    }
    public int getRarity(){
        return rank.getRarity();
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }
    public void setRank(String rank) {
        this.rank = Rank.toRank(rank);
    }

    @Override
    public int compareTo(Toy o) {
        return this.rank.getRarity() - o.getRank().getRarity();
    }
}
