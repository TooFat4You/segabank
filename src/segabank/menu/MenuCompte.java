package segabank.menu;

import segabank.bo.*;
import segabank.dal.AgenceDAO;
import segabank.dal.CompteDAO;
import segabank.dal.OperationDAO;

import javax.tools.Tool;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class MenuCompte {
    private List<Agence> agences;
    private Agence agence;
    private Scanner scanner;
    private MenuAgence menuAgence;
    private CompteDAO compteDAO;

    MenuCompte(List<Agence> agences, Agence agence, MenuAgence menuAgence) {
        this.agences = agences;
        this.agence = agence;
        this.menuAgence = menuAgence;
        scanner = new Scanner(System.in);
        compteDAO = new CompteDAO();
    }

    public void printMenu() {
        System.out.println(Tools.getBoard("menu compte"));
        System.out.printf("vous êtes dans l'agence à l'adresse: %s%s", agence.getAdresse(), System.lineSeparator());
        Integer choix = getChoice();
        executeChoice(choix);
    }

    private Integer getChoice() {
        System.out.println("que souhaitez vous faire ?");
        System.out.println("1 - Visualiser les comptes");
        System.out.println("2 - Ajouter un compte");
        System.out.println("3 - Modifier un compte");
        System.out.println("4 - Supprimer un compte");
        System.out.println("5 - effectuer une opération");
        System.out.println("6 - visualiser les opérations du compte");
        System.out.println("7 - Quitter le menu compte");

        return Tools.getIntBetween(1, 7, scanner, "votre choix: ", "erreur de saisie"); // MODIFIED b 6 -> 7
    }

    private void executeChoice(Integer choice) {
        switch (choice) {
            case 1:
                printComptes();
                break;
            case 2:
                ajouterCompte();
                break;
            case 3:
                modifComptes();
                break;
            case 4:
                supprCompte();
                break;
            case 5:
                effectuerOperation();
                break;
            case 6:
                printMenuOperation();
                break;
            case 7:
                return;
        }

        printMenu();
    }

    public void printComptes() {
        System.out.println("souhaiteriez visualiser un type de compte en particuler ?");
        System.out.println("1 - compte epargne");
        System.out.println("2 - compte payant");
        System.out.println("3 - compte simple");
        System.out.println("4 - tout les comptes");

        List<Compte> comptes = new ArrayList<>();
        Integer choix = Tools.getIntBetween(1, 4, scanner, "votre choix: ", "erreur de saisie");
        switch (choix) {
            case 1:
                comptes.addAll(agence.getCompteEpargnes());
                break;
            case 2:
                comptes.addAll(agence.getComptePayants());
                break;
            case 3:
                comptes.addAll(agence.getCompteSimples());
                break;
            case 4:
                comptes.addAll(agence.getComptes());
        }
        printCompte(comptes, 0);
    }

    private void printCompte(List<Compte> comptes, Integer id) {
        for (Compte compte : comptes) {
            System.out.println(compte);
        }
    }

    //TODO
    private void modifComptes() {
        // MODIFIED
        printComptes();
        Integer choix = 0;

        List<Compte> comptes = agence.getComptes();
        Compte compte = null;
        boolean notFound = false;
        while (compte == null) {
            if (notFound) {
                System.out.printf("compte non trouvé pour l'id %d%s", choix, System.lineSeparator());
            }
            choix = Tools.getIntSuperZero(scanner, "tapez l'id du compte à modifier: ", "erreur de saisie");
            compte = Compte.getCompteById(comptes, choix);
            notFound = true;
        }

        modifCompte(compte);
    }

    private void modifCompte(Compte compte) {
        System.out.println("--------------------------------------------------------------");
        System.out.printf("Voici l'interface de modification concernant le compte n° %d%s", compte.getId(), System.lineSeparator());
        System.out.println("Veuillez saisir les nouvelles données (laisser vide si inchangé)");

        System.out.print("date de création: ");
        compte.setDateCreation((Date) Tools.modifForms(compte.getDateCreation(), Date.class));
        System.out.print("solde: ");
        compte.setSolde((Double) Tools.modifForms(compte.getSolde(), Double.class));


        System.out.println("Il vous est possible de modifier l'agence du compte");
        System.out.println("Souhaiteriez vous visualiser la liste des agences ? [O/n]");
        String choix = scanner.nextLine();
        if (choix.equals("O")) {
            menuAgence.printAgences();
        }


        boolean found = false;
        while (!found) {
            Integer idAgence = Tools.getIntSuperZeroOrBlanck(compte.getIdAgence(), scanner, "l'id de l'agence: ", "erreur de saisie");
            for (Agence agence : agences) {
                if (agence.getId() == idAgence) {
                    compte.setIdAgence(idAgence);
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("l'agence correspondant à l'id n'a pas pu être trouvé...");
            }
        }
        AgenceDAO agenceDAO = new AgenceDAO();
        try {
            compteDAO.update(compte);
        } catch (SQLException | IOException | ClassNotFoundException e) {
            System.out.println("erreur lors de la mise à jours...");
        }
        agenceDAO.updateRelations(agences);
    }

    private void ajouterCompte() {
        System.out.println("Choisissez le type du nouveau compte");
        System.out.println("1 - compte epargne");
        System.out.println("2 - compte payant");
        System.out.println("3 - compte simple");

        Integer choix = Tools.getIntBetween(1, 3, scanner, "votre choix: ", "erreur de saisie");

        System.out.println("--création du compte--");
        Double solde = Tools.getDoubleSuperZero(scanner, "solde: ", "erreur de saisie");

        System.out.println("La date de création sera enregistré à la date d'aujourd'hui");
        System.out.println("Toutefois, il vous est possible d'enregistrer une date personnalisé");
        System.out.println("Souhaitez vous entrer une Date ? [O/n]");
        String option = scanner.nextLine();
        Date dateCreation = new Date();
        if (option.equals("O")) {
            System.out.println("Entrez les valeurs souhaiter (laisser vide conservera la valeurs d'aujourd'hui)");
            Tools.modifDate(dateCreation);
        }
        Compte compte = null;
        switch (choix) {
            case 1:
                Double tauxInteret = Tools.getDoubleSuperZero(scanner, "taux interet: ", "erreur de saisie");
                compte = new CompteEpargne(0, solde, dateCreation, agence.getId(), tauxInteret);
                agence.getCompteEpargnes().add((CompteEpargne) compte);
                break;
            case 2:
                compte = new ComptePayant(0, solde, dateCreation, agence.getId());
                agence.getComptePayants().add((ComptePayant) compte);
                break;
            case 3:
                Double decouvert = Tools.getDoubleSuperZero(scanner, "decouver: ", "erreur de saisie");
                compte = new CompteSimple(0, solde, dateCreation, agence.getId(), decouvert);
                agence.getCompteSimples().add((CompteSimple) compte);
        }
        compte.setAgence(agence);
        try {
            compteDAO.create(compte);
        } catch (SQLException | IOException | ClassNotFoundException e) {
            System.out.println("erreur lors de la création du compte...");
        }
    }

    private void supprCompte() { // MODIFIED
        printComptes();

        Compte compte = null;
        while (compte == null) {
            Integer id = Tools.getIntSuperZero(scanner, "Entrez l'id du compte à supprimer: ", "erreur de saisie");
            compte = Compte.getCompteById(agence.getComptes(), id);
        }

        System.out.println("êtes vous sûr de vouloir continuer?  [O/N]");
        String option = scanner.nextLine();
        if (option.toUpperCase().equals("O")) {
            try {
                compteDAO.delete(compte);
                System.out.println("compte supprimé...");
                removeCompteAgencesList(compte);
            } catch (SQLException | IOException | ClassNotFoundException e) {
                System.out.println("erreur de suppresion...");
            }
        }else{
            System.out.println("Annulation");
        }
    }

    private void removeCompteAgencesList(Compte compte) {
        switch (compte.getType()) {
            case payant:
                agence.getComptePayants().remove(compte);
            case simple:
                agence.getCompteSimples().remove(compte);
            case epargne:
                agence.getCompteEpargnes().remove(compte);
        }
    }

    private void effectuerOperation() {
        Compte compte = null;
        boolean typed = false;
        while (compte == null) {
            printComptes();
            if (typed) {
                System.out.println("erreur compte non trouvé...");
            }
            Integer id = Tools.getIntSuperZero(scanner, "l'id du compte: ", "erreur de saisie");
            compte = Compte.getCompteById(agence.getComptes(), id);
            typed = true;
        }


        System.out.println("quel opération souhaitez vous effectuer ?");
        System.out.println("1 - retrait");
        System.out.println("2 - virement");
        Integer option = Tools.getIntBetween(1, 2, scanner, "votre choix: ", "erreur de saisie");

        Double montant = Tools.getDoubleSuperZero(scanner, "motant de l'opération: ", "erreur de saisie");
        OperationDAO operationDAO = new OperationDAO();

        if (option == 2) {
            try {
                Operation operation = compte.versement(montant);
                operationDAO.create(operation);
                operation.setCompte(compte);
                compte.getOperations().add(operation);
                compteDAO.update(compte);
            } catch (IOException | ClassNotFoundException | SQLException e) {
                System.out.println("erreur lors du versement...");
            }
        } else {
            try {
                Operation operation = compte.retrait(montant);
                if (operation == null) {
                    System.out.println("le montant de l'operation est trop élevée...");
                } else {
                    operationDAO.create(operation);
                    compteDAO.update(compte);
                }
            } catch (IOException | SQLException | ClassNotFoundException e) {
                System.out.println("erreur lors du retrait...");
            }
        }
    }

    private void printMenuOperation() {
        List<Compte> comptes = agence.getComptes();
        printCompte(comptes, 0);
        Compte compte = null;
        while (compte == null) {
            Integer id = Tools.getIntSuperZero(scanner, "votre choix: ", "erreur de saisie");
            compte = Compte.getCompteById(comptes, id);
        }

        MenuOperation menuOperation = new MenuOperation(compte, this);
        menuOperation.printMenu();
    }
}
