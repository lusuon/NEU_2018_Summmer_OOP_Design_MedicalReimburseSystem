package sample.Model;


/**
 * @author 马杰生
 * @date 2018-7-30
 * 模型类：医疗保险参数
 */
public class Attribute {
    private String type;
    private String value;
    /**
     * 构造器
     * @param type 医疗参数类型
     * @param value 参数值
     */
    public Attribute(String type, String value) {
        this.type = type;
        this.value = value;
    }

    /**
     * Getter and Setter
     * @return
     */

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
