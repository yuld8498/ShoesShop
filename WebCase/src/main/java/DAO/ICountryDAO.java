package DAO;

import Model.Country;

import java.util.List;

public interface ICountryDAO {
    public List<Country> selectAllCountry();
    public Country selectCountryByID(int ID);
}
