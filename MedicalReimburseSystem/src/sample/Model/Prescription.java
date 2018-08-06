package sample.Model;

/**
 * @author 马杰生
 * @date 2018-7-30
 * 模型类：处方信息
 */
import javafx.beans.property.*;
import java.util.ArrayList;


public class Prescription {
    private ArrayList<PrescriptionItem> items = new ArrayList<PrescriptionItem>();
    private final StringProperty pre_outpatientCode;
    private final StringProperty Total;
    private int status = 0;

    /**
     * 构造器
     * @param pre_outpatientCode 门诊号
     * @param total 处方总金额
     */
    public Prescription(String pre_outpatientCode, String total) {
        this.pre_outpatientCode =new SimpleStringProperty(pre_outpatientCode) ;
        this.Total = new SimpleStringProperty(total);
    }

    /**
     * 获取处方信息
     * @return 处方信息文本
     */
    @Override
    public String toString() {
        String ItemString="";
        for (PrescriptionItem item:this.getItems()
                ) {
            ItemString+=item.toString()+"\r\n";
        }
        return "*"+this.getOutpatientCode()+"\r\n"+ItemString;
    }


    /**
     * 根据药品ID，获取处方内含有该药的处方子项目
     * @param drugID
     * @return 处方子项目
     */
    public PrescriptionItem getPrescriptionItem(String drugID){//Java 判定字符串相等时，应该使用equals，而非==
        PrescriptionItem returnItem = null;
        //System.out.println(this.getItems());
        for (PrescriptionItem item:this.getItems()
                ) {
            if (item.getDrugCode().equals(drugID)){
                returnItem = item;
            }
        }
        return returnItem;
    }

    /**
     * Getter and Setter
     * @return
     */
    public ArrayList<PrescriptionItem> getItems() {
        return items;
    }
    public String getOutpatientCode() {
        return pre_outpatientCode.get();
    }

    public StringProperty pre_outpatientCodeProperty() {
        return pre_outpatientCode;
    }

    public void setOutpatientCode(String pre_outpatientCode) {
        this.pre_outpatientCode.set(pre_outpatientCode);
    }

    public String getTotal() {
        return Total.get();
    }

    public StringProperty totalProperty() {
        return Total;
    }

    public void setTotal(String total) {
        this.Total.set(total);
    }

    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public void setItems(ArrayList<PrescriptionItem> items) {
        this.items = items;
    }
    public String getPre_outpatientCode() {
        return pre_outpatientCode.get();
    }
    public void setPre_outpatientCode(String pre_outpatientCode) {
        this.pre_outpatientCode.set(pre_outpatientCode);
    }

}
