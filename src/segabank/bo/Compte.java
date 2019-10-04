package segabank.bo;

public abstract class Compte {

    public double id;
    public double solde;

    public Compte(double id, double solde) {
        this.id = id;
        this.solde = solde;
    }

    public double getId() {
        return id;
    }

    public void setId(double id) {
        this.id = id;
    }

    public double getSolde() {
        return solde;
    }

    public void setSolde(double solde) {
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
