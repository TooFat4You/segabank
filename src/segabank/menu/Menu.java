package segabank.menu;

import segabank.bo.Agence;
import segabank.dal.AgenceDAO;

import java.util.List;

public class Menu {
    public String getBoard(String msg) {
        msg = msg.toUpperCase();

        String border = "====================================";
        StringBuilder md = new StringBuilder(border);
        md.append(System.lineSeparator());
        Integer n = border.length();
        Integer m = n / 2;
        Integer y = msg.length() / 2;
        m = m - y;
        Integer t = m * 2;
        for (Integer i = 0; i != t; i++) {
            md.append("=");
            if (i == m) {
                md.append(" ");
                md.append(msg);
                md.append(" ");
            }
        }
        md.append(System.lineSeparator());
        md.append(border);
        return md.toString();
    }

    private List<Agence> agences;
    private AgenceDAO agenceDAO;

    public Menu(List<Agence> agences, AgenceDAO agenceDAO) {
        this.agences = agences;
        this.agenceDAO = agenceDAO;
    }

    public void printMenu() {
        System.out.println("Bienvenue dans le menu !");
        System.out.printf("Il existe actuellement %d agences ! %s", agences.size(), System.lineSeparator());
        System.out.println(getBoard("salut a vous tous !"));
    }
}
