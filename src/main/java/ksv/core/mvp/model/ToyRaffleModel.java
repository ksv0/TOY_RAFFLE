package ksv.core.mvp.model;

import ksv.core.Config;
import ksv.core.models.Toy;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ToyRaffleModel implements Model {
    HashMap<Toy, Integer> pool;
    HashMap<Toy, Integer> issuance;
    int tickets = 100 * Config.mode;

    public ToyRaffleModel() {
        this.pool = createPoolList();
        this.issuance = createPoolList(false);
    }

    @Override
    public HashMap<Toy, Integer> getPool() {
        return this.pool;
    }

    @Override
    public HashMap<Toy, Integer> getIssuance() {
        return this.issuance;
    }

    @Override
    public double getChance(Toy toy) {
        double oneToy = pool.get(toy);
        if (oneToy < 1) {
            return 0;
        }
        double allToys = 0;
        for (Toy item : pool.keySet()) {
            if (!item.equals(toy)) {
                allToys += pool.get(item);
            }
        }
        return (oneToy * 100) / allToys;
    }

    @Override
    public String getDraft() {
        StringBuilder str = new StringBuilder();
        int sumRewards = 0;
        for (Toy toy : issuance.keySet()) {
            sumRewards += issuance.get(toy);
        }
        str.append(String.format("Вы выйграли %s игрушек\n", sumRewards));
        for (Toy toy : issuance.keySet()) {
            if (issuance.get(toy)!=0) {
                str.append(String.format("%s в количестве %s\n", toy.getName(), issuance.get(toy)));
            }
        }
        str.append(String.format("у вас осталось %s билетов", tickets));
        return str.toString();
    }

    @Override
    public int getTickets() {
        return tickets;
    }

    @Override
    public void sellAward(Toy toy) {
        this.issuance.put(toy, this.issuance.get(toy) - 1);
        tickets += toy.getCost();
    }

    @Override
    public void saveDraft(String draft) {
        try (FileWriter br = new FileWriter(Config.deliveryListPath, false)) {
            br.append(draft);
            br.flush();
        } catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    @Override
    public void winToy(Toy toy) {
        this.pool.put(toy, this.pool.get(toy) - 1);
        this.issuance.put(toy, this.issuance.get(toy) + 1);
    }

    @Override
    public void setTickets(int tickets) {
        this.tickets = tickets;
    }

    public HashMap<Toy, Integer> createPoolList() {
        return createPoolList(true);
    }

    public HashMap<Toy, Integer> createPoolList(boolean key) {
        HashMap<Toy, Integer> pool = new HashMap<>();
        List<String[]> strings = parserCsv();
        for (String[] s : strings) {
            Toy toy = new Toy(s[0], s[1], s[2], s[3]);
            if (key) {
                pool.put(toy, toy.getRarity() * Config.mode);
            } else {
                pool.put(toy, 0);
            }
        }
        return pool;
    }

    private List<String[]> parserCsv() {
        List<String[]> lines = new ArrayList<>();
        File file = new File(Config.toysListPath);
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String row;
            while ((row = reader.readLine()) != null) {
                if (!row.startsWith("id")) {
                    lines.add(row.split(","));
                }
            }
        } catch (IOException e) {
            return new ArrayList<>();
        }
        return lines;
    }
}
