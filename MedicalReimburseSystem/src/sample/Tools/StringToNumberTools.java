package sample.Tools;
/**
 * @author 马杰生
 * @date 2018-7-30
 * 工具类：字符串转为数字
 * 实现：
 * 1.判断字符串能否转化为数字
 * 2.将字符串转为数字
 */

public class StringToNumberTools {
    public static StringToNumberTools tools = new StringToNumberTools();

    /**
     * 获取转换工具类静态实例
     * @return 转换工具类对象
     */
    public static StringToNumberTools getTools() {
        return tools;
    }
    private StringToNumberTools(){}


    /**
     * 判断字符串是否能转换为整数
     * @param str 输入字符串
     * @return
     */
    public boolean isStrToInt(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 判断字符串是否能转换为double型小数
     * @param str 输入字符串
     * @return
     */
    public boolean isStrToDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    /**
     * 将字符串转换为整数
     * @param str 输入字符串
     * @return 整数
     */
    public int stringToInt(String str){
        int returnInt = 0;

        if(isStrToInt(str)==true) {
            returnInt = Integer.parseInt(str);
        }
        return  returnInt;
    }

    /**
     * 将字符串转换为double型小数
     * @param str 输入字符串
     * @return 小数
     */
    public double stringTodouble(String str){
        double returnDouble = 0;
        if(isStrToInt(str)==false){
            returnDouble = Double.parseDouble(str);
        }
        return returnDouble;
    }

    /**
     * 将日期字符串处理为一年前的日期
     * @param date
     * @return 一年前的日期
     */
    public String getAgoTime(String date){
        String[] Tokens=date.split("-");
        int y =tools.stringToInt(Tokens[0].split("0")[1]);
        int yy = y;
        int mm = 1;
        int dd = 1;
        return Integer.toString(yy)+"-"+Integer.toString(mm)+"-"+Integer.toString(dd);
    }

    public  int drugHospitalLevelTrans(String input){
        int level = 0;
        if(input.equals( "三级")){
            level=3;
        }else if(input.equals("二级")){
            level=2;
        }else if(input.equals("一级")){
            level=1;
        }
        return level;
    }

}
