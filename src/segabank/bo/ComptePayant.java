package segabank.bo;

import java.util.Date;

public class ComptePayant extends Compte {

    // Chaque opération de retrait et de versement est payante et vaut 5 % du montant de l'opération.
    // Si l'utilisateur retire 100€, on lui retirera donc 100*1.05 = 105€ de son compte, soit le cout de chaque opération
    // Si l'utilisateur dépose 100€, on lui ajoutera donc 100*0.95 = 95€ à son compte, les 5€ correspondant à la "taxe"

    private static final Integer TAUX_COMMISSION = 5;

    public ComptePayant(Integer id, Double solde, Date date, Integer idAgence) {
        super(id, solde, date, idAgence);
    }

    @Override
    public Etat getType() {
        return Etat.payant;
    }

    public Integer getTauxOperation() {
        return TAUX_COMMISSION;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ComptePayant{");
        sb.append('}');
        return sb.toString();
    }
}
