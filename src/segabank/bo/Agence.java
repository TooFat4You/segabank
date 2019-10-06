package segabank.bo;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Agence {

    private static List<Agence> agences;

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

    public List<CompteEpargne> getCompteEpargnes() {
        return compteEpargnes;
    }

    public List<ComptePayant> getComptePayants() {
        return comptePayants;
    }

    public List<CompteSimple> getCompteSimples() {
        return compteSimples;
    }

    public List<Compte> getComptes() {
        List<Compte> comptes = new ArrayList<>();
        comptes.addAll(compteEpargnes);
        comptes.addAll(comptePayants);
        comptes.addAll(compteSimples);
        return comptes;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("id: ");
        sb.append(id).append(System.lineSeparator());
        sb.append("code: ").append(code).append(System.lineSeparator());
        sb.append("adresse: ").append(adresse).append(System.lineSeparator());
        sb.append("nombre de compteEpargnes: ").append(compteEpargnes.size()).append(System.lineSeparator());
        sb.append("nombre de comptePayants: ").append(comptePayants.size()).append(System.lineSeparator());
        sb.append("nombre de compteSimples: ").append(compteSimples.size()).append(System.lineSeparator());
        sb.append("--------------------");
        return sb.toString();
    }

    public static void setAgences(List<Agence> agences) {
        Agence.agences = agences;
    }

    public static List<Agence> getAgences() {
        return agences;
    }
}
