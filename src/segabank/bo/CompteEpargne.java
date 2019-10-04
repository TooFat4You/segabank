package segabank.bo;

public class CompteEpargne extends Compte {



    private double tauxInteret;

    public CompteEpargne(double id, double solde, double tauxInteret) {
        super(id, solde);
        this.tauxInteret = tauxInteret;
    }

    public double getTauxInteret() {
        return tauxInteret;
    }

    public void setTauxInteret(double tauxInteret) {
        this.tauxInteret = tauxInteret;
    }

    public void calculInteret(){
        solde = solde * ((tauxInteret/100) + 1);
    }

}
