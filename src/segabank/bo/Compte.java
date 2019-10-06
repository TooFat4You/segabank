package segabank.bo;

import segabank.dal.CompteDAO;
import segabank.dal.OperationDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class Compte {

    private static List<Compte> comptes;

    public enum Etat {
        epargne,
        payant,
        simple;

        public String getLabel() {
            switch (this) {
                case epargne:
                    return "epargne";
                case payant:
                    return "payant";
                default:
                    return "simple";
            }
        }
    }

    protected Integer id;
    protected Double solde;
    protected Date dateCreation;
    protected Integer idAgence;
    protected Agence agence;
    protected List<Operation> operations;

    public Compte(Integer id, Double solde, Date dateCreation, Integer idAgence) {
        this.id = id;
        this.solde = solde;
        this.dateCreation = dateCreation;
        this.idAgence = idAgence;
        operations = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getSolde() {
        return solde;
    }

    public void setSolde(Double solde) {
        this.solde = solde;
    }

    public Integer getIdAgence() {
        return idAgence;
    }

    public void setIdAgence(Integer idAgence) {
        this.idAgence = idAgence;
    }

    public abstract Etat getType();

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date date) {
        this.dateCreation = date;
    }

    public Agence getAgence() {
        return agence;
    }

    public void setAgence(Agence agence) {
        this.agence = agence;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public abstract Operation versement(Double montant) throws SQLException, IOException, ClassNotFoundException;

    public abstract Operation retrait(Double montant) throws SQLException, IOException, ClassNotFoundException;

    protected Operation saveOperation(Double montant, Operation.TypeOperation typeOperation) throws SQLException, IOException, ClassNotFoundException {
        Operation operation = new Operation(0, montant, null, typeOperation, id);
        operations.add(operation);
        operation.setCompte(this);
        return operation;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Compte ");
        sb.append(id);
        sb.append(" a un solde de : ").append(solde);
        sb.append('€');
        return sb.toString();
    }

    public static Compte getCompteById(List<Compte> comptes, Integer id) {
        for (Compte cpt : comptes) {
            if (cpt.getId() == id)
                return cpt;
        }
        return null;
    }

    public static List<Compte> getComptes() {
        return comptes;
    }

    public static void setComptes(List<Compte> comptes) {
        Compte.comptes = comptes;
    }
}
