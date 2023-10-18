package ksv.ui;

import ksv.core.mvp.presenter.Presenter;
import ksv.core.mvp.presenter.ToyRafflePresenter;

public class App {
    public void run(){
        Presenter presenter = new ToyRafflePresenter();
        while (presenter.flag()){
            presenter.loadScreen(1);
            presenter.switchAction();
        }
    }
}
