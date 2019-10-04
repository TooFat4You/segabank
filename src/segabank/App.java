package segabank;

import segabank.bo.*;
import segabank.dal.AgenceDAO;
import segabank.dal.CompteDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class App {
    public static void main(String[] args) {
        Agence agence = new Agence(1, "", "");
        ComptePayant compte = new ComptePayant(2, 4.5, agence);
        CompteDAO compteDAO = new CompteDAO();
        try {
            compteDAO.create(compte);
            System.out.println(compte);
        } catch (SQLException | IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
