package segabank.bo;

public class ComptePayant extends Compte {

    // Chaque opération de retrait et de versement est payante et vaut 5 % du montant de l'opération.
    // Si l'utilisateur retire 100€, on lui retirera donc 100*1.05 = 105€ de son compte, soit le cout de chaque opération
    // Si l'utilisateur dépose 100€, on lui ajoutera donc 100*0.95 = 95€ à son compte, les 5€ correspondant à la "taxe"

    private static final Integer TAUX_OPERATION = 5;

    public ComptePayant(Double id, Double solde) {
        super(id, solde);
    }

    public Integer getTauxOperation() {
        return TAUX_OPERATION;
    }

}
