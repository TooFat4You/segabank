package segabank.bo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

public class CompteSimple extends Compte {
    private Double decouvert;

    public CompteSimple(Integer id, Double solde, Date date, Integer idAgence, Double decouvert) {
        super(id, solde, date, idAgence);
        this.decouvert = decouvert;
    }

    @Override
    public Etat getType() {
        return Etat.simple;
    }

    @Override
    public Operation versement(Double montant) throws SQLException, IOException, ClassNotFoundException {
        solde += montant;
        return saveOperation(montant, Operation.TypeOperation.versement);
    }

    @Override
    public Operation retrait(Double montant) throws SQLException, IOException, ClassNotFoundException {
        Double decouvert = this.decouvert;
        if (decouvert > 0) {
            decouvert = decouvert - decouvert * 2;
        }
        if (solde - montant < decouvert)
            return null;
        solde -= montant;
        return saveOperation(montant, Operation.TypeOperation.retrait);
    }

    public void setDecouvert(Double decouvert) {
        this.decouvert = decouvert;
    }

    public double getDecouvert() {
        return decouvert;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CompteSimple{");
        sb.append("decouvert=").append(decouvert);
        sb.append(", id=").append(id);
        sb.append(", solde=").append(solde);
        sb.append(", date=").append(dateCreation);
        sb.append(", agence=").append(5);
        sb.append('}');
        return sb.toString();
    }
}
