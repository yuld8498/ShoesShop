package DAO;

import Model.TypeOrder;

import java.util.List;

public interface ITypeOrderDao {
    List<TypeOrder> selectAllType();
    TypeOrder selectTypeByID(int ID);
}
