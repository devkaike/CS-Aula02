import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        JFrame frame = new JFrame("Aula02");
        frame.setContentPane(new Tela().paiel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}