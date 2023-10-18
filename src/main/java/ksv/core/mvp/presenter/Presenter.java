package ksv.core.mvp.presenter;

import ksv.core.models.Toy;

import java.util.List;

public interface Presenter {
    public boolean flag();

    public List<Toy> loadScreen(int screen);

    public void switchAction();
}
