package segabank.menu;

import org.ietf.jgss.GSSContext;
import segabank.bo.Compte;
import segabank.bo.Operation;
import segabank.csv.OperationCSV;
import segabank.dal.OperationDAO;

import javax.tools.Tool;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class MenuOperation {
    private Scanner scanner;
    private Compte compte;
    private List<Operation> operations;
    private MenuCompte menuCompte;

    public MenuOperation(Compte compte, MenuCompte menuCompte) {
        this.compte = compte;
        this.menuCompte = menuCompte;
        operations = compte.getOperations();
        scanner = new Scanner(System.in);
    }

    public void printMenu() {
        System.out.println(Tools.getBoard("menu operation"));
        System.out.printf("vous visualiser les operations du compte %d de l'agence à l'adresse %s%s", compte.getId(), compte.getAgence().getAdresse(), System.lineSeparator());
        makeOption();
    }

    private void makeOption() {
        Integer choix = getChoice();
        executeChoice(choix);
    }

    private Integer getChoice() {
        System.out.println("Que souhaitez vous faire ?");
        System.out.println("1 - visualiser les operations");
        System.out.println("2 - modifier une operation");
        System.out.println("3 - ajouter une operation");
        System.out.println("4 - supprimer une operation");
        System.out.println("5 - enregistrer au format CSV");
        System.out.println("6 - Quitter le menu operation");
        return Tools.getIntBetween(0, 6, scanner, "votre choix: ", "erreur de saisie...");
    }

    private void executeChoice(Integer choice) {
        switch (choice) {
            case 1:
                printOperations();
                break;
            case 2:
                modifOperation();
                break;
            case 3:
                ajouterOperation();
                break;
            case 4:
                supprOperation();
                break;
            case 5:
                saveCSV();
                break;
            case 6:
                return;
        }
        makeOption();
    }

    private void printOperations() {
        printOperation(0);
    }

    private void printOperation(Integer nb) {
        if (nb == operations.size())
            return;
        System.out.println(operations.get(nb));
        printOperation(nb + 1);
    }

    private void modifOperation() {
        printOperations();
        Operation operation = null;
        boolean typed = false;

        while (operation == null) {
            if (typed) {
                System.out.println("erreur operation, compte non trouvé...");
            }
            Integer id = Tools.getIntSuperZero(scanner, "l'id de l'operation: ", "erreur de saisie");
            operation = Operation.getOperationById(operations, id);
            typed = true;
        }

        System.out.printf("Voici l'interface de modification concernant l'operation n° %d%s", operation.getId(), System.lineSeparator());
        System.out.println("Veuillez saisir les nouvelles données (laisser vide si inchangé)");

        System.out.println("veuillez selectionne le type d'operation:");
        System.out.println("1 - versement");
        System.out.println("2 - retrait");
        Integer n = operation.getTypeOperation().ordinal();
        Integer type = Tools.getValueBetweenActual(n, 1, 2, scanner, "votre choix: ", "erreur de saisie");
        operation.setTypeOperation(Operation.TypeOperation.values()[type - 1]);

        System.out.println("date de l'operation:");
        operation.setDate(Tools.modifDate(operation.getDate()));
        operation.setMontant(Tools.getDoubleSuperZeroOrActual(operation.getMontant(), scanner, "montant: ", "erreur de saisie"));

        System.out.println("souhaitez vous modifier le compte auteur de l'operation [O/n]");
        String option = scanner.nextLine();
        if (option.equals("O")) {
            Compte compte = null;
            List<Compte> comptes = operation.getCompte().getAgence().getComptes();
            while (compte == null) {
                menuCompte.printComptes();
                Integer idCompte = Tools.getIntSuperZero(scanner, "l'id du compte: ", "erreur de saisie");
                compte = Compte.getCompteById(comptes, idCompte);
            }
            operation.setIdCompte(compte.getId());
            operation.getCompte().getOperations().remove(operation);
            compte.getOperations().add(operation);
            operation.setCompte(compte);
        }
        OperationDAO operationDAO = new OperationDAO();
        try {
            operationDAO.update(operation);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void ajouterOperation() {
        Double montant = Tools.getDoubleSuperZero(scanner, "montant: ", "erreur de saisie");
        System.out.println("type d'operation: ");
        System.out.println("1 - versement");
        System.out.println("2 - retrait");
        Integer option = Tools.getIntBetween(1, 2, scanner, "votre choix: ", "erreur de saisie");
        Operation.TypeOperation typeOperation = Operation.TypeOperation.values()[option - 1];

        Date date = new Date();
        System.out.println("souhaitez utiliser la date actuelle ou enregistrer une autre ?");
        System.out.println("utiliser la date actuelle [O/n]");
        String optionStr = scanner.nextLine();
        if (optionStr.equals("O")) {
            System.out.println("date (laissez vide pour conserver la valeur actuelle):");
            date = Tools.modifDate(date);
        }

        Compte compte = this.compte;
        System.out.println("souhaitez utiliser le compte actuellement selectionner ou en choisir un autre ?");
        System.out.println("utiliser le compte actuel [O/n]");
        optionStr = scanner.nextLine();
        if (optionStr.equals("O")) {
            compte = null;
            List<Compte> comptes = this.compte.getAgence().getComptes();
            while (compte == null) {
                menuCompte.printComptes();
                Integer idCompte = Tools.getIntSuperZero(scanner, "l'id du compte: ", "erreur de saisie");
                compte = Compte.getCompteById(comptes, idCompte);
            }
        }

        Operation operation = new Operation(0, montant, date, typeOperation, compte.getId());
        OperationDAO operationDAO = new OperationDAO();
        try {
            operationDAO.create(operation);
            compte.getOperations().add(operation);
            operation.setCompte(compte);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void supprOperation() {
        System.out.println("voici la liste des operations");
        Operation operation = null;
        while (operation == null) {
            printOperations();
            System.out.println("tapez l'id de l'operation que vous souhaitez supprimer");
            Integer idOperation = Tools.getIntSuperZero(scanner, "l'id de l'operation: ", "erreur de saisie");
            operation = Operation.getOperationById(operations, idOperation);
        }

        OperationDAO operationDAO = new OperationDAO();
        try {
            operationDAO.delete(operation);
            operation.getCompte().getOperations().remove(operation);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void saveCSV() {
        OperationCSV operationCSV = new OperationCSV();
        String absolutePath = operationCSV.toCSV(operations);
        System.out.println("sauvegarde des operations pour ce compte dans:");
        System.out.println(absolutePath);
    }
}
