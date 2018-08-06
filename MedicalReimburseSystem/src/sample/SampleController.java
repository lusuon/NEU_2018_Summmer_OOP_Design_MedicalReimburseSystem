package sample;
/**
 * @author 马杰生
 * @date 2018-7-30
 * 控制器类：实现GUI各控件的操作
 */
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import org.controlsfx.dialog.Dialogs;
import sample.Model.*;
import sample.Tools.*;
import sample.Tools.Writer;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SampleController extends Tab implements Initializable {
    Writer writer= Writer.getWriter() ;
    Checker checker = Checker.getChecker();
    StringToNumberTools tools = StringToNumberTools.getTools();

    /**
     * 设置人员管理界面搜索框为过滤框
     */
    public void setSortedPerson() {
        FilteredList<Person> filteredPerson = new FilteredList<>(personData, p -> true);
        personFilterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredPerson.setPredicate(person -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (person.getpersonID().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                } else if (person.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                }
                return false; // Does not match.
            });
        });
        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Person> sortedPerson = new SortedList<>(filteredPerson);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedPerson.comparatorProperty().bind(personTable.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        personTable.setItems(sortedPerson);
    }

    /**
     * 设置药品管理界面搜索框为过滤框
     */
    public void setSortedDrug() {
        FilteredList<Drug> filteredDrug = new FilteredList<>(drugData, p -> true);
        drugFilterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredDrug.setPredicate(drug -> {
                // If filter text is empty, display all drugs.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every drug with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (drug.getDrugID().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                } else if (drug.getDrugName().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                }
                return false; // Does not match.
            });
        });
        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Drug> sortedDrug = new SortedList<>(filteredDrug);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedDrug.comparatorProperty().bind(drugTable.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        drugTable.setItems(sortedDrug);
    }

    /**
     * 设置门诊信息管理界面搜索框为过滤框
     */
    public void setSortedVisit() {
        FilteredList<Visit> filteredVisit = new FilteredList<>(visitData, p -> true);
        VisitFilterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredVisit.setPredicate(visit -> {
                // If filter text is empty, display all visits.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every visit with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (visit.getPersonID().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                } else {
                    return false; // Filter matches last name.
                }
            });
        });
        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Visit> sortedVisit = new SortedList<>(filteredVisit);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedVisit.comparatorProperty().bind(visitTable.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        visitTable.setItems(sortedVisit);
    }

    /**
     * 设置处方管理界面搜索框为过滤框
     */
    public void setSortedPrescription() {
        FilteredList<Prescription> filteredPrescription = new FilteredList<>(prescriptionData, p -> true);
        PrescriptionFilterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredPrescription.setPredicate(prescription -> {
                // If filter text is empty, display all visits.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every visit with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (prescription.getOutpatientCode().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                }
                return false; // Does not match.
            });
        });
        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Prescription> sortedPrescription = new SortedList<>(filteredPrescription);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedPrescription.comparatorProperty().bind(prescriptionTable.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        prescriptionTable.setItems(sortedPrescription);
    }

    /**
     * 初始化GUI内容
     */
    @FXML
    private void initialize() {
        // TODO (don't really need to do anything here).
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Initalize the attribute
        try {
                DataManager.getDatamanager().loadAttribute();
            } catch (IOException e) {
                e.printStackTrace();
            }


        // Initialize the person table with the two columns.

        try {
            DataManager.getDatamanager().loadPeople();
            for (String key : DataManager.getDatamanager().getPeople().keySet()
                    ) {
                personData.add(DataManager.getDatamanager().getPeople().get(key));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        idColumn.setCellValueFactory(cellData -> cellData.getValue().personIDProperty());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        personTable.setItems(this.getPersonData());


        // Listen for selection changes and show the person details when changed.
        personTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showPersonDetails(newValue));


        FilteredList<Person> filteredPerson = new FilteredList<>(personData, p -> true);
        personFilterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredPerson.setPredicate(person -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (person.getpersonID().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                } else if (person.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                }
                return false; // Does not match.
            });
        });
        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Person> sortedPerson = new SortedList<>(filteredPerson);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedPerson.comparatorProperty().bind(personTable.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        personTable.setItems(sortedPerson);


        //drug
        try {
            DataManager.getDatamanager().loadDrugs();
            for (String key : DataManager.getDatamanager().getDrugs().keySet()
                    ) {
                drugData.add(DataManager.getDatamanager().getDrugs().get(key));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        drugIDColumn.setCellValueFactory(cellData -> cellData.getValue().drugIDProperty());
        drugNameColumn.setCellValueFactory(cellData -> cellData.getValue().drugNameProperty());
        drugTable.setItems(this.getDrugData());
        // Clear drug details.
        showDrugDetails(null);
        // Listen for selection changes and show the drug details when changed.
        drugTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showDrugDetails(newValue));


        FilteredList<Drug> filteredDrug = new FilteredList<>(drugData, p -> true);
        drugFilterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredDrug.setPredicate(drug -> {
                // If filter text is empty, display all drugs.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every drug with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (drug.getDrugID().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                } else if (drug.getDrugName().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                }
                return false; // Does not match.
            });
        });
        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Drug> sortedDrug = new SortedList<>(filteredDrug);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedDrug.comparatorProperty().bind(drugTable.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        drugTable.setItems(sortedDrug);


        //Visit

        // Initialize the person table with the two columns.

        try {
            DataManager.getDatamanager().loadVisits();
            for (String key : DataManager.getDatamanager().getVisits().keySet()
                    ) {
                visitData.add(DataManager.getDatamanager().getVisits().get(key));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        visit_personIDColumn.setCellValueFactory(cellData -> cellData.getValue().outpatientCodeProperty());
        visitTable.setItems(this.getVisitData());
        // Clear person details.
        showVisitDetails(null);
        // Listen for selection changes and show the person details when changed.
        visitTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showVisitDetails(newValue));


        FilteredList<Visit> filteredVisit = new FilteredList<>(visitData, p -> true);
        VisitFilterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredVisit.setPredicate(visit -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (visit.getPersonID().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                } else{
                    return false; // Does not match.
                }

            });
        });
        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Visit> sortedVisit = new SortedList<>(filteredVisit);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedVisit.comparatorProperty().bind(visitTable.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        visitTable.setItems(sortedVisit);

        //Prescription

        // Initialize the prescription table with the two columns.
        /*
        prescriptionData.add(new Prescription("ZY01","0"));
        prescriptionData.add(new Prescription("ZY02","0"));
        prescriptionData.add(new Prescription("ZY03","0"));
        prescriptionData.add(new Prescription("ZY04","0"));
        prescriptionData.add(new Prescription("ZY05","0"));
        prescriptionData.get(0).getItems().add(new PrescriptionItem("A001","5.00","1","5"));
        */

        try {
            DataManager.getDatamanager().loadPrescription();
            for (Prescription prescription : DataManager.getDatamanager().getPrescriptions()
                    ) {
                prescriptionData.add(prescription);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        prescription_outpateintCodeColumn.setCellValueFactory(cellData -> cellData.getValue().pre_outpatientCodeProperty());
        prescriptionItem_drugCodeColumn.setCellValueFactory(cellData -> cellData.getValue().drugCodeProperty());
        prescriptionItem_drugPriceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty());
        prescriptionItem_drugQuantityColumn.setCellValueFactory(cellData -> cellData.getValue().quantityProperty());
        prescriptionItem_totalColumn.setCellValueFactory(cellData -> cellData.getValue().itemTotalProperty());
        prescriptionTable.setEditable(true);
        prescriptionItemTable.setEditable(true);
        prescription_outpateintCodeColumn.setCellFactory(TextFieldTableCell.<Prescription>forTableColumn());
        prescription_outpateintCodeColumn.setOnEditCommit(
                (TableColumn.CellEditEvent<Prescription, String> t) -> {
                    ((Prescription) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setOutpatientCode(t.getNewValue());
                });
        //使单元格可修改
        prescriptionItem_drugCodeColumn.setCellFactory(TextFieldTableCell.<PrescriptionItem>forTableColumn());
        prescriptionItem_drugCodeColumn.setOnEditCommit(
                (TableColumn.CellEditEvent<PrescriptionItem, String> t) -> {
                    ((PrescriptionItem) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setDrugCode(t.getNewValue());
                });
        prescriptionItem_drugPriceColumn.setCellFactory(TextFieldTableCell.<PrescriptionItem>forTableColumn());
        prescriptionItem_drugPriceColumn.setOnEditCommit(
                (TableColumn.CellEditEvent<PrescriptionItem, String> t) -> {
                    ((PrescriptionItem) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setPrice(t.getNewValue());
                });
        prescriptionItem_drugQuantityColumn.setCellFactory(TextFieldTableCell.<PrescriptionItem>forTableColumn());
        prescriptionItem_drugQuantityColumn.setOnEditCommit(
                (TableColumn.CellEditEvent<PrescriptionItem, String> t) -> {
                    ((PrescriptionItem) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setQuantity(t.getNewValue());
                });
        prescriptionItem_totalColumn.setCellFactory(TextFieldTableCell.<PrescriptionItem>forTableColumn());
        prescriptionItem_totalColumn.setOnEditCommit(
                (TableColumn.CellEditEvent<PrescriptionItem, String> t) -> {
                    ((PrescriptionItem) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setItemTotal(t.getNewValue());
                });


        // Clear person details.
        showPrescriptionDetails(null);
        // Listen for selection changes and show the person details when changed.
        prescriptionTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showPrescriptionDetails(newValue));


        FilteredList<Prescription> filteredPrescription = new FilteredList<>(prescriptionData, p -> true);
        PrescriptionFilterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredPrescription.setPredicate(prescription -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (prescription.getOutpatientCode().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                } else if (prescription.getOutpatientCode().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                }
                return false; // Does not match.
            });
        });
        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Prescription> sortedPrescription = new SortedList<>(filteredPrescription);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedPrescription.comparatorProperty().bind(prescriptionTable.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        prescriptionTable.setItems(sortedPrescription);


        //设置为复选：

        personTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        drugTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        visitTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        prescriptionTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        prescriptionItemTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);


    }

    /**
     * PersonTab
     */

    @FXML
    private TableView<Person> personTable;
    @FXML
    private TableColumn<Person, String> idColumn;
    @FXML
    private TableColumn<Person, String> nameColumn;
    @FXML
    private TextField personIDField;
    @FXML
    private TextField documentTypeTextField;
    @FXML
    private TextField documentCodeTextField;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField genderTextField;
    @FXML
    private TextField nationalityTextField;
    @FXML
    private TextField birthDateTextField;
    @FXML
    private TextField medicalStaffTypeTextField;
    @FXML
    private TextField personFilterField;
    @FXML
    final ComboBox genderComboBox = new ComboBox();
    @FXML
    final ComboBox documentTupeComboBox = new ComboBox();
    //private ObservableList<Person> personData =  FXCollections.observableArrayList();
    private ObservableList<Person> personData = FXCollections.observableArrayList();

    public ObservableList<Person> getPersonData() {
        return personData;
    }

    /**
     * 实现选择表格后显示详细信息
     * @param person
     */
    private void showPersonDetails(Person person) {
        if (person != null) {
            // Fill the labels with info from the person object.
            birthDateTextField.setText(person.getBirthDate());
            documentCodeTextField.setText(person.getDocumentCode());
            documentTypeTextField.setText(person.getDocumentType());
            genderTextField.setText(person.getGender());
            medicalStaffTypeTextField.setText(person.getMedicalStaffType());
            nationalityTextField.setText(person.getNationality());
            nameTextField.setText(person.getName());
            personIDField.setText(person.getpersonID());
        } else {
            // Person is null, remove all the text.
            birthDateTextField.setText("");
            documentCodeTextField.setText("");
            documentTypeTextField.setText("");
            genderTextField.setText("");
            medicalStaffTypeTextField.setText("");
            nationalityTextField.setText("");
            nameTextField.setText("");
            personIDField.setText("");
        }
    }

    /**
     * 人员信息管理界面添加按钮
     * @throws IOException
     */
    //
    @FXML
    private void handleAddPerson() throws IOException {
        boolean isPersonID = checker.isPersonID(personIDField.getText());
        boolean isPersonIDRepeated = checker.isPersonRepeated(personIDField.getText());
        boolean isDate = checker.isDate(birthDateTextField.getText());
        boolean isMedicalStaffType = checker.isMedicalStaffType(medicalStaffTypeTextField.getText());
        if (isPersonIDRepeated) {
            Alert error = new Alert(Alert.AlertType.ERROR, "人员ID重复");
            error.showAndWait();
            personIDField.requestFocus();
        } else if (!isPersonID) {
            Alert error = new Alert(Alert.AlertType.ERROR, "请按格式输入正确ID");
            error.showAndWait();
            personIDField.requestFocus();
        } else if (!isMedicalStaffType) {
            Alert error = new Alert(Alert.AlertType.ERROR, "请按格式输入医疗人员类别");
            error.showAndWait();
            medicalStaffTypeTextField.requestFocus();
        } else if (!isDate) {
            Alert error = new Alert(Alert.AlertType.ERROR, "请按格式输入正确日期");
            error.showAndWait();
            birthDateTextField.requestFocus();
        } else {
            Person addPerson = new Person(personIDField.getText(), documentTypeTextField.getText(), documentCodeTextField.getText(), nameTextField.getText(), genderTextField.getText(), nationalityTextField.getText(), birthDateTextField.getText(), medicalStaffTypeTextField.getText());
            personData.add(addPerson);
        }
    }

    /**
     * 人员信息管理界面删除按钮
     * @throws IOException
     */
    //当使用搜索框后，需进行修改，以避免错误
    @FXML
    private void handleDeletePerson() throws IOException {
        int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            personTable.setItems(personData);
            personTable.getItems().remove(selectedIndex);
            //int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
            //personTable.getItems().remove(selectedIndex);
            setSortedPerson();
        } else {
            Alert error = new Alert(Alert.AlertType.ERROR, "表格已空，无法删除");
            error.showAndWait();
        }
    }

    /**
     * 人员信息管理界面修改按钮
     * @throws IOException
     */
    @FXML
    private void handleChangePerson() throws IOException {
        boolean isPersonID = checker.isPersonID(personIDField.getText());
        boolean isPersonIDRepeated = checker.isPersonRepeated(personIDField.getText());
        boolean isDate = checker.isDate(birthDateTextField.getText());
        boolean isMedicalStaffType = checker.isMedicalStaffType(medicalStaffTypeTextField.getText());
        boolean isGender = checker.isGender(genderTextField.getText());
        boolean isDocumentType = checker.isDocumentType(documentTypeTextField.getText());

        if (!isPersonID) {
            Alert error = new Alert(Alert.AlertType.ERROR, "请按格式输入正确ID");
            error.showAndWait();
            personIDField.requestFocus();
        } else if (!isMedicalStaffType) {
            Alert error = new Alert(Alert.AlertType.ERROR, "请按格式输入医疗人员类别");
            error.showAndWait();
            medicalStaffTypeTextField.requestFocus();
        } else if (!isDate) {
            Alert error = new Alert(Alert.AlertType.ERROR, "请按格式输入正确日期");
            error.showAndWait();
            birthDateTextField.requestFocus();
        }else if (!isGender) {
            Alert error = new Alert(Alert.AlertType.ERROR, "请输入合法的性别");
            error.showAndWait();
            genderTextField.requestFocus();

        }else if (!isDocumentType) {
            Alert error = new Alert(Alert.AlertType.ERROR, "请输入支持的证件类型");
            error.showAndWait();
            drugNameField.requestFocus();

        }else {
            personTable.getSelectionModel().getSelectedItem().setpersonID(personIDField.getText());
            personTable.getSelectionModel().getSelectedItem().setName(nameTextField.getText());
            personTable.getSelectionModel().getSelectedItem().setBirthDate(birthDateTextField.getText());
            personTable.getSelectionModel().getSelectedItem().setDocumentCode(documentCodeTextField.getText());
            personTable.getSelectionModel().getSelectedItem().setDocumentType(documentTypeTextField.getText());
            personTable.getSelectionModel().getSelectedItem().setNationality(nationalityTextField.getText());
            personTable.getSelectionModel().getSelectedItem().setMedicalStaffType(medicalStaffTypeTextField.getText());
            personTable.getSelectionModel().getSelectedItem().setGender(genderTextField.getText());
        }
    }

    /**
     * 人员信息管理界面程序内信息写入外置储存按钮
     * @throws IOException
     */
    @FXML
    private void handleSavePerson() throws IOException {
        Writer.getWriter().writePerson(personData);
    }




    /**
     * Drug
     */

    @FXML
    private TableView<Drug> drugTable;
    @FXML
    private TableColumn<Drug, String> drugIDColumn;
    @FXML
    private TableColumn<Drug, String> drugNameColumn;
    @FXML
    private TextField drugIDField;
    @FXML
    private TextField drugNameField;
    @FXML
    private TextField maxPriceTextField;
    @FXML
    private TextField unitTextField;
    @FXML
    private TextField payLevelTextField;
    @FXML
    private TextField hospitalLevelTextField;
    @FXML
    private TextField drugFilterField;
    private ObservableList<Drug> drugData = FXCollections.observableArrayList();
    
    public ObservableList<Drug> getDrugData() {
        return drugData;
    }

    /**
     * 选择表格中项目后显示详细信息
     * @param drug
     */
    private void showDrugDetails(Drug drug) {
        if (drug != null) {
            // Fill the labels with info from the drug object.
            drugNameField.setText(drug.getDrugName());
            unitTextField.setText(drug.getUnit());
            maxPriceTextField.setText(drug.getMaxPrice());
            payLevelTextField.setText(drug.getPayLevel());
            hospitalLevelTextField.setText(drug.getHospitalLevel());
            drugIDField.setText(drug.getDrugID());
        } else {
            // Drug is null, remove all the text.
            drugNameField.setText("");
            unitTextField.setText("");
            maxPriceTextField.setText("");
            payLevelTextField.setText("");
            hospitalLevelTextField.setText("");
            drugIDField.setText("");
        }
    }


    /**
     * 药品信息管理界面添加按钮
     * @throws IOException
     */
    //添加按钮
    @FXML
    private void handleAddDrug() throws IOException {
        boolean isMaxPrice = tools.isStrToDouble(maxPriceTextField.getText());
        boolean isDrugIDRepeated = checker.isPersonRepeated(drugIDField.getText());
        boolean isPayLevel = checker.isPayLevel(payLevelTextField.getText());
        boolean isHospitalLevel = checker.isHospitalLevel(hospitalLevelTextField.getText());
        if (isDrugIDRepeated == true) {
            Alert error = new Alert(Alert.AlertType.ERROR, "药品ID重复");
            error.showAndWait();
            drugIDField.requestFocus();
        } else if (!isMaxPrice) {
            Alert error = new Alert(Alert.AlertType.ERROR, "请按格式输入最大限价");
            error.showAndWait();
            maxPriceTextField.requestFocus();
        } else if (!isPayLevel) {
            Alert error = new Alert(Alert.AlertType.ERROR, "请按格式输入收费项目等级");
            error.showAndWait();
            payLevelTextField.requestFocus();
        } else if (!isHospitalLevel) {
            Alert error = new Alert(Alert.AlertType.ERROR, "请按格式输入医院等级");
            error.showAndWait();
            hospitalLevelTextField.requestFocus();
        } else {
            Drug addDrug = new Drug(drugIDField.getText(), drugNameField.getText(), maxPriceTextField.getText(), unitTextField.getText(), payLevelTextField.getText(), hospitalLevelTextField.getText());
            drugData.add(addDrug);
        }
        
    }

    /**
     * 药品信息管理界面删除按钮
     * @throws IOException
     */
    //当使用搜索框后，需进行修改，以避免错误
    @FXML
    private void handleDeleteDrug() throws IOException {
        int selectedIndex = drugTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            drugTable.setItems(drugData);
            ObservableList<Drug> list = drugTable.getSelectionModel().getSelectedItems();
            drugTable.getItems().removeAll(list);
            setSortedDrug();
        } else {
            Alert error = new Alert(Alert.AlertType.ERROR, "表格已空，无法删除");
            error.showAndWait();
        }
    }

    /**
     * 药品信息管理界面修改按钮
     */
    @FXML
    private void handleChangeDrug() {
        boolean isMaxPrice = tools.isStrToDouble(maxPriceTextField.getText());
        boolean isDrugIDRepeated = checker.isPersonRepeated(drugIDField.getText());
        boolean isPayLevel = checker.isPayLevel(payLevelTextField.getText());
        boolean isHospitalLevel = checker.isHospitalLevel(hospitalLevelTextField.getText());
        if (!isMaxPrice) {
            Alert error = new Alert(Alert.AlertType.ERROR, "请按格式输入最大限价");
            error.showAndWait();
            maxPriceTextField.requestFocus();
        } else if (!isPayLevel) {
            Alert error = new Alert(Alert.AlertType.ERROR, "请按格式输入收费项目等级");
            error.showAndWait();
            payLevelTextField.requestFocus();
        } else if (!isHospitalLevel) {
            Alert error = new Alert(Alert.AlertType.ERROR, "请按格式输入医院等级");
            error.showAndWait();
            hospitalLevelTextField.requestFocus();
        } else {
            drugTable.getSelectionModel().getSelectedItem().setDrugID(drugIDField.getText());
            drugTable.getSelectionModel().getSelectedItem().setDrugName( drugNameField.getText());
            drugTable.getSelectionModel().getSelectedItem().setUnit(unitTextField.getText());
            drugTable.getSelectionModel().getSelectedItem().setMaxPrice(maxPriceTextField.getText());
            drugTable.getSelectionModel().getSelectedItem().setHospitalLevel(hospitalLevelTextField.getText());
            drugTable.getSelectionModel().getSelectedItem().setPayLevel(payLevelTextField.getText());
        }
    }

    /**
     * 药品信息管理界面内信息写入外置储存按钮
     * @throws IOException
     */
    @FXML
    private void handleSaveDrug() throws IOException {
        Writer.getWriter().writeDrug(drugData);
    }


    /**
     * VisitTab
     */
    @FXML
    private TableView<Visit> visitTable;
    @FXML
    private TableColumn<Visit, String> visit_personIDColumn;
    @FXML
    private TableColumn<Visit, String> visit_nameColumn;
    @FXML
    private TextField visit_personIDField;
    @FXML
    private TextField visit_outpatientCodeTextField;
    @FXML
    private TextField visit_hospitalLevelTextField;
    @FXML
    private TextField visit_hospitalCodeTextField;
    @FXML
    private TextField visit_hospitalNameTextField;
    @FXML
    private TextField inDateTextField;
    @FXML
    private TextField outDateTextField;
    @FXML
    private TextField outReasonTextField;
    @FXML
    private TextField VisitFilterField;

    private ObservableList<Visit> visitData = FXCollections.observableArrayList();
    public ObservableList<Visit> getVisitData() {
        return visitData;
    }

    /**
     * 选择表格后显示详细信息
     * @param visit
     */
    private void showVisitDetails(Visit visit) {
        if (visit != null) {
            // Fill the labels with info from the visit object.
            inDateTextField.setText(visit.getInDate());
            visit_hospitalLevelTextField.setText(visit.getHospitalLevel());
            visit_outpatientCodeTextField.setText(visit.getOutpatientCode());
            visit_hospitalCodeTextField.setText(visit.getHospitalCode());
            outDateTextField.setText(visit.getOutDate());
            visit_hospitalNameTextField.setText(visit.getHospitalName());
            visit_personIDField.setText(visit.getPersonID());
            outReasonTextField.setText(visit.getOutReason());
        } else {
            // Visit is null, remove all the text.
            inDateTextField.setText("");
            visit_hospitalLevelTextField.setText("");
            visit_outpatientCodeTextField.setText("");
            visit_hospitalCodeTextField.setText("");
            outDateTextField.setText("");
            visit_hospitalNameTextField.setText("");
            visit_personIDField.setText("");
            outReasonTextField.setText("");
        }
    }

    /**
     * 门诊信息管理界面信息添加按钮
     * @throws IOException
     */
    @FXML
    private void handleAddVisit() throws IOException {
        boolean isPersonID = checker.isPersonID(visit_personIDField.getText());
        boolean isHospitalCode = tools.isStrToInt(visit_hospitalCodeTextField.getText());
        boolean isOutpatientIDRepeated = checker.isVisitRepeated(visit_outpatientCodeTextField.getText());
        boolean isInDate = checker.isDate(inDateTextField.getText());
        boolean isOutDate = checker.isDate(outDateTextField.getText());
        if (isOutpatientIDRepeated){
            Alert error = new Alert(Alert.AlertType.ERROR, "门诊号重复");
            error.showAndWait();
            visit_outpatientCodeTextField.requestFocus();
        }else if(!isPersonID) {
            Alert error = new Alert(Alert.AlertType.ERROR, "请按正确格式输入ID");
            error.showAndWait();
            visit_personIDField.requestFocus();
        }else if (!isHospitalCode) {
            Alert error = new Alert(Alert.AlertType.ERROR, "请按格式输入医院编号");
            error.showAndWait();
            visit_hospitalCodeTextField.requestFocus();
        } else if (!isInDate) {
            Alert error = new Alert(Alert.AlertType.ERROR, "请按格式输入入院日期");
            error.showAndWait();
            inDateTextField.requestFocus();
        } else if (!isOutDate) {
            Alert error = new Alert(Alert.AlertType.ERROR, "请按格式输入出院日期");
            error.showAndWait();
            outDateTextField.requestFocus();
        } else {
            Visit addVisit = new Visit(visit_personIDField.getText(), visit_outpatientCodeTextField.getText(),inDateTextField.getText(), outDateTextField.getText(), visit_hospitalLevelTextField.getText(), visit_hospitalCodeTextField.getText(), visit_hospitalNameTextField.getText(),  outReasonTextField.getText());
            visitData.add(addVisit);
        }
    }

    /**
     * 门诊信息管理界面内信息删除按钮
     * @throws IOException
     */
    @FXML
    private void handleDeleteVisit() throws IOException {
        int selectedIndex = visitTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            visitTable.setItems(visitData);
            //int selectedIndex = visitTable.getSelectionModel().getSelectedIndex();
            //visitTable.getItems().remove(selectedIndex);
            ObservableList<Visit> list = visitTable.getSelectionModel().getSelectedItems();
            visitTable.getItems().removeAll(list);
            setSortedVisit();
        } else {
            Alert error = new Alert(Alert.AlertType.ERROR, "表格已空，无法删除");
            error.showAndWait();
        }
    }


    /**
     * 门诊信息管理界面内信息修改按钮
     * @throws IOException
     */
    @FXML
    private void handleChangeVisit() throws IOException {
        boolean isPersonID = checker.isPersonID(visit_personIDField.getText());
        boolean isHospitalCode = tools.isStrToInt(visit_hospitalCodeTextField.getText());
        boolean isOutpatientIDRepeated = checker.isVisitRepeated(visit_outpatientCodeTextField.getText());
        boolean isInDate = checker.isDate(inDateTextField.getText());
        boolean isOutDate = checker.isDate(outDateTextField.getText());
        if (!isPersonID) {
            Alert error = new Alert(Alert.AlertType.ERROR, "请按正确格式输入ID");
            error.showAndWait();
            visit_personIDField.requestFocus();
        }else if (!isHospitalCode) {
            Alert error = new Alert(Alert.AlertType.ERROR, "请按格式输入医院编号");
            error.showAndWait();
            visit_hospitalCodeTextField.requestFocus();
        } else if (!isInDate) {
            Alert error = new Alert(Alert.AlertType.ERROR, "请按格式输入入院日期");
            error.showAndWait();
            inDateTextField.requestFocus();
        } else if (!isOutDate) {
            Alert error = new Alert(Alert.AlertType.ERROR, "请按格式输入出院日期");
            error.showAndWait();
            outDateTextField.requestFocus();
        } else {
            visitTable.getSelectionModel().getSelectedItem().setPersonID(visit_personIDField.getText());
            visitTable.getSelectionModel().getSelectedItem().setInDate(inDateTextField.getText());
            visitTable.getSelectionModel().getSelectedItem().setHospitalLevel(visit_hospitalLevelTextField.getText());
            visitTable.getSelectionModel().getSelectedItem().setOutpatientCode(visit_outpatientCodeTextField.getText());
            visitTable.getSelectionModel().getSelectedItem().setHospitalName(visit_hospitalNameTextField.getText());
            visitTable.getSelectionModel().getSelectedItem().setOutDate(outDateTextField.getText());
            visitTable.getSelectionModel().getSelectedItem().setHospitalCode(visit_hospitalCodeTextField.getText());
            visitTable.getSelectionModel().getSelectedItem().setOutReason(outReasonTextField.getText());
        }
    }

    /**
     * 门诊信息管理界面内信息写入外置储存按钮
     * @throws IOException
     */
    @FXML
    private void handleSaveVisit()throws IOException{
        Writer.getWriter().writeVisit(visitData);
    }



    /**
     * PrescriptionTab
     */
    @FXML
    private TableView<Prescription> prescriptionTable;//可检索，过滤
    @FXML
    private TableView<PrescriptionItem> prescriptionItemTable;
    @FXML
    private TableColumn<Prescription, String> prescription_outpateintCodeColumn;
    @FXML
    private TableColumn<PrescriptionItem, String> prescriptionItem_drugCodeColumn;
    @FXML
    private TableColumn<PrescriptionItem, String> prescriptionItem_drugNameColumn;
    @FXML
    private TableColumn<PrescriptionItem, String> prescriptionItem_drugPriceColumn;
    @FXML
    private TableColumn<PrescriptionItem, String> prescriptionItem_drugQuantityColumn;
    @FXML
    private TableColumn<PrescriptionItem, String> prescriptionItem_totalColumn;
    @FXML
    private TextArea prescription_total;
    @FXML
    private TextField PrescriptionFilterField;

    private ObservableList<Prescription> prescriptionData = FXCollections.observableArrayList();

    public ObservableList<Prescription> getPrescriptionData() {
        return prescriptionData;
    }

    private ObservableList<PrescriptionItem> prescriptionItemData = FXCollections.observableArrayList();

    public ObservableList<PrescriptionItem> getPrescriptionItemData() {
        return prescriptionItemData;
    }

    /**
     * 选择表格后显示详细信息
     * @param prescription
     */
    private void showPrescriptionDetails(Prescription prescription) {
        if (prescription != null) {
            // Fill the labels with info from the visit object.
            prescriptionItemData.clear();
            for (PrescriptionItem item : prescription.getItems()
                    ) {
                prescriptionItemData.add(item);
            }
            prescriptionItemTable.setItems(prescriptionItemData);
        } else {
            // Visit is null, remove all the text.
            prescriptionItemTable.setItems(null);
        }
    }

    /**
     * 处方信息管理界面内信息修改按钮
     * @throws IOException
     */
    @FXML
    private void handleChangePrescription() throws IOException {
        System.out.println("Clearing");
        OutputStream clearTxt = new FileOutputStream("C:/Users/54234/IdeaProjects/NEU_Summer/src/sample/DB/Prescription.txt");
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(clearTxt, "UTF-8"));
        bw.write("");
        clearTxt.close();
        System.out.println("Rewriting");
        OutputStream reWriteTxt = new FileOutputStream("C:/Users/54234/IdeaProjects/NEU_Summer/src/sample/DB/Prescription.txt");
        BufferedWriter bw2 = new BufferedWriter(new OutputStreamWriter(reWriteTxt, "UTF-8"));
        System.out.println(prescriptionData);
        for (Prescription prescription : prescriptionData
                ) {
            bw2.write(prescription.toString());
            bw2.flush();
        }
        reWriteTxt.close();
    }

    /**
     * 处方信息管理界面内信息添加按钮
     * @throws IOException
     */
    @FXML
    private void handleAddPrescription() throws IOException {
        Prescription addPrescription = new Prescription("请输入门诊号", "0");
        prescriptionData.add(addPrescription);
    }

    /**
     * 处方子项目信息管理界面内信息添加按钮
     * @throws IOException
     */
    @FXML
    private void handleAddPrescriptionItem() throws IOException {
        PrescriptionItem addPrescriptionItem = new PrescriptionItem("请输入药品编码", "0", "0");
        prescriptionItemData.add(addPrescriptionItem);
        prescriptionTable.getSelectionModel().getSelectedItem().getItems().add(addPrescriptionItem);
    }

    /**
     * 处方信息管理界面内信息删除按钮
     * @throws IOException
     */
    //删除按钮,当使用搜索框后，需进行修改，以避免错误
    @FXML
    private void handleDeletePrescription() throws IOException {
        prescriptionTable.setItems(prescriptionData);
        //int selectedIndex = prescriptionTable.getSelectionModel().getSelectedIndex();
        //prescriptionTable.getItems().remove(selectedIndex);
        ObservableList<Prescription> list = prescriptionTable.getSelectionModel().getSelectedItems();
        prescriptionTable.getItems().removeAll(list);
        setSortedPrescription();
    }

    /**
     * 处方子项目信息管理界面内信息添加按钮
     * @throws IOException
     */
    @FXML
    private void handleDeletePrescriptionItem() throws IOException {
        int selectedIndex = prescriptionItemTable.getSelectionModel().getSelectedIndex();
        //以下两行不可调换次序，先修改实际数据，再变更前端数据
        prescriptionTable.getSelectionModel().getSelectedItem().getItems().remove(prescriptionItemTable.getSelectionModel().getSelectedItem());
        prescriptionItemTable.getItems().remove(selectedIndex);
    }


    //报销界面
    @FXML
    private TableView<Prescription> reimburse_prescriptionTable;//可检索，过滤
    @FXML
    private TableView<PrescriptionItem> reimburse_prescriptionItemTable;
    @FXML
    private TableColumn<Prescription, String> reimburse_prescription_outpateintCodeColumn;
    @FXML
    private TableColumn<PrescriptionItem, String> reimburse_prescriptionItem_drugCodeColumn;
    @FXML
    private TableColumn<PrescriptionItem, String> reimburse_prescriptionItem_drugNameColumn;
    @FXML
    private TableColumn<PrescriptionItem, String> reimburse_prescriptionItem_drugPriceColumn;
    @FXML
    private TableColumn<PrescriptionItem, String> reimburse_prescriptionItem_drugQuantityColumn;
    @FXML
    private TableColumn<PrescriptionItem, String> reimburse_prescriptionItem_totalColumn;
    @FXML
    private TextArea reimburse_prescription_total;
    @FXML
    private TextField reimburse_PrescriptionFilterField;

    private ObservableList<Prescription> reimburse_prescriptionData = FXCollections.observableArrayList();

    public ObservableList<Prescription> reimburse_getPrescriptionData() {
        return prescriptionData;
    }

    private ObservableList<PrescriptionItem> reimburse_prescriptionItemData = FXCollections.observableArrayList();

    public ObservableList<PrescriptionItem> reimburse_getPrescriptionItemData() {
        return prescriptionItemData;
    }


    /**
     * 处方信息管理界面内展示详细信息
     * @param prescription
     */
    private void reimburse_showPrescriptionDetails(Prescription prescription) {
        if (prescription != null) {
            // Fill the labels with info from the visit object.
            prescriptionItemData.clear();
            for (PrescriptionItem item : prescription.getItems()
                    ) {
                prescriptionItemData.add(item);
            }
            prescriptionItemTable.setItems(prescriptionItemData);
        } else {
            // Visit is null, remove all the text.
            prescriptionItemTable.setItems(null);
        }
    }
    @FXML
    private TextArea preview;

    /**
     * 处方信息管理界面内预结算/结算按钮
     */
    @FXML
    private void handleReimburse() {
        Calculator calculator = new Calculator();
        Prescription selectedPrescription = prescriptionTable.getSelectionModel().getSelectedItem();
        preview.setText(calculator.mainCalculate(selectedPrescription));
        if(selectedPrescription.getStatus()==2){
            Alert error = new Alert(Alert.AlertType.ERROR, "已完成结算，无需再执行");
            error.showAndWait();
            personIDField.requestFocus();
        }
    }

    /**
     * 处方信息管理界面内输出预结算结果到外置储存按钮
     * @throws IOException
     */
    @FXML
    private void handlOutputPreCal() throws IOException {
        System.out.println("Start writing pre");
        writer.writeReimburse_pre();
    }

    /**
     * 处方信息管理界面内输出结算结果到外置储存按钮
     * @throws IOException
     */
    @FXML
    private void handleOutputCal() throws IOException {
        System.out.println("Start writing cal");
        writer.writeReimburse();
    }
}