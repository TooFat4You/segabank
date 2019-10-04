package segabank.bo;

public abstract class Compte {

    public Double id;
    public Double solde;

    public Compte(Double id, Double solde) {
        this.id = id;
        this.solde = solde;
    }

    public Double getId() {
        return id;
    }

    public void setId(Double id) {
        this.id = id;
    }

    public Double getSolde() {
        return solde;
    }

    public void setSolde(Double solde) {
        this.solde = solde;
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
