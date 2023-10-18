package ksv.core.mvp.model;

import ksv.core.models.Toy;

import java.util.HashMap;

public interface Model {
    public HashMap<Toy, Integer> getPool();

    public HashMap<Toy, Integer> getIssuance();

    public double getChance(Toy toy);

    public void saveDraft(String draft);

    public String getDraft();

    public int getTickets();

    public void sellAward(Toy toy);
    public void winToy(Toy toy);

    public void setTickets(int tickets);
}
