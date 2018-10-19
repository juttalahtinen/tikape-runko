package tikape.runko.domain;

public class Vastaus {
    Integer id;
    String vastausteksti;
    Boolean oikein;
    Kysymys kysymys;
    Integer kysymysId;

    public Vastaus(Integer id, String vastausteksti, Boolean oikein) {
        this.id = id;
        this.vastausteksti = vastausteksti;
        this.oikein = oikein;
    }

    public Vastaus(String vastausteksti, Boolean oikein, Integer kysymysId) {
        this.vastausteksti = vastausteksti;
        this.oikein = oikein;
        this.kysymysId = kysymys.id;
    }

    public Integer getId() {
        return id;
    }

    public String getVastausteksti() {
        return vastausteksti;
    }

    public Boolean getOikein() {
        return oikein;
    }
    public Integer getKysymysId() {
        return kysymys.id;
    }
}
