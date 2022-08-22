package DAO;

import Model.TypeProduct;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TypeDAO implements ITypeDAO{
    private String jdbcURL = "jdbc:mysql://localhost:3306/quanlisanpham?useSSL=false";
    private String jdbcUsername = "root";
    private String jdbcPassword = "123456";
    private static final String selectAllType = "select * from typeProduct;";
    private static final String selectTypeByID = "select typeName from typeProduct where typeID = ?;";

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
    public List<TypeProduct> selectAllType() {
        List<TypeProduct> listType = new ArrayList<>();
        try(Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(selectAllType)){
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                int typeID = rs.getInt("typeID");
                String typeName = rs.getString("typeName");
                listType.add(new TypeProduct(typeID,typeName));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listType;
    }

    @Override
    public TypeProduct selectTypeByID(int id) {
        TypeProduct typeProduct = null;
        try(Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(selectTypeByID)){
            preparedStatement.setInt(1,id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                int typeID = rs.getInt("typeID");
                String typeName = rs.getString("typeName");
                typeProduct = new TypeProduct(typeID, typeName);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return typeProduct;
    }
}
