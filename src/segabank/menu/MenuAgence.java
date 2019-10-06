package segabank.menu;

import segabank.bo.Agence;
import segabank.bo.Operation;
import segabank.csv.OperationCSV;
import segabank.dal.AgenceDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class MenuAgence {
    private List<Agence> agences;
    private Scanner scanner;
    private AgenceDAO agenceDAO;

    public MenuAgence(List<Agence> agences) {
        this.agences = agences;
        scanner = new Scanner(System.in);
        agenceDAO = new AgenceDAO();
    }

    public void printMenu() {
        System.out.println(Tools.getBoard("menu agences"));
        Integer choix = getChoice();
        executeChoice(choix);
    }

    private void executeChoice(Integer choix) {
        switch (choix) {
            case 1:
                printAgences();
                break;
            case 2:
                modifAgences();
                break;
            case 3:
                ajouterAgence();
                break;
            case 4:
                supprAgence();
                break;
            case 5:
                visualiserCompteAgence();
                break;
            case 6:
                operationsToCSV();
                break;
            case 7:
                return;
        }
        printMenu();
    }

    private Integer getChoice() {
        System.out.println("Que souhaitez vous effectuer ?");
        System.out.println("choisissez selon la liste suivante: ");
        System.out.println("1 - visualiser les agences");
        System.out.println("2 - modifier une agence");
        System.out.println("3 - ajouter une agence");
        System.out.println("4 - supprimer une agence");
        System.out.println("5 - visualiser les comptes d'une agence");
        System.out.println("6 - enregistrer les operations dans un fichier CSV");
        System.out.println("7 - Quitter");
        return Tools.getIntBetween(0, 7, scanner, "votre choix: ", "erreur de saisie...");
    }

    public void printAgences()  {
        System.out.printf("Il existe actuellement %d agences %s" , agences.size(), System.lineSeparator());
        printAgence(0);
    }

    private void printAgence(Integer n) {
        if (n > agences.size() - 1)
            return;
        System.out.printf("agence n° %d: %s", n + 1, System.lineSeparator());
        System.out.println(agences.get(n));
        printAgence(n + 1);
        return;
    }

    //TODO
    private void modifAgences() {
        printAgences();
        Integer choix = Tools.getIntBetween(0, agences.size(), scanner, "tapez l'id de l'agence: ", "erreur de saisie");
        modifAgence(choix);

        /* MODIFIED
        System.out.println("Tapez '0' pour visualiser les agences,");
        System.out.println("Tapez '1' pour selectionner une agence à modifier");
        Integer choix = Tools.getIntBetween(0, 1, scanner,"votre choix: ", "erreur de saisie");
        if (choix == 0) {
            printAgences();
            modifAgences();
            return;
        }
        choix = Tools.getIntBetween(0, agences.size()-1, scanner, "tapez l'id de l'agence: ", "erreur de saisie");
        */
    }


    private void modifAgence(Integer nbAgence) {
        Agence agence = agences.get(nbAgence - 1); // MODIFIED
        System.out.printf("Voici l'interface de modification concernant l'agence n°%d à l'adresse %s%s", nbAgence, agence.getAdresse(), System.lineSeparator());
        System.out.println("Veuillez saisir les nouvelles données (laisser vide si inchangé)");

        System.out.print("adresse: ");
        agence.setAdresse((String) Tools.modifForms(agence.getAdresse(), String.class));
        System.out.print("code: ");
        agence.setCode((String) Tools.modifForms(agence.getCode(), String.class));
        try {
            agenceDAO.update(agence);
            System.out.println("agence mise à jours...");
        } catch (SQLException | IOException | ClassNotFoundException e) {
            System.out.println("erreur de mise à jour...");
        }
    }

    private void ajouterAgence() {
        System.out.println("veuillez saisir les informations concernant la nouvelle agence: ");

        System.out.print("adresse: ");
        String adresse = scanner.nextLine();
        System.out.print("code: ");
        String code = scanner.nextLine();
        Agence agence = new Agence(0, adresse, code);
        try {
            agenceDAO.create(agence);
            agences.add(agence);
            System.out.println("agence ajoutée..");
        } catch (SQLException | IOException | ClassNotFoundException e) {
            System.out.println("erreur de création....");
        }
    }

    private void supprAgence() {
        // MODIFIED
        printAgences();
        Integer choix = Tools.getIntBetween(0, agences.size(), scanner, "Entrez l'ID de l'agence à supprimer: ", "erreur de saisie");
        System.out.println("êtes vous sûr de vouloir continuer?  [O/N]");
        String option = scanner.nextLine();
        if (option.toUpperCase().equals("O")) { // MODIFIED
            try {
                agenceDAO.delete(agences.get(choix - 1));
                agences.remove(choix);
                System.out.println("agence supprimé...");
            } catch (SQLException | IOException | ClassNotFoundException e) {
                System.out.println("erreur de suppresion...");
            }
        }else {
            System.out.println("Annulation"); // MODIFIED
        }
    }

    private void visualiserCompteAgence() {
        //MODIFIED
        printAgences();
        Integer choix = Tools.getIntBetween(0, agences.size(), scanner, "Entrez l'ID de l'agence à gérer: ", "Erreur de saisie"); // MODIFIED (size())
        MenuCompte menuCompte = new MenuCompte(agences, agences.get(choix - 1), this); // MODIFIED (choix - 1)
        menuCompte.printMenu();
    }

    private void operationsToCSV() {
        List<Operation> operations = Operation.getOperations();
        OperationCSV operationCSV = new OperationCSV();
        String absolutePath = operationCSV.toCSV(operations);
        System.out.println("sauvegarde des operations pour ce compte dans:");
        System.out.println(absolutePath);
    }
}