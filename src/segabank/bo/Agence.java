package segabank.bo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Agence {

    private Integer id;
    private String code;
    private String adresse;
    private List<CompteEpargne> compteEpargnes;
    private List<ComptePayant> comptePayants;
    private List<CompteSimple> compteSimples;

    public Agence(Integer id, String code, String adresse) {
        this.id = id;
        this.code = code;
        this.adresse = adresse;
        compteEpargnes = new ArrayList<>();
        comptePayants = new ArrayList<>();
        compteSimples = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Agence{");
        sb.append("id=").append(id);
        sb.append(", code='").append(code).append('\'');
        sb.append(", adresse='").append(adresse).append('\'');
        sb.append(", compteEpargnes=").append(compteEpargnes);
        sb.append(", comptePayants=").append(comptePayants);
        sb.append(", compteSimples=").append(compteSimples);
        sb.append('}');
        return sb.toString();
    }
}
