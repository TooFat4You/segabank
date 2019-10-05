package segabank.bo;

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
        sb.append(", date=").append(date);
        sb.append(", agence=").append(5);
        sb.append('}');
        return sb.toString();
    }
}
