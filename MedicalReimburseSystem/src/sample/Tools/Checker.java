package sample.Tools;
import sample.Model.Prescription;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import sample.Tools.StringToNumberTools;

/**
 * @author 马杰生
 * @date 2018-7-30
 * 用于提供判定输入值是否为特定格式的方法
 */
public class Checker {
    StringToNumberTools tools = StringToNumberTools.getTools();
    DataManager dataManager = DataManager.getDatamanager();

    public static Checker getChecker() {
        return checker;
    }

    private Checker(){};
    public static Checker checker = new Checker();


    /**
     * 使用正则表达式判断输入是否为日期
     * @param input 输入
     * @return
     */
    public boolean isDate(String input){
        Pattern pattern = Pattern.compile("((((19|20)\\d{2})-(0?(1|[3-9])|1[012])-(0?[1-9]|[12]\\d|30))|(((19|20)\\d{2})-(0?[13578]|1[02])-31)|(((19|20)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-8]))|((((19|20)([13579][26]|[2468][048]|0[48]))|(2000))-0?2-29))$");
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
        }

    /**
     * 判断日期在比较者的前后
     * @param compare 比较者
     * @param standard 基准日期
     * @return
     */
    public boolean isEarly(String compare,String standard){
        boolean boo = true;
        String[] compareTokens=compare.split("-");
        System.out.println(Arrays.toString(compareTokens));
        String[] standardTokens=standard.split("-");
        System.out.println(Arrays.toString(standardTokens));
        int cy =tools.stringToInt(compareTokens[0]);
        int cm =tools.stringToInt(compareTokens[1]);
        int cd =tools.stringToInt(compareTokens[2]);
        int sy =tools.stringToInt(standardTokens[0]);
        int sm =tools.stringToInt(standardTokens[1]);
        int sd =tools.stringToInt(standardTokens[2]);
        if(cy < sy){
            boo = true;
        }else if(cy == sy){
            if (cm < sm){
                boo = true;
            }else if(cm == sm){
                if (cd<sd) {
                    boo = true;
                }else if(cd == sd){
                    boo =false;
                }else if(cd>sd){
                    boo = false;
                }
            }else if(cm > sm){
                boo = false;
            }
        }else if(cy>sy){
            boo = false;
        }
        return boo;
    }

    /**
     * 应用正则表达式判断人员ID格式是否正确
     * @param input 输入
     * @return
     */
    public boolean isPersonID(String input){
        Pattern pattern = Pattern.compile("^CN[0-9]*$");
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    /**
     * 判断输入是否为合法证件类型
     * @param input 输入
     * @return
     */
    public boolean isDocumentType(String input){
        boolean boo = false;
        if(input.equals("身份证")||input.equals("医保卡")){
            boo = true;
        }
        return boo;
    }

    /**
     * 判断输入是否为合法性别
     * @param input 输入
     * @return
     */
    public boolean isGender(String input){
        boolean boo = false;
        if(input.equals("男")||input.equals("女")){
            boo = true;
        }
        return boo;
    }

    /**
     * 判断输入是否为合法医疗人员类别
     * @param input 输入
     * @return
     */
    public boolean isMedicalStaffType(String input){
        boolean boo = false;
        for (String type:dataManager.getMaxLines().keySet()) {
            System.out.println("key:"+type);
            System.out.println("check:"+"#"+input);
            System.out.println(type.equals("#"+input));
            if(type.equals(input)){
                boo=true;
            }
            System.out.println(boo);
        }
        return boo;
    }//

    /**
     * 判断输入是否为合法收费等级
     * @param input 输入
     * @return
     */
    public boolean isPayLevel(String input){
        boolean boo = false;
        List<String> payLevels = Arrays.asList("甲类","乙类","丙类");
        for (String level:payLevels) {
            if(level.equals(input)){
                boo = true;
            }
        }
        return boo;
    }


    /**
     * 判断输入是否为合法医院等级
     * @param input 输入
     * @return
     */
    public boolean isHospitalLevel(String input){
        boolean boo = false;
        List<String> payLevels = Arrays.asList("一级","二级","三级");
        for (String level:payLevels) {
            if(level.equals(input)){
                boo = true;
            }
        }
        return boo;
    }


    /**
     * 以id判断输入人员数据是否重复
     * @param input 输入
     * @return
     */
    public boolean isPersonRepeated(String input){
        boolean boo =false;
        if(dataManager.getPeople().get(input)!=null){
            boo = true;
        }
        return boo;
    }


    /**
     * 以药品编号判断输入药品数据是否重复
     * @param input 输入
     * @return
     */
    public boolean isDrugRepeated(String input){
        boolean boo =false;
        if(dataManager.getDrugs().get(input)!=null){
            boo = true;
        }
        return boo;
    }

    /**
     * 以门诊号判断输入就诊数据是否重复
     * @param input 输入
     * @return
     */
    public boolean isVisitRepeated(String input){
        boolean boo =false;
        if(dataManager.getVisits().get(input)!=null){
            boo = true;
        }
        return boo;
    }


    /**
     * 以门诊号判断输入处方数据是否重复
     * @param input 输入
     * @return
     */
    public boolean isPrescriptionRepeated(String input){
        boolean boo =false;
        for (Prescription prescription:dataManager.getPrescriptions()) {
            if(prescription.getOutpatientCode().equals(input)){
                boo = true;
            }
        }
        return boo;
    }
}
