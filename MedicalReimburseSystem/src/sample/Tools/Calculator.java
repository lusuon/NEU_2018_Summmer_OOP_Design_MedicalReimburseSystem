package sample.Tools;

/**
 * @author 马杰生
 * @date 2018-7-30
 * 工具类：计算报销信息
 */
import sample.Model.*;
import sample.Tools.StringToNumberTools;
import java.util.ArrayList;
import java.util.Date;

//报销系统的GUI尚未完全绑定,算法未完成部分:

//封顶线相关：【yearTotal】未完成：封顶线是设置中心报销时一年之内报销总额（不包括自费的部分）的封顶线，超过了封顶线的部分，全部自费。
//1.获取当前日期为锚定点
//2.以一年为范围，通过门诊号，以visit获取人员id，搜索区间内的诊疗信息，将其code加入Arraylist；visitTime为Arraylist的size（）
//3.由code寻找处方，求和
//4.在最先决条件判断，超过则全为C类

public class Calculator {
    DataManager dataManager=DataManager.getDatamanager();
    StringToNumberTools tools=StringToNumberTools.getTools();
    Checker checker =Checker.getChecker();
    String output = "";
    String preCal = "";
    String cal = "";

    double standard ;
    double lowBound1 ;
    double upBound1 ;
    double lowBound2 ;
    double upBound2 ;
    double lowBound3 ;
    double upBound3 ;
    int visitTimes ;
    String name ;
    String code ;
    String hospitalname;
    String inD ;
    String ouD ;
    String mst ;
    String typ;
    double sta ;
    double selfPaid ;
    double centerReimburse ;
    public String mainCalculate(Prescription prescription) {

        double APrice = 0;//甲类报销金额
        double BPrice = 0;//乙类报销金额
        double CPrice = 0;//丙类自费金额
        double Apart_selfPay = 0;//A段自费金额
        double Bpart_selfPay = 0;//B段自费金额
        double Cpart_selfPay = 0;//C段自费金额
        double selfPay = 0;//总自费金额
        double yearTotal = 0;//未完成
        double prescriptionTotal = 0;
        ArrayList<Visit> lastYearVisits = new ArrayList<Visit>();
        ArrayList<Drug> TypeA_Drug = new ArrayList<Drug>();//甲类药集合
        ArrayList<Drug> TypeB_Drug = new ArrayList<Drug>();//乙类药集合
        ArrayList<Drug> TypeC_Drug = new ArrayList<Drug>();//丙类药集合
        double allowReimbursePrice = APrice + BPrice;//可报销部分“X”

        standard = Integer.parseInt(dataManager.getStandards().get(0).getStartStandard());
        lowBound1= Double.parseDouble(dataManager.getStandards().get(0).getLowBound());
        upBound1 = Double.parseDouble(dataManager.getStandards().get(0).getUpBound());
        lowBound2 = Double.parseDouble(dataManager.getStandards().get(1).getLowBound());
        upBound2 = Double.parseDouble(dataManager.getStandards().get(1).getUpBound());
        lowBound3 = Double.parseDouble(dataManager.getStandards().get(2).getLowBound());
        upBound3 = Double.parseDouble(dataManager.getStandards().get(2).getUpBound()); ;
        /*
        standard = 100;
        lowBound1= 100;
        upBound1 = 10000;
        lowBound2 = 10001;
        upBound2 = 20000;
        lowBound3 = 20001;
        upBound3 = 99999999 ;
        */
        if (prescription.getStatus() == 0) {
            System.out.println("开始预结算：");
            System.out.println(dataManager.getStandards().get(0).getLowBound());
            System.out.println();
            Visit visit = dataManager.getVisit(prescription.getOutpatientCode());
            Person person = dataManager.getPerson(visit.getPersonID());
            System.out.println(person);
            String maxLine = dataManager.getMaxLines().get(person.getMedicalStaffType());
            //锚定点：传入处方对应的时间——修正：一律用本年度时间
            String currentTime = visit.getOutDate();
            String agoTime =StringToNumberTools.getTools().getAgoTime(currentTime);
            for (Visit past_visit:dataManager.getVisits().values()) {
                if(checker.isEarly(agoTime,past_visit.getInDate()) || checker.isEarly(past_visit.getOutDate(),currentTime) ) {//最早出院日期、最晚入院日期落在一年区间内的——或运算
                lastYearVisits.add(past_visit);
                }
            }
            visitTimes = lastYearVisits.size();
            //System.out.println("本年度就诊次数："+visitTimes);
            //System.out.println("本年度就诊："+lastYearVisits);
            for(Visit lastYearVisit:lastYearVisits){//由去年一年内的就诊信息，获取对应处方、求和
                yearTotal += branchCalculate(dataManager.getPrescription(lastYearVisit.getOutpatientCode()));
            }
            //预结算
            //计算金额：查询方法使用的列表目前尚未填入内容，在控制器初始化时记得将载入的数据同时存入查询用的列表
            //未实现：从visit获取【个人信息】，【由个人ID获取封顶线】，【判定年度报销额是否过封顶，超过了的部分，全部自费。】
            //将处方中的药品分类
            if(yearTotal>tools.stringToInt(maxLine)){
                //System.out.println("去年消费大于封顶,所有都属于自费");
                for (PrescriptionItem item : prescription.getItems()) {
                    Drug drug = dataManager.getDrug(item.getDrugCode());
                    TypeC_Drug.add(drug);
                }
                if (!TypeC_Drug.isEmpty()) {
                    for (Drug c : TypeC_Drug) {
                        double drugPrice = tools.stringTodouble(prescription.getPrescriptionItem(c.getDrugID()).getPrice());
                        CPrice += drugPrice;
                        selfPay += drugPrice;
                    }
                }
            }else {
                //System.out.println("消费未封顶");
                //System.out.println(prescription.getItems());
                for (PrescriptionItem item : prescription.getItems()) {
                    Drug drug = dataManager.getDrug(item.getDrugCode());
                    //System.out.println(drug);
                    int drugHospitalLevel = StringToNumberTools.getTools().drugHospitalLevelTrans(drug.getHospitalLevel());
                    int visitHospitalLevel = tools.stringToInt(dataManager.getVisit(prescription.getOutpatientCode()).getHospitalLevel());
                    if (drugHospitalLevel < visitHospitalLevel) {
                        TypeC_Drug.add(dataManager.getDrug(item.getDrugCode()));
                    } else if (dataManager.getDrug(item.getDrugCode()).getPayLevel().equals("甲类")) {
                        TypeA_Drug.add(dataManager.getDrug(item.getDrugCode()));
                    } else if (dataManager.getDrug(item.getDrugCode()).getPayLevel().equals("乙类")) {
                        TypeB_Drug.add(dataManager.getDrug(item.getDrugCode()));
                    } else if (dataManager.getDrug(item.getDrugCode()).getPayLevel().equals("丙类")) {//全部自费
                        TypeC_Drug.add(dataManager.getDrug(item.getDrugCode()));
                    }
                }
                System.out.println();
                //计算甲类:
                if (!TypeB_Drug.isEmpty()) {
                    for (Drug a : TypeA_Drug) {
                        double drugPrice = tools.stringTodouble(prescription.getPrescriptionItem(a.getDrugID()).getPrice());
                        double maxPrice = tools.stringTodouble(a.getMaxPrice());
                        if (drugPrice > maxPrice)//A类超出限价范围则超出部分自费
                        {
                            APrice += maxPrice;//报销金额为最大限价
                            selfPay += drugPrice - maxPrice;//余下自费
                        } else {
                            APrice += drugPrice;//全额报销
                        }
                    }
                }
                //计算乙类:
                if (!TypeB_Drug.isEmpty()) {
                    for (Drug b : TypeB_Drug) {
                        double drugPrice = tools.stringTodouble(prescription.getPrescriptionItem(b.getDrugID()).getPrice());
                        double maxPrice = tools.stringTodouble(b.getMaxPrice());
                        if (drugPrice > maxPrice) {//B类超出限价范围则超出部分自费
                            BPrice += maxPrice * 0.5;//报销最大限价的一半
                            selfPay += (maxPrice * 0.5 + drugPrice - maxPrice);//最大限价的一半与余下部分
                        } else {
                            BPrice += drugPrice * 0.5;//只报销一半
                            selfPay += drugPrice * 0.5;
                        }
                    }
                }
                //计算丙类:完全自费
                if (!TypeC_Drug.isEmpty()) {
                    for (Drug c : TypeC_Drug) {
                        double drugPrice = tools.stringTodouble(prescription.getPrescriptionItem(c.getDrugID()).getPrice());
                        CPrice += drugPrice;
                        selfPay += drugPrice;
                    }
                }
            }
            //计算分段
            //本次住院所有甲类药品总额 + 本次住院所有乙类药品总额的50% == X/allowReimbursePrice
            //1: X > 0 and X < 100   自费金额 = 所有甲类药品 + 所有乙类药品 + 所有丙类药品
            //（因为X为可报销部分，可报销部分小于100，不给予报销，所以所有费用自费承担）
            //举例说明： 30甲类药品 20乙类药品  115丙类药品
            //能报销部分： 30（甲） + 10（乙）== 40 < 100
            //所以自费部分为 30 + 20 + 115 = 165
            //2：X > 100 && X < 10000 自费金额=（X-100）* 20% + 100 + 丙类药品总额 + 乙类药品总额的50%
            //3：X > 10000 && X < 20000 自费金额=（X-10000）* 10% +（10000-100）* 20% + 100 + 丙类药品 + 乙类药品总额的50%
            //4：X> 20000 自费金额=（X-20000）* 5% +（20000-10000）* 10% +（10000-100）* 20% + 100 + 丙类药品总额 + 乙类药品总额的50%
            allowReimbursePrice = APrice + BPrice;
            System.out.println("X:"+allowReimbursePrice);
            if (allowReimbursePrice < standard) {
                System.out.println("小于起付标准");
                selfPay = allowReimbursePrice + CPrice + BPrice;
            } else if (lowBound1 < allowReimbursePrice && allowReimbursePrice < upBound1) {
                System.out.println("计算A段中");
                Apart_selfPay = (allowReimbursePrice - lowBound1) * 0.2;
                selfPay = Apart_selfPay + standard + CPrice + BPrice;
            } else if (lowBound2 < allowReimbursePrice && allowReimbursePrice < upBound2) {
                System.out.println("计算B段中");
                Bpart_selfPay = (allowReimbursePrice - lowBound2) * 0.1;
                selfPay = Bpart_selfPay + (lowBound2 - standard) * 20 % +CPrice + BPrice;
            } else if (lowBound3 < allowReimbursePrice && allowReimbursePrice < upBound3) {
                System.out.println("计算C段中");
                Cpart_selfPay = (allowReimbursePrice - lowBound3) * 0.05;
                selfPay = Cpart_selfPay + (upBound2 - lowBound2) * 0.1 + (upBound1 - lowBound1) * 0.2 + standard + CPrice + BPrice;
            }

            for (PrescriptionItem item:prescription.getItems()) {
                prescriptionTotal+=tools.stringTodouble(item.getItemTotal());
            }
            yearTotal+=(prescriptionTotal - selfPay);
            name = person.getName();
            code = person.getDocumentCode();
            hospitalname = visit.getHospitalName();
            inD = visit.getInDate();
            ouD = visit.getOutDate();
            mst = person.getMedicalStaffType();
            selfPaid =selfPay;
            centerReimburse =(prescriptionTotal - selfPay);
            dataManager.getStringResultCache().add(name);
            dataManager.getStringResultCache().add(code);
            dataManager.getStringResultCache().add(hospitalname);
            dataManager.getStringResultCache().add(inD);
            dataManager.getStringResultCache().add(ouD);
            dataManager.getStringResultCache().add(mst);
            dataManager.getDoubleResultCache().add(selfPaid);
            dataManager.getDoubleResultCache().add(centerReimburse);
            preCal += "#姓名：" + person.getName() + "\r\n"
                    +"证件号：" + person.getDocumentCode() + "\r\n"
                    +"医院名：" + visit.getHospitalName() + "\r\n"
                    +"住院日期：自" + visit.getInDate() + "到" + visit.getOutDate() + "\r\n";
            //起付标准,报销封顶线      || 根据人员的 医疗人员类别  从  医疗人员类别.txt  医疗待遇计算参数.txt 个文件里取
            preCal += "起付标准："+dataManager.getStandards().get(0).getStartStandard()
                    + "\r\n报销封顶线：" + dataManager.getMaxLine(person.getMedicalStaffType()) + "\r\n";
            //费用总额,                || 本次住院所有药品的金额之和  (包含甲乙丙类)
            preCal += "费用总额：" + prescriptionTotal + "\r\n";//getTotal获取的为GUI专用数据，故返回空指针
            //乙类项目自费金额         || 本次住院所有乙类药品的金额之和的50%
            preCal += "乙类项目自费金额：" + BPrice + "\r\n";
            //计算分段，需载入参数
            //分段计算中自费金额_A段,  || 本次住院所有乙类药品的金额之和的50%+所有甲类药品之和X,中符合(X-100) * 20%    X<10000
            preCal += "分段计算中自费金额_A段：" + Apart_selfPay + "\r\n";
            //分段计算中自费金额_B段,  || 本次住院所有乙类药品的金额之和的50%+所有甲类药品之和中符合(X-10000) * 10%  X>10000 AND X<=20000
            preCal += "分段计算中自费金额_B段：" + Bpart_selfPay + "\r\n";
            //分段计算中自费金额_C段,  || 本次住院所有乙类药品的金额之和的50%+所有甲类药品之和中符合(X-20000) * 5%   X>20000
            preCal += "分段计算中自费金额_C段：" + Cpart_selfPay + "\r\n";
            //自费金额,                || 分段计算中自费金额_A段 + 分段计算中自费金额_B段 + 分段计算中自费金额_C段
            preCal += "自费金额：" + selfPay + "\r\n";
            //报销金额,                || 费用总额 - 自费金额
            preCal += "报销金额：" + (prescriptionTotal - selfPay) + "\r\n";
            //年度累计报销金额,        || 预结算信息.txt文件内 指定证件编号 就诊时段为本年内的 报销金额之和  (算本次)
            preCal += "年度累计报销金额：" + yearTotal + "\r\n";//未完成?
            prescription.setStatus(1);
            output = "**************************************************\r\n"+"预结算结果为：\r\n"+preCal+"**************************************************\r\n";
            dataManager.getResult().add(0,output);
        } else if (prescription.getStatus() == 1) {
            //结算
            //#姓名,证件编号(身份证号/医保卡号)就诊医院,就诊时段,  || 取预结算文件中的 对应数据的对应字段
            cal += "#人员姓名：" + dataManager.getStringResultCache().get(0) + ",\r\n"
                    + "证件编号："+dataManager.getStringResultCache().get(1)+ ",\r\n"
                    + "医院名称："+dataManager.getStringResultCache().get(2) + ",\r\n"
                    + "住院时间：" + dataManager.getStringResultCache().get(3) + "到" + dataManager.getStringResultCache().get(4) + "\r\n";
            //起付标准,                                            || 取预结算文件中的 对应数据的对应字段
            cal += "起付标准：" + dataManager.getStandards().get(0).getStartStandard()+ "\r\n";
            //人员类别,                                            || 根据 证件编号 在人员基本信息 对象里取
            cal += "人员类别：" + dataManager.getStringResultCache().get(5) + "\r\n";
            //住院次数,                                            || 结算信息.txt文件内 指定证件编号 就诊时段为本年内的 记录条数
            cal += "住院次数：" + visitTimes + "\r\n";
            //个人自费费用,                                        || 取预结算文件中的 对应数据的  自费金额 字段
            cal += "个人自费费用：" + dataManager.getDoubleResultCache().get(0) + "\r\n";//未完成，如何获取上一方法的值？
            //中心报销金额                                         || 取预结算文件中的 对应数据的  报销金额 字段
            cal += "中心报销金额：" + dataManager.getDoubleResultCache().get(1) +"\r\n";
            prescription.setStatus(2);
            output = "**************************************************\r\n"+"结算结果为：\r\n"+cal+"**************************************************\r\n";
            dataManager.getResult().add(1,output);
        } else {
            System.out.println("该处方已完成结算");
            //报告已结算,考虑弹窗
        }
        return output;
    }
    public double branchCalculate(Prescription prescription){
        double yearTotal = 0;//未完成
        double prescriptionTotal = 0;
        ArrayList<Visit> lastYearVisits = new ArrayList<Visit>();
        double APrice = 0;//甲类报销金额
        double BPrice = 0;//乙类报销金额
        double CPrice = 0;//丙类自费金额
        double Apart_selfPay = 0;//A段自费金额
        double Bpart_selfPay = 0;//B段自费金额
        double Cpart_selfPay = 0;//C段自费金额
        double selfPay = 0;//总自费金额
        ArrayList<Drug> TypeA_Drug = new ArrayList<Drug>();//甲类药集合
        ArrayList<Drug> TypeB_Drug = new ArrayList<Drug>();//乙类药集合
        ArrayList<Drug> TypeC_Drug = new ArrayList<Drug>();//丙类药集合
        double prescriptionReimburse ;
        double allowReimbursePrice = APrice + BPrice;//可报销部分“X”
        Visit visit = dataManager.getVisit(prescription.getOutpatientCode());
        Person person = dataManager.getPerson(visit.getPersonID());

        String maxLine = dataManager.getMaxLines().get(person.getMedicalStaffType());
        //预结算
        //计算金额：查询方法使用的列表目前尚未填入内容，在控制器初始化时记得将载入的数据同时存入查询用的列表
        //未实现：从visit获取【个人信息】，【由个人ID获取封顶线】，【判定年度报销额是否过封顶，超过了的部分，全部自费。】
        //将处方中的药品分类
        if(yearTotal>tools.stringToInt(maxLine)){//去年消费大于封顶,所有都属于丙类自费
            prescriptionReimburse = 0;
        }else {//消费未封顶
            for (PrescriptionItem item : prescription.getItems()) {
                Drug drug = dataManager.getDrug(item.getDrugCode());
                int drugHospitalLevel = tools.stringToInt(drug.getHospitalLevel());
                int visitHospitalLevel = tools.stringToInt(dataManager.getVisit(prescription.getOutpatientCode()).getHospitalLevel());
                if (drugHospitalLevel < visitHospitalLevel) {//超出医院等级全部自费
                    TypeC_Drug.add(dataManager.getDrug(item.getDrugCode()));
                } else if (dataManager.getDrug(item.getDrugCode()).getPayLevel() == "甲类") {//全部报销
                    TypeA_Drug.add(dataManager.getDrug(item.getDrugCode()));
                } else if (dataManager.getDrug(item.getDrugCode()).getPayLevel() == "乙类") {
                    TypeB_Drug.add(dataManager.getDrug(item.getDrugCode()));
                } else if (dataManager.getDrug(item.getDrugCode()).getPayLevel() == "丙类") {//全部自费
                    TypeC_Drug.add(dataManager.getDrug(item.getDrugCode()));
                }
            }
            //计算甲类:
            if (!TypeB_Drug.isEmpty()) {
                //System.out.println("计算甲类中");
                for (Drug a : TypeA_Drug) {
                    double drugPrice = tools.stringTodouble(prescription.getPrescriptionItem(a.getDrugID()).getPrice());
                    double maxPrice = tools.stringTodouble(a.getMaxPrice());
                    if (drugPrice > maxPrice)//A类超出限价范围则超出部分自费
                    {
                        APrice += maxPrice;//报销金额为最大限价
                        selfPay += drugPrice - maxPrice;//余下自费
                    } else {
                        APrice += drugPrice;//全额报销
                    }
                }
            }
            //计算乙类:
            if (!TypeB_Drug.isEmpty()) {
                //System.out.println("计算乙类中");
                for (Drug b : TypeB_Drug) {
                    double drugPrice = tools.stringTodouble(prescription.getPrescriptionItem(b.getDrugID()).getPrice());
                    double maxPrice = tools.stringTodouble(b.getMaxPrice());
                    if (drugPrice > maxPrice) {//B类超出限价范围则超出部分自费
                        BPrice += maxPrice * 0.5;//报销最大限价的一半
                        selfPay += (maxPrice * 0.5 + drugPrice - maxPrice);//最大限价的一半与余下部分
                    } else {
                        BPrice += drugPrice * 0.5;//只报销一半
                        selfPay += drugPrice * 0.5;
                    }
                }
            }
            //计算丙类:完全自费
            if (!TypeC_Drug.isEmpty()) {
                //System.out.println("计算丙类中");
                for (Drug c : TypeC_Drug) {
                    double drugPrice = tools.stringTodouble(prescription.getPrescriptionItem(c.getDrugID()).getPrice());
                    CPrice += drugPrice;
                    selfPay += drugPrice;
                }
            }
            prescriptionReimburse = allowReimbursePrice;
        }
        return prescriptionReimburse;
    }
}
