package sample.Model;

/**
 * @author 马杰生
 * @date 2018-7-30
 * 模型类：就诊信息
 */
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;


//人员ID, 住院号(门诊号) 医疗类别(医院等级), 点医疗机构编号（医院编号）,定点医疗机构名称（医院名称）,入院日期,出院日期,医院等级,出院原因
public class Visit {
    private final StringProperty personID;
    private final StringProperty outpatientCode;
    private final StringProperty hospitalLevel;
    private final StringProperty hospitalCode;
    private final StringProperty hospitalName;
    private final StringProperty inDate;
    private final StringProperty outDate;
    private final StringProperty outReason;

    /**
     * 构造器
     * @param personID 患者个人ID
     * @param outpatientCode 门诊号
     * @param inDate 入院时间
     * @param outDate 出院时间
     * @param hospitalLevel 医院等级
     * @param hospitalCode 医院编号
     * @param hospitalName 医院名称
     * @param outReason 出院原因
     */
    public Visit(String personID,String outpatientCode, String inDate, String outDate, String hospitalLevel, String hospitalCode, String hospitalName, String outReason) {
        this.personID =new SimpleStringProperty( personID);
        this.outpatientCode =new SimpleStringProperty(outpatientCode);
        this.hospitalLevel =new SimpleStringProperty(hospitalLevel);
        this.hospitalCode =new SimpleStringProperty(hospitalCode);
        this.hospitalName =new SimpleStringProperty(hospitalName);
        this.inDate =new SimpleStringProperty(inDate);
        this.outDate =new SimpleStringProperty(outDate);
        this.outReason =new SimpleStringProperty(outReason);
    }

    /**
     * 获取就诊信息
     * @return 就诊信息文本
     */
    @Override//人员ID, 住院号(门诊号),医疗类别(医院等级), 定点医疗机构编号（医院编号）,定点医疗机构名称（医院名称）,入院日期,出院日期,出院原因
    public String toString() {
        return this.getPersonID()+","+this.getOutpatientCode()+","+this.getInDate()+","+this.getOutDate()+","+this.getHospitalLevel()+","+this.getHospitalCode()+","+this.getHospitalName()+","+this.getOutReason();
    }

    public String getPersonID() {
        return personID.get();
    }

    public StringProperty personIDProperty() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID.set(personID);
    }

    public String getOutpatientCode() {
        return outpatientCode.get();
    }

    public StringProperty outpatientCodeProperty() {
        return outpatientCode;
    }

    public void setOutpatientCode(String outpatientCode) {
        this.outpatientCode.set(outpatientCode);
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

    public String getHospitalCode() {
        return hospitalCode.get();
    }

    public StringProperty hospitalCodeProperty() {
        return hospitalCode;
    }

    public void setHospitalCode(String hospitalCode) {
        this.hospitalCode.set(hospitalCode);
    }

    public String getHospitalName() {
        return hospitalName.get();
    }

    public StringProperty hospitalNameProperty() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName.set(hospitalName);
    }

    public String getInDate() {
        return inDate.get();
    }

    public StringProperty inDateProperty() {
        return inDate;
    }

    public void setInDate(String inDate) {
        this.inDate.set(inDate);
    }

    public String getOutDate() {
        return outDate.get();
    }

    public StringProperty outDateProperty() {
        return outDate;
    }

    public void setOutDate(String outDate) {
        this.outDate.set(outDate);
    }

    public String getOutReason() {
        return outReason.get();
    }

    public StringProperty outReasonProperty() {
        return outReason;
    }

    public void setOutReason(String outReason) {
        this.outReason.set(outReason);
    }
}
