package sample.Model;
/**
 * @author 马杰生
 * @date 2018-7-30
 * 模型类：药品信息
 */
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 */
public class Drug {
    private final StringProperty drugID;
    private final StringProperty drugName;
    private final StringProperty maxPrice;
    //private final StringProperty price;
    private final StringProperty unit;
    private final StringProperty payLevel;
    private final StringProperty hospitalLevel;

    /**
     * 构造器
     * @param drugID 药品编码
     * @param drugName 药品名称
     * @param maxPrice 最大限价
     * @param unit 单位
     * @param payLevel 药品付费等级
     * @param hospitalLevel 医院等级
     */
    public Drug(String drugID, String drugName, String maxPrice, String unit, String payLevel, String hospitalLevel) {
        this.drugID = new SimpleStringProperty(drugID);
        this.drugName = new SimpleStringProperty(drugName);
        this.maxPrice = new SimpleStringProperty(maxPrice);
        this.unit = new SimpleStringProperty( unit);
        this.payLevel = new SimpleStringProperty( payLevel);
        this.hospitalLevel = new SimpleStringProperty( hospitalLevel);
    }

    /**
     * 输出药品信息
     * @return 药品信息文本
     */
    @Override
    public String toString() {
        return this.getDrugID()+","+this.getDrugName()+","+this.getMaxPrice()+","+this.getUnit()+","+this.getPayLevel()+","+this.getHospitalLevel();
    }



    /**
     * Getter and Setter
     * @return
     */
    public String getDrugID() {
        return drugID.get();
    }

    public StringProperty drugIDProperty() {
        return drugID;
    }

    public void setDrugID(String drugID) {
        this.drugID.set(drugID);
    }

    public String getDrugName() {
        return drugName.get();
    }

    public StringProperty drugNameProperty() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName.set(drugName);
    }

    public String getMaxPrice() {
        return maxPrice.get();
    }

    public StringProperty maxPriceProperty() {
        return maxPrice;
    }

    public void setMaxPrice(String maxPrice) {
        this.maxPrice.set(maxPrice);
    }

   /* public String getPrice() {
        return price.get();
    }*/

    /*public StringProperty priceProperty() {
        return price;
    }*/

    /*public void setPrice(String price) {
        this.price.set(price);
    }*/

    public String getUnit() {
        return unit.get();
    }

    public StringProperty unitProperty() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit.set(unit);
    }

    public String getPayLevel() {
        return payLevel.get();
    }

    public StringProperty payLevelProperty() {
        return payLevel;
    }

    public void setPayLevel(String payLevel) {
        this.payLevel.set(payLevel);
    }

    public String getHospitalLevel() {
        return hospitalLevel.get();
    }

    public StringProperty hospitalLevelProperty() {
        return hospitalLevel;
    }

    public void setHospitalLevel(String hospitalLevel) {
        this.hospitalLevel.set(hospitalLevel);
    }


}
