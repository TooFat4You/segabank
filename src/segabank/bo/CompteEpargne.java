package segabank.bo;

public class CompteEpargne extends Compte {
    
    private double tauxInteret;

    public CompteEpargne(Integer id, Double solde, Agence agence, Double tauxInteret) {
        super(id, solde, agence);
        this.tauxInteret = tauxInteret;
    }

    public Double getTauxInteret() {
        return tauxInteret;
    }

    public void setTauxInteret(Double tauxInteret) {
        this.tauxInteret = tauxInteret;
    }

    public void calculInteret(){
        solde = solde * ((tauxInteret/100) + 1);
    }

    @Override
    public Etat getType() {
        return Etat.epargne;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CompteEpargne{");
        sb.append("tauxInteret=").append(tauxInteret);
        sb.append(", id=").append(id);
        sb.append(", solde=").append(solde);
        sb.append(", date=").append(date);
        sb.append(", agence=").append(agence);
        sb.append('}');
        return sb.toString();
    }
}
