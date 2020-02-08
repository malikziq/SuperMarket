package com.example.supermarket.models.pojo;

public class TypeSpinnerItem {

    private String type;
    private int typeImage;

    public TypeSpinnerItem() {
    }

    public TypeSpinnerItem(String type, int typeImage) {
        this.type = type;
        this.typeImage = typeImage;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTypeImage() {
        return typeImage;
    }

    public void setTypeImage(int typeImage) {
        this.typeImage = typeImage;
    }

    @Override
    public String toString() {
        return "TypeSpinnerItem{" +
                "type='" + type + '\'' +
                ", typeImage=" + typeImage +
                '}';
    }
}
