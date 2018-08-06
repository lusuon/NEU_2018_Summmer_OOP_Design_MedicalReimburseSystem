package sample.Tools;
import sample.Model.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
/**
 * /**
 * @author 马杰生
 */


public class DataManager {

    public  static  DataManager datamanager =new DataManager();
    private DataManager(){}

    /**
     * 获取数据管理类静态实例
     * @return  数据管理类静态实例
     */
    public static DataManager getDatamanager() {
        return datamanager;
    }

    //暂存参数数据的集合
    private HashMap<String,String> maxLines = new HashMap<String, String>();
    private ArrayList<Standard> standards = new ArrayList<Standard>();

    /**
     * 自txt加载参数数据
     * @throws IOException
     */
    public void loadAttribute() throws IOException {
        String str;
        InputStream f = new FileInputStream("C:/Users/54234/IdeaProjects/NEU_Summer/src/sample/DB/MaxLine.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(f));
        while (((str = br.readLine()) != null)) {
            if (str.split(",").length==2) {
                String medicalStaffType =str.split(",")[0];
                String maxLine = str.split(",")[1];
                maxLines.put(medicalStaffType,maxLine);
            }
        }
        br.close();
        InputStream g = new FileInputStream("C:/Users/54234/IdeaProjects/NEU_Summer/src/sample/DB/Attribute.txt");
        BufferedReader br2 = new BufferedReader(new InputStreamReader(g));
        while ((str= br2.readLine())!=null){
                if(str.split(",").length == 4) {
                String [] tokens = str.split(",");
                standards.add(new Standard(tokens[0],tokens[1],tokens[2],tokens[3]));
                //System.out.println("loading standards");
                }
            }
        br2.close();
        //System.out.println(standards);
        }

    /**
     * 获取封顶线
     * @param key
     * @return
     */
    public String getMaxLine(String key) {
        return (String) maxLines.get(key);
    }

    private HashMap<String,Person> people = new HashMap<String, Person>();//以ID为token

    /**
     * 从txt加载人员信息
     * @throws IOException
     */
    public void loadPeople() throws IOException {
        String str;
        InputStream f = new FileInputStream("C:/Users/54234/IdeaProjects/NEU_Summer/src/sample/DB/Person.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(f));
        while (((str = br.readLine()) != null)) {
            String[] information = str.split(",");
            //添加检定，避免token数异常
            if (information.length == 8) {
                people.put(information[0],new Person(information[0], information[1], information[2], information[3], information[4], information[5], information[6], information[7]));
            } else {
                System.out.println("异常数据:" + str);
            }
        }
    }

    /**
     * 由人员ID获取人员类对象
     * @param id
     * @return 人员类对象
     */
    public Person getPerson(String id) {
        return people.get(id);
    }

    //暂存药品数据的集合
    private HashMap<String,Drug> drugs = new HashMap<String, Drug>();

    /**
     * 从txt加载药品
     * @throws IOException
     */
    public void loadDrugs() throws IOException {
        String str;
        InputStream f = new FileInputStream("C:/Users/54234/IdeaProjects/NEU_Summer/src/sample/DB/Drug.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(f));
        while (((str = br.readLine()) != null)) {
            String[] information = str.split(",");
            //添加检定，避免token数异常
            if (information.length == 6) {
                drugs.put(information[0],new Drug(information[0], information[1], information[2], information[3], information[4], information[5]));
            } else {
                System.out.println("异常数据:" + str);
            }
        }
    }

    /**
     * 由ID获取药品对象
     * @param id
     * @return 药品类对象
     */
    public Drug getDrug(String id) {
        return drugs.get(id);
    }

    private HashMap<String,Visit> visits = new HashMap<String, Visit>();

    /**
     * 从txt加载就诊信息
     * @throws IOException
     */
    public void loadVisits() throws IOException {
        String str;
        InputStream f = new FileInputStream("C:/Users/54234/IdeaProjects/NEU_Summer/src/sample/DB/Visit.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(f));
        while (((str = br.readLine()) != null)) {
            String[] information = str.split(",");
            //添加检定，避免token数异常
            if (information.length == 8) {
                visits.put(information[1],new Visit(information[0], information[1], information[2], information[3], information[4], information[5], information[6], information[7]));
            } else {
                System.out.println("异常数据:" + str);
            }
        }
        //System.out.println(visits);
    }

    /**
     * 根据门诊号获取就诊信息
     * @param outpatientCode
     * @return 就诊信息类对象
     */
    public Visit getVisit(String outpatientCode) {
        //System.out.println(visits);
        return visits.get(outpatientCode);
    }

    //暂存处方数据的集合
    private ArrayList<Prescription> prescriptions = new ArrayList<Prescription>();

    /**
     * 从txt加载处方数据
     * @throws IOException
     */
    public void loadPrescription() throws IOException {
        String line;
        InputStream readPrescription = new FileInputStream("C:/Users/54234/IdeaProjects/NEU_Summer/src/sample/DB/Prescription.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(readPrescription));
        while (((line = br.readLine()) != null)) {
            if (line.startsWith("*")) {
                //System.out.println("载入新处方");
                prescriptions.add(new Prescription(line.split("\\*")[1], "0"));
            }
        }
        br.close();
        InputStream readItem = new FileInputStream("C:/Users/54234/IdeaProjects/NEU_Summer/src/sample/DB/Prescription.txt");
        BufferedReader br2 = new BufferedReader(new InputStreamReader(readItem, "UTF-8"));
        int i = 0;
        while ((line = br2.readLine()) != null) {
            System.out.println(line);
            if (!line.startsWith("\\*")) {//txt文件开头有字符，在记事本中看不见
                //System.out.println("读取处方项目");
                String[] tempArray = line.split(",");
                /*
                System.out.println();
                System.out.println("1st:"+tempArray[0]);
                System.out.println("2nd:"+tempArray[1]);
                System.out.println("3rd:"+tempArray[2]);
                */
                if (tempArray.length == 4) {
                    prescriptions.get(i).getItems().add(new PrescriptionItem(tempArray[0], tempArray[1], tempArray[2]));
                /*
                System.out.println(originPrescriptionData);
                System.out.println(i);
                */
                }else{
                    System.out.println("问题数据：" + line);
                }
            }else{
                System.out.println("读取新处方门诊号");
                i++;
            }
        }
        br2.close();
    }


    //缓存预结算
    private ArrayList<Double> preCal_Cache = new ArrayList<Double>();

    //缓存结算过程信息
    private ArrayList<String> result = new ArrayList<String>();

    private ArrayList<String> stringResultCache= new ArrayList<String>();

    public ArrayList<String> getStringResultCache() {
        return stringResultCache;
    }

    public void setStringResultCache(ArrayList<String> stringResultCache) {
        this.stringResultCache = stringResultCache;
    }

    public ArrayList<Double> getDoubleResultCache() {
        return doubleResultCache;
    }

    public void setDoubleResultCache(ArrayList<Double> doubleResultCache) {
        this.doubleResultCache = doubleResultCache;
    }

    private ArrayList<Double> doubleResultCache = new ArrayList<Double>();


    /**
     * Getter and Setter
     * @return
     */
    public ArrayList<String> getResult() {
        return result;
    }



    public ArrayList<Standard> getStandards() {
        return standards;
    }

    public void setStandards(ArrayList<Standard> standards) {
        this.standards = standards;
    }

    public HashMap<String, Person> getPeople() {
        return people;
    }

    public void setPeople(HashMap<String, Person> people) {
        this.people = people;
    }

    public HashMap<String, Drug> getDrugs() {
        return drugs;
    }

    public void setDrugs(HashMap<String, Drug> drugs) {
        this.drugs = drugs;
    }

    public HashMap<String, Visit> getVisits() {
        return visits;
    }

    public void setVisits(HashMap<String, Visit> visits) {
        this.visits = visits;
    }

    public ArrayList<Prescription> getPrescriptions() {
        return prescriptions;
    }

    public void setPrescriptions(ArrayList<Prescription> prescriptions) {
        this.prescriptions = prescriptions;
    }
    public ArrayList<Double> getPreCal_Cache() {
        return preCal_Cache;
    }

    public void setPreCal_Cache(ArrayList<Double> preCal_Cache) {
        this.preCal_Cache = preCal_Cache;
    }
    public static void setDatamanager(DataManager datamanager) {
        DataManager.datamanager = datamanager;
    }
    public HashMap<String, String> getMaxLines() {
        return maxLines;
    }

    public void setMaxLines(HashMap<String, String> maxLines) {
        this.maxLines = maxLines;
    }

    public void setResult(ArrayList<String> result) {
        this.result = result;
    }
    public Prescription getPrescription(String code){
        Prescription returnPrescription = null;
        for (Prescription pre:this.getPrescriptions()){
            if (pre.getOutpatientCode().equals(code)){
                returnPrescription = pre;
            }
        }
        return returnPrescription;
    }
}