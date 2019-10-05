package segabank.bo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class Compte {

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
    protected Date date;
    protected Integer idAgence;
    protected Agence agence;
    protected List<Operation> operations;

    public Compte(Integer id, Double solde, Date date, Integer idAgence) {
        this.id = id;
        this.solde = solde;
        this.date = date;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Compte ");
        sb.append(id);
        sb.append(" a un solde de : ").append(solde);
        sb.append('€');
        return sb.toString();
    }
}
