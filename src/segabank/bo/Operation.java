package segabank.bo;

import java.util.Date;
import java.util.List;

public class Operation {

    private static List<Operation> operations;

    public enum TypeOperation {
        versement,
        retrait;

        public String getLabel() {
            switch (this) {
                case versement:
                    return "versement";
                case retrait:
                    return "retrait";
            }
            return null;
        }
    }

    private Integer id;
    private Double montant;
    private Date date;
    private TypeOperation typeOperation;
    private Integer idCompte;
    private Compte compte;
    
    public Operation(Integer id, Double montant, Date date, TypeOperation typeOperation, Integer idCompte) {
        this.id = id;
        this.montant = montant;
        this.date = date;
        this.typeOperation = typeOperation;
        this.idCompte = idCompte;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public TypeOperation getTypeOperation() {
        return typeOperation;
    }

    public Integer getIdCompte() {
        return idCompte;
    }

    public void setIdCompte(Integer idCompte) {
        this.idCompte = idCompte;
    }

    public void setTypeOperation(TypeOperation typeOperation) {
        this.typeOperation = typeOperation;
    }

    public Compte getCompte() {
        return compte;
    }

    public void setCompte(Compte compte) {
        this.compte = compte;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Operation{");
        sb.append("id=").append(id);
        sb.append(", montant=").append(montant);
        sb.append(", date=").append(date);
        sb.append(", typeOperation=").append(typeOperation);
        sb.append(", idCompte=").append(idCompte);
        sb.append('}');
        return sb.toString();
    }

    public static Operation getOperationById(List<Operation> operations, Integer id) {
        for (Operation operation : operations)
            if (operation.getId() == id)
                return operation;
        return null;
    }

    public static List<Operation> getOperations() {
        return operations;
    }

    public static void setOperations(List<Operation> operations) {
        Operation.operations = operations;
    }
}
