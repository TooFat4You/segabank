package segabank.menu;

import segabank.bo.Agence;
import segabank.dal.AgenceDAO;

import javax.tools.Tool;
import java.util.List;

public class Menu {

    private List<Agence> agences;
    private MenuAgence menuAgence;

    public Menu(List<Agence> agences) {
        this.agences = agences;
        menuAgence = new MenuAgence(agences);
    }

    public void printMenu() {
        menuAgence.printMenu();
    }
}
