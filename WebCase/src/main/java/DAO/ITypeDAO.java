package DAO;

import Model.TypeProduct;

import java.util.List;
import java.util.Map;

public interface ITypeDAO {
    public List<TypeProduct> selectAllType();
    public TypeProduct selectTypeByID(int id);
}
