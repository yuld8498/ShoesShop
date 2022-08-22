package DAO;

import Model.Country;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CountryDAO implements  ICountryDAO{
    private String jdbcURL = "jdbc:mysql://localhost:3306/quanlisanpham?useSSL=false";
    private String jdbcUsername = "root";
    private String jdbcPassword = "123456";
    private static final String selectAllContry = "select * from city;";
    private static final String selectContryByID = "select * from city where cityID = ?;";

    protected Connection getConnection(){
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL,jdbcUsername,jdbcPassword);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
    @Override
    public List<Country> selectAllCountry() {
        List<Country> countryList = new ArrayList<>();
        try(Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(selectAllContry)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                int countryID = resultSet.getInt("cityID");
                String countryName =resultSet.getString("cityName");
                countryList.add(new Country(countryID,countryName));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return countryList;
    }

    @Override
    public Country selectCountryByID(int ID) {
        Country country = null;
        try(Connection connection = getConnection(); PreparedStatement preparedStatement= connection.prepareStatement(selectContryByID)) {
            preparedStatement.setInt(1, ID);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                int countryID = rs.getInt("cityID");
                String countryName =rs.getString("cityName");
                country = new Country(countryID,countryName);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return country;
    }
}
