package ksv.core.mvp.presenter;

import ksv.core.Config;
import ksv.core.models.Toy;
import ksv.core.mvp.model.Model;
import ksv.core.mvp.model.ToyRaffleModel;
import ksv.core.mvp.view.ConsoleView;
import ksv.core.mvp.view.View;

import java.util.*;

public class ToyRafflePresenter implements Presenter {
    int screen;
    View view;
    Model model;
    boolean flag;

    public ToyRafflePresenter() {
        this.view = new ConsoleView();
        this.model = new ToyRaffleModel();
        this.flag = true;
        this.screen = 1;
    }

    @Override
    public boolean flag() {
        return flag;
    }



    @Override
    public List<Toy> loadScreen(int screen) {
        view.clear();
        StringBuilder str = new StringBuilder();
        int i = 0;
        switch (screen) {
            case 1 -> {
                List<Toy> list = new ArrayList<>(model.getPool().keySet());
                Collections.sort(list);
                for (Toy toy : list) {
                    if (model.getChance(toy) != 0) {
                        i++;
                        str.append(String.format("%s. %s: %s\n",
                                i,
                                toy.getName(),
                                model.getChance(toy)));
                    }
                }
                str.append(String.format("у вас осталось %s билетов, 1 прокрутка стоит %s билетов\n",
                        model.getTickets(),
                        Config.gameCost));
                str.append("1. Прокрутить рулетку       2. Посмотреть выйгранное    \n");
                str.append("3. Продать ненужное         3. Получить выйгрыш         \n");
                view.set(str.toString());
                return list;
            }
            case 2 -> {
                List<Toy> list = new ArrayList<>(model.getIssuance().keySet());
                Collections.sort(list);
                for (Toy toy : list) {
                    if (model.getIssuance().get(toy) != 0) {
                        i++;
                        str.append(String.format("%s. %s: %s штук, стоимостью: %s каждая\n",
                                i,
                                toy.getName(),
                                model.getIssuance().get(toy),
                                toy.getCost()));
                    }
                }
                str.append("Введите номер награды для обмена, quit или q для выхода    \n");
                view.set(str.toString());
                return list;
            }
            default -> {
                return null;
            }
        }
    }

    @Override
    public void switchAction() {
        switch (view.get()) {
            case "1" -> {
                spin();
            }
            case "2" -> {
                showAwards();
            }
            case "3" -> {
                exchangeAwards();
            }
            case "4" -> {
                takeRewards();
                quit();
            }
            default -> {

            }
        }
    }

    private void spin() {
        if (model.getTickets() < Config.gameCost) {
            view.set("У вас не хватает билетов");
            view.get();
            return;
        }
        HashMap<Toy, Integer> pool = model.getPool();
        List<Toy> list = new ArrayList<>(pool.keySet());
        Random rand = new Random();
        int amount = 0;
        for (Toy toy : list) {
            amount += pool.get(toy);
        }
        if (amount > 0) {
            model.setTickets(model.getTickets() - Config.gameCost);
            int i = rand.nextInt(amount);
            for (Toy toy : list) {
                i -= pool.get(toy);
                if (i <= 0) {
                    model.winToy(toy);
                    view.set(String.format("Вы выйграли %s ранга %s\n", toy.getName(), toy.getRank()));
                    break;
                }
            }
            view.get();
        }
    }

    private void showAwards() {
        view.set(model.getDraft());
        view.get();
    }

    private void exchangeAwards() {
        boolean flag = true;
        while (flag) {
            List<Toy> toyList = loadScreen(2);
            String choice;
            if (toyList.isEmpty()) {
                choice = "q";
            } else {
                choice = view.get();
            }
            HashMap<Toy, Integer> issuance = model.getIssuance();
            for (int i = 0; i < toyList.size(); i++) {
                if (issuance.get(toyList.get(i)) <= 0) {
                    toyList.remove(i);
                    i--;
                }
            }
            switch (choice) {
                case "quit", "q", "qqq", "выход", "вых", "exit", "ex" -> {
                    flag = false;
                }
                default -> {
                    try {
                        int i = Integer.parseInt(choice);
                        i--;
                        model.sellAward(toyList.get(i));
                    } catch (NumberFormatException e) {
                        view.set("l1");
                    } catch (IndexOutOfBoundsException e) {
                        view.set("l2");
                    }
                }
            }
        }
    }

    private void takeRewards() {
        String str = model.getDraft();
        model.saveDraft(str);
        view.set(str);
    }

    private void quit() {
        flag = false;
    }
}
