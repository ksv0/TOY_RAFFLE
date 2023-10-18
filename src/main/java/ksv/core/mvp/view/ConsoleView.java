package ksv.core.mvp.view;

import java.util.Scanner;

public class ConsoleView implements View {
    Scanner in;

    public ConsoleView() {
        this.in = new Scanner(System.in);
    }

    @Override
    public void set(String message) {
        System.out.print(message);
    }

    @Override
    public String get() {
        return in.nextLine();
    }

    @Override
    public void clear() {
        set("\n\n\n\n\n\n\n\n\n\n" +
                "\n\n\n\n\n\n\n\n\n\n" +
                "\n\n\n\n\n\n\n\n\n\n" +
                "\n\n\n\n\n\n\n\n\n\n" +
                "\n\n\n\n\n\n\n\n\n\n" +
                "\n\n\n\n\n\n\n\n\n\n" +
                "\n\n\n\n\n\n\n\n\n\n" +
                "\n\n\n\n\n\n\n\n\n\n" +
                "\n\n\n\n\n\n\n\n\n\n" +
                "\n\n\n\n\n\n\n\n\n\n");
    }
}
