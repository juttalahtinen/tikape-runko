package tikape.runko.domain;

public class Kysymys {
    Integer id;
    String kurssi;
    String aihe;
    String kysymysteksti;

    public Kysymys(String kurssi, String aihe, String kysymysteksti) {
        this.kurssi = kurssi;
        this.aihe = aihe;
        this.kysymysteksti = kysymysteksti;
    }
    public Kysymys(Integer id, String kurssi, String aihe, String kysymysteksti) {
        this.id = id;
        this.kurssi = kurssi;
        this.aihe = aihe;
        this.kysymysteksti = kysymysteksti;
    }

    public String getKurssi() {
        return kurssi;
    }

    public String getAihe() {
        return aihe;
    }

    public String getKysymysteksti() {
        return kysymysteksti;
    }
    public Integer getId() {
        return id;
    }
    
}

