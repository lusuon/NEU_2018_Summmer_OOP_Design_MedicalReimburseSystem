package sample.Model;

/**
 * @author 马杰生
 * @date 2018-7-30
 * 模型类：处方信息
 */
import javafx.beans.property.*;
import sample.Tools.StringToNumberTools;

//使用code值作为依据
public class PrescriptionItem {

    private final StringProperty drugCode;
    private final StringProperty price;
    private final StringProperty quantity;
    private final StringProperty itemTotal;

    /**
     * 构造器
     * @param drugCode 药品编码
     * @param price 药品价格
     * @param quantity 药品数量
     */
    public PrescriptionItem(String drugCode, String price, String quantity) {
        StringToNumberTools tools=StringToNumberTools.getTools();
        String itemTotal =Double.toString((tools.stringTodouble(price)  * tools.stringToInt(quantity)));
        this.drugCode = new SimpleStringProperty(drugCode);
        this.price = new SimpleStringProperty(price) ;
        this.quantity = new SimpleStringProperty(quantity);
        this.itemTotal =new SimpleStringProperty(itemTotal);
    }

    /**
     * 获取处方子项目信息
     * @return 处方子项目信息文本
     */
    @Override
    public String toString() {
        return this.getDrugCode()+","+this.getPrice()+","+this.getQuantity()+","+this.getItemTotal();
    }


    /**
     * Getter and Setter
     * @return
     */
    public String getPrice() {
        return price.get();
    }

    public StringProperty priceProperty() {
        return price;
    }

    public void setPrice(String price) {
        this.price.set(price);
    }

    public String getDrugCode() {
        return drugCode.get();
    }

    public StringProperty drugCodeProperty() {
        return drugCode;
    }

    public void setDrugCode(String drugCode) {
        this.drugCode.set(drugCode);
    }

    public String getQuantity() {
        return quantity.get();
    }

    public StringProperty quantityProperty() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity.set(quantity);
    }

    public String getItemTotal() {
        return itemTotal.get();
    }

    public StringProperty itemTotalProperty() {
        return itemTotal;
    }

    public void setItemTotal(String itemTotal) {
        this.itemTotal.set(itemTotal);
    }




}
