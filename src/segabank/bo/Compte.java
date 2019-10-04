package segabank.bo;

import java.util.Date;

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
    protected Agence agence;

    public Compte(Integer id, Double solde, Agence agence) {
        this.id = id;
        this.solde = solde;
        this.agence = agence;
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

    public Agence getAgence() {
        return agence;
    }

    public void setAgence(Agence agence) {
        this.agence = agence;
    }

    public abstract Etat getType();

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Compte ");
        sb.append(id);
        sb.append(" a un solde de : ").append(solde);
        sb.append('€');
        return sb.toString();
    }
}
