package segabank.bo;

public class Agence {

    private int id;
    private String code;
    private String adresse;

    public Agence(int id, String code, String adresse) {
        this.id = id;
        this.code = code;
        this.adresse = adresse;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
}
