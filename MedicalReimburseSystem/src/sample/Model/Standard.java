package sample.Model;
/**
 * @author 马杰生
 * @date 2018-7-30
 * 模型类：分段付费标准
 */
public class Standard {


    String startStandard;
    String upBound;
    String lowBound;
    String selfPercentage;

    /**
     * 构造器
     * @param startStandard 起付标准
     * @param upBound 分段上限
     * @param lowBound 分段下线
     * @param selfPercentage 自费比例
     */

    public Standard(String startStandard, String upBound, String lowBound, String selfPercentage) {
        this.startStandard = startStandard;
        this.upBound = upBound;
        this.lowBound = lowBound;
        this.selfPercentage = selfPercentage;
    }


    /**
     * Getter and setter
     * @return
     */
    public String getUpBound() {
        return upBound;
    }
    public void setUpBound(String upBound) {
        this.upBound = upBound;
    }
    public String getLowBound() {
        return lowBound;
    }
    public void setLowBound(String lowBound) {
        this.lowBound = lowBound;
    }
    public String getSelfPercentage() {
        return selfPercentage;
    }
    public void setSelfPercentage(String selfPercentage) {
        this.selfPercentage = selfPercentage;
    }
    public String getStartStandard() {
        return startStandard;
    }
    public void setStartStandard(String startStandard) {
        this.startStandard = startStandard;
    }


}
