package segabank.memu;

import segabank.bo.Agence;
import segabank.dal.AgenceDAO;

import java.util.List;

public class Menu {
    private List<Agence> agences;
    private AgenceDAO agenceDAO;

    public Menu(List<Agence> agences, AgenceDAO agenceDAO) {
        this.agences = agences;
        this.agenceDAO = agenceDAO;
    }

    public void printMenu() {
        System.out.println("Bienvenue dans le menu !");
        System.out.printf("Il existe actuellement %d agences !", agences.size());
    }
}
