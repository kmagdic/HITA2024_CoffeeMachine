package t5_marin.coffeemachine;

public class Location {
    private int id;
    private String drzava;
    private String zupanija;
    private String postanskiBroj;  // Changed to String
    private String adresa;

    // Updated constructor to accept String for postanskiBroj
    public Location(int id, String drzava, String zupanija, String postanskiBroj, String adresa) {
        this.id = id;
        this.drzava = drzava;
        this.zupanija = zupanija;
        this.postanskiBroj = postanskiBroj;  // Now accepts String
        this.adresa = adresa;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDrzava() {
        return drzava;
    }

    public void setDrzava(String drzava) {
        this.drzava = drzava;
    }

    public String getZupanija() {
        return zupanija;
    }

    public void setZupanija(String zupanija) {
        this.zupanija = zupanija;
    }

    public String getPostanskiBroj() {
        return postanskiBroj;
    }

    // Updated setter to accept String for postanskiBroj
    public void setPostanskiBroj(String postanskiBroj) {
        this.postanskiBroj = postanskiBroj;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }
}
