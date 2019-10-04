package segabank.bo;

public class ComptePayant extends Compte {

    // Chaque opération de retrait et de versement est payante et vaut 5 % du montant de l'opération.
    // Si l'utilisateur retire 100€, on lui retirera donc 100*1.05 = 105€ de son compte, soit le cout de chaque opération
    // Si l'utilisateur dépose 100€, on lui ajoutera donc 100*0.95 = 95€ à son compte, les 5€ correspondant à la "taxe"

    private static final Integer TAUX_COMMISSION = 5;

    public ComptePayant(Integer id, Double solde, Agence agence) {
        super(id, solde, agence);
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
        sb.append("id=").append(id);
        sb.append(", solde=").append(solde);
        sb.append(", date=").append(date);
        sb.append(", agence=").append(agence);
        sb.append('}');
        return sb.toString();
    }
}
