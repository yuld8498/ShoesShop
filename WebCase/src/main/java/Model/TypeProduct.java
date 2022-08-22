package Model;

public class TypeProduct {
    private int typeID;
    private String typeName;

    public TypeProduct() {
    }

    public TypeProduct(String typeName) {
        this.typeName = typeName;
    }

    public TypeProduct(int typeID, String typeName) {
        this.typeID = typeID;
        this.typeName = typeName;
    }

    public int getTypeID() {
        return typeID;
    }

    public void setTypeID(int typeID) {
        this.typeID = typeID;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
