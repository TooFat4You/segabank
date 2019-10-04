package segabank.bo;

public class CompteEpargne extends Compte {
    
    private double tauxInteret;

    public CompteEpargne(Double id, Double solde, Double tauxInteret) {
        super(id, solde);
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

}
