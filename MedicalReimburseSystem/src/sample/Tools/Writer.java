package sample.Tools;

/**
 * @author 马杰生
 * @date 2018-7-30
 * 工具类：将程序中的信息写入到txt
 */
import javafx.collections.ObservableList;
import sample.Model.*;
import java.io.*;
import java.util.Collection;

public class Writer {
    private Writer(){};
    DataManager dataManager = DataManager.getDatamanager();

    /**
     * 获取Writer类的静态实例
     * @return Writer类静态实例
     */
    public static Writer getWriter() {
        return writer;
    }

    public static Writer writer =new Writer();

    /**
     * 对外界txt文件写入程序内人员信息
     * @param personData 人员信息
     * @throws IOException
     */
    public void writePerson(ObservableList<Person> personData) throws IOException {
        OutputStream g = new FileOutputStream("C:/Users/54234/IdeaProjects/NEU_Summer/src/sample/DB/Person.txt");
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(g, "UTF-8"));
        bw.write("");
        g.close();
        OutputStream h = new FileOutputStream("C:/Users/54234/IdeaProjects/NEU_Summer/src/sample/DB/Person.txt");
        BufferedWriter bw2 = new BufferedWriter(new OutputStreamWriter(h, "UTF-8"));

        for (Person person : personData
                ) {
            bw2.write(person.toString() + "\r\n");
            bw2.flush();
        }
        h.close();
    }

    /**
     * 对外界txt文件写入程序内药品信息
     * @param drugData 药品信息
     * @throws IOException
     */
    public void writeDrug(ObservableList<Drug> drugData) throws IOException {
        OutputStream g = new FileOutputStream("C:/Users/54234/IdeaProjects/NEU_Summer/src/sample/DB/Drug.txt");
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(g, "UTF-8"));
        bw.write("");
        g.close();
        OutputStream h = new FileOutputStream("C:/Users/54234/IdeaProjects/NEU_Summer/src/sample/DB/Drug.txt");
        BufferedWriter bw2 = new BufferedWriter(new OutputStreamWriter(h, "UTF-8"));

        for (Drug drug : drugData
                ) {
            bw2.write(drug.toString() + "\r\n");
            bw2.flush();
        }
        h.close();
    }

    /**
     * 对外界txt文件写入程序内就诊信息
     * @param visitData 就诊信息
     * @throws IOException
     */
    public void writeVisit(ObservableList<Visit> visitData) throws IOException {
        OutputStream g = new FileOutputStream("C:/Users/54234/IdeaProjects/NEU_Summer/src/sample/DB/Visit.txt");
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(g, "UTF-8"));
        bw.write("");
        g.close();
        OutputStream h = new FileOutputStream("C:/Users/54234/IdeaProjects/NEU_Summer/src/sample/DB/Visit.txt");
        BufferedWriter bw2 = new BufferedWriter(new OutputStreamWriter(h, "UTF-8"));

        for (Visit visit : visitData
                ) {
            bw2.write(visit.toString() + "\r\n");
            bw2.flush();
        }
        h.close();
    }

    /**
     * 对外界txt文件写入程序内处方信息
     * @param prescriptionData 处方信息
     * @throws IOException
     */
    public void writePrescription(ObservableList<Prescription> prescriptionData) throws IOException {
        OutputStream g = new FileOutputStream("C:/Users/54234/IdeaProjects/NEU_Summer/src/sample/DB/Prescription.txt");
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(g, "UTF-8"));
        bw.write("");
        g.close();
        OutputStream h = new FileOutputStream("C:/Users/54234/IdeaProjects/NEU_Summer/src/sample/DB/Prescription.txt");
        BufferedWriter bw2 = new BufferedWriter(new OutputStreamWriter(h, "UTF-8"));

        for (Prescription prescription : prescriptionData
                ) {
            bw2.write(prescription.toString() + "\r\n");
            bw2.flush();
        }
        h.close();
    }

    /**
     * 对外界txt文件写入程序内结算信息
     * @throws IOException
     */
    public void writeReimburse() throws IOException {
        OutputStream h = new FileOutputStream("C:/Users/54234/IdeaProjects/NEU_Summer/src/sample/DB/Cal.txt",true);
        BufferedWriter bw2 = new BufferedWriter(new OutputStreamWriter(h, "UTF-8"));
        bw2.write(dataManager.getResult().get(1));
        bw2.flush();//否则不能正常输出
        bw2.close();
        h.close();
        }

    /**
     * 对外界txt文件写入程序内预结算信息
     * @throws IOException
     */
    public void writeReimburse_pre()throws IOException {
        OutputStream g = new FileOutputStream("C:/Users/54234/IdeaProjects/NEU_Summer/src/sample/DB/PreCal.txt",true);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(g, "UTF-8"));
        bw.write(dataManager.getResult().get(0));//从datamanager缓存预结算和结算中获取
        bw.flush();
        bw.close();
        g.close();
    }
}
