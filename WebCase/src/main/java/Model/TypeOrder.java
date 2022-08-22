package Model;

public class TypeOrder {
    int TypeOrderID;
    String typeOrderName;

    public TypeOrder() {
    }

    public TypeOrder(int typeOrderID, String typeOrderName) {
        TypeOrderID = typeOrderID;
        this.typeOrderName = typeOrderName;
    }

    public int getTypeOrderID() {
        return TypeOrderID;
    }

    public void setTypeOrderID(int typeOrderID) {
        TypeOrderID = typeOrderID;
    }

    public String getTypeOrderName() {
        return typeOrderName;
    }

    public void setTypeOrderName(String typeOrderName) {
        this.typeOrderName = typeOrderName;
    }
}
