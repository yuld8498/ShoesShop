package Model;

public class Country {
    private int IdCountry;
    private String countryName;

    public Country() {
    }

    public Country(int idCountry, String countryName) {
        IdCountry = idCountry;
        this.countryName = countryName;
    }

    public int getIdCountry() {
        return IdCountry;
    }

    public void setIdCountry(int idCountry) {
        IdCountry = idCountry;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}
