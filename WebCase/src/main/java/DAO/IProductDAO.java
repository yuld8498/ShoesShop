package DAO;

import Model.Product;
import Model.User;

import java.util.List;

public interface IProductDAO {
    public void insertProduct(Product product);
    public Product selectProductByID(int id);
    public List<Product> selectAllProduct();
    public boolean deleteProductByID(int ID);
    public boolean updateQuailityProduct(int ID, int quaility);
    public boolean updatePriceOfProduct(int ID, double price);
    public boolean updateProductByID(Product product);
    public List<Product> selectProductPagging(int offset, int noOfRecord);
    List<Product> selectProductByType(int typeID);
}
