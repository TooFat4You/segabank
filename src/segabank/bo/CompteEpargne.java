package segabank.bo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

public class CompteEpargne extends Compte {
    
    private double tauxInteret;

    public CompteEpargne(Integer id, Double solde, Date date, Integer idAgence, Double tauxInteret) {
        super(id, solde, date, idAgence);
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
    public Operation versement(Double montant) throws SQLException, IOException, ClassNotFoundException {
        solde += montant;
        return saveOperation(montant, Operation.TypeOperation.versement);
    }

    @Override
    public Operation retrait(Double montant) throws SQLException, IOException, ClassNotFoundException {
        if (montant - solde < 0)
            return null;
        solde -= montant;
        return saveOperation(montant, Operation.TypeOperation.retrait);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CompteEpargne").append(System.lineSeparator());
        sb.append("tauxInteret: ").append(tauxInteret).append(System.lineSeparator());
        sb.append("id: ").append(id).append(System.lineSeparator());
        sb.append("solde: ").append(solde).append(System.lineSeparator());
        sb.append("date: ").append(dateCreation).append(System.lineSeparator());
        sb.append("agence: ").append(5).append(System.lineSeparator());
        sb.append("-------------------").append(System.lineSeparator());
        return sb.toString();
    }
}
