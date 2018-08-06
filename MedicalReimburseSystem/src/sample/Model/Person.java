package sample.Model;
/**
 * @author 马杰生
 * @date 2018-7-30
 * 模型类：人员信息
 */
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
//人员ID,证件类型,证件编号,姓名,性别,民族,出生日期,医疗人员类别
public class Person {
    private final StringProperty personID;
    private final StringProperty documentType;
    private final StringProperty documentCode;
    private final StringProperty name;
    private final StringProperty gender;
    private final StringProperty nationality;
    private final StringProperty birthDate;
    private final StringProperty medicalStaffType;

    /**
     * 构造器
     * @param personID 人员ID
     * @param documentType 证件类型
     * @param documentCode 证件编号
     * @param name 姓名
     * @param gender 性别
     * @param nationality 民族
     * @param birthDate 出生日期
     * @param medicalStaffType 医疗人员类别
     */
    public Person(String personID, String documentType, String documentCode, String name, String gender, String nationality, String birthDate, String medicalStaffType) {
        this.personID = new SimpleStringProperty(personID);
        this.documentType = new SimpleStringProperty(documentType);
        this.documentCode = new SimpleStringProperty(documentCode);
        this.name = new SimpleStringProperty(name);
        this.gender = new SimpleStringProperty(gender);
        this.nationality = new SimpleStringProperty(nationality);
        this.birthDate = new SimpleStringProperty(birthDate);
        this.medicalStaffType = new SimpleStringProperty(medicalStaffType);
    }

    /**
     * 返回人员信息
     * @return 人员信息文本
     */
    @Override
    public String toString() {
        return this.getpersonID()+","+this.getDocumentType()+","+this.getDocumentCode()+","+this.getName()+","+this.getGender()+","+this.getNationality()+","+this.getBirthDate()+","+this.getMedicalStaffType();
    }

    /**
     * Getter and Setter
     * @return
     */
    public String getpersonID() {
        return personID.get();
    }

    public StringProperty personIDProperty() {
        return personID;
    }

    public void setpersonID(String personID) {
        this.personID.set(personID);
    }

    public String getDocumentType() {
        return documentType.get();
    }

    public StringProperty documentTypeProperty() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType.set(documentType);
    }

    public String getDocumentCode() {
        return documentCode.get();
    }

    public StringProperty documentCodeProperty() {
        return documentCode;
    }

    public void setDocumentCode(String documentCode) {
        this.documentCode.set(documentCode);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getGender() {
        return gender.get();
    }

    public StringProperty genderProperty() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender.set(gender);
    }

    public String getNationality() {
        return nationality.get();
    }

    public StringProperty nationalityProperty() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality.set(nationality);
    }

    public String getBirthDate() {
        return birthDate.get();
    }

    public StringProperty birthDateProperty() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate.set(birthDate);
    }

    public String getMedicalStaffType() {
        return medicalStaffType.get();
    }

    public StringProperty medicalStaffTypeProperty() {
        return medicalStaffType;
    }

    public void setMedicalStaffType(String medicalStaffType) {
        this.medicalStaffType.set(medicalStaffType);
    }


}
