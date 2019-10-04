package segabank;

import segabank.bo.*;
import segabank.dal.AgenceDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class App {
    public static void main(String[] args) {
        AgenceDAO agenceDAO = new AgenceDAO();
        try {
            List<Agence> agences = agenceDAO.findAll();
            for (Agence agence : agences) {
                for (Compte compte : agence.getComptePayants()) {
                    System.out.println(compte);
                }
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
