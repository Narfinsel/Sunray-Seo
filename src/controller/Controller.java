package controller;

import datamodel.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.controlsfx.control.textfield.TextFields;


import java.io.File;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    public String FILE_LOCATION;
    public String FILE_LOCATION_SAVE;
    public Listwork listwork;
    public VirtualWorkbook virtualWorkbook;

    @FXML private ComboBox <BloggerData> comboBox_BlogData      = new ComboBox<>();
    @FXML private ComboBox <LinkTarget> comboBox_LinkTargets    = new ComboBox<>();
    @FXML private TableView <LinkTarget> tableView_LinkTarget   = new TableView<>();
    @FXML private TextField textField_blogName                  = new TextField();
    @FXML private TextField textField_blogWebsiteURL            = new TextField();
    @FXML private TextArea textArea_blogSiteDescription         = new TextArea();
    @FXML private TextArea textArea_blogValuesInfo              = new TextArea();
    @FXML private TextArea textArea_blogTagcrowd                = new TextArea();
    @FXML private TextField textField_blogOwner                 = new TextField();
    @FXML private TextField textField_blogEmailContact          = new TextField();
    @FXML private TextField textField_blogWhenToSend            = new TextField();
    @FXML private TextArea textArea_blogStrategiesIntel         = new TextArea();
    @FXML private TextArea textArea_blogCommentsOnSite          = new TextArea();
    @FXML private TextField textField_SearchBlog                = new TextField();
    @FXML private TextField textField_SearchLinkTarget          = new TextField();
    @FXML private TextArea textArea_blLtEmailThread             = new TextArea();
    @FXML private Button button_OpenFile            = new Button();
    @FXML private Button button_AddNewBlog          = new Button();
    @FXML private Button button_RemoveBlog          = new Button();
    @FXML private Button button_AppendEmail         = new Button();


    @FXML private ComboBox <Article>    comboBox_Articles           = new ComboBox<>();
    @FXML private ComboBox <BloggerData> comboBox_CampaignBlog      = new ComboBox<>();
    @FXML private ChoiceBox <EnumLtStatus> choiceBox_EnumLtStatus   = new ChoiceBox<>();
    @FXML private TableView <Article> tableView_LT_Articles         = new TableView <>();
    @FXML private TableView <LinkTarget> tableView_LT_Properties    = new TableView<>();
    @FXML private TextField textField_ltPageLinkSourceMine          = new TextField();
    @FXML private TextField textField_ltPageLinkTargetTheirs        = new TextField();
    @FXML private TextField textField_ltDA                          = new TextField();
    @FXML private TextField textField_ltPA                          = new TextField();
    @FXML private TextArea textArea_ltEmailExchanges                = new TextArea();
    @FXML private Button button_ModifyLT            = new Button();
    @FXML private Button button_AddLT               = new Button();
    @FXML private Button button_RemoveLT            = new Button();


    @FXML private TableView <Article> tableView_Articles        = new TableView<>();
    @FXML private TextField textField_artArticleTitle           = new TextField();
    @FXML private TextField textField_artArticlePageLink        = new TextField();
    @FXML private Button button_ModifyArticle      = new Button();
    @FXML private Button button_AddNewArticle      = new Button();
    @FXML private Button button_RemoveArticle      = new Button();

    @FXML private TableView <LinkTarget> tableView_CollectionCampaign = new TableView<>();
    @FXML private TextArea textArea_CollCampaign        = new TextArea();
    @FXML private Button button_ModifyCollCampaign      = new Button();


// ------------------------- METHODS --------------------------

    @FXML
    public void open_File() {
        FileChooser openFileChooser = new FileChooser();
        File selectedFile = openFileChooser.showOpenDialog(null);
        openFileChooser.getExtensionFilters().add( new FileChooser.ExtensionFilter( "Excel File","*.txt" ));

        if (selectedFile != null)   {
            String fileLocation = selectedFile.getAbsolutePath();
            this.setFileLocations( fileLocation );
            this.createListOfBloggers( fileLocation );

            this.populate_ComboBox_BlogData_With_Blogs ();
            this.populate_ComboBox_NewCampaignBlog_With_Blogs();
            this.populate_comboBox_Articles_With_Articles();
            this.populate_TableView_LT_Articles_For_List_LinkTargets();
            this.populate_TableView_Articles_With_Articles();
            this.populate_TableView_CollectionsCampaign_With_LinkTargets();
        }
    }                                                                          // IS WORKING !!!

    @FXML
    public void save_All()   {
        if (this.listwork != null) {
            XSSFWorkbook workbook = this.listwork.workbookFromList();
            this.virtualWorkbook.saveWorkbookToFile(FILE_LOCATION_SAVE, workbook);
        }
    }                                                                         // IS WORKING !!!

    // ----------------- SETUP and INITIALIZE -----------------

    @Override
    public void initialize (URL location, ResourceBundle resources) {



        TableColumn <LinkTarget, String> tableColStatus             = new TableColumn<>("Status");
        tableColStatus.setCellValueFactory( new PropertyValueFactory<>("ltStatus"));
        tableColStatus.setMinWidth(120);

        TableColumn <LinkTarget, String> tableColPageTargetTheirs    = new TableColumn<>("Targeted Page for Link");
        tableColPageTargetTheirs.setCellValueFactory( new PropertyValueFactory<>("ltPageLinkTargetTheirs"));
        tableColPageTargetTheirs.setMinWidth(250);
        this.tableView_LinkTarget.getColumns().addAll( tableColStatus, tableColPageTargetTheirs );

        TableColumn <Article, String> tableCol_LinkTarget_Articles           = new TableColumn<>("Article Title");
        tableCol_LinkTarget_Articles.setCellValueFactory( new PropertyValueFactory<>("artArticleTitle"));
        tableCol_LinkTarget_Articles.setMinWidth(200);
        this.tableView_LT_Articles.getColumns().addAll( tableCol_LinkTarget_Articles );

        TableColumn <LinkTarget, String> tableCol_LinkTarget_Properties_Art             = new TableColumn<>("Link Targets Page");
        tableCol_LinkTarget_Properties_Art.setCellValueFactory( new PropertyValueFactory<>("ltPageLinkTargetTheirs"));
        tableCol_LinkTarget_Properties_Art.setMinWidth(250);
        TableColumn <LinkTarget, String> tableCol_LinkTarget_Properties_DA             = new TableColumn<>("DA");
        tableCol_LinkTarget_Properties_DA.setCellValueFactory( new PropertyValueFactory<>("ltDA"));
        tableCol_LinkTarget_Properties_DA.setMinWidth(50);
        TableColumn <LinkTarget, String> tableCol_LinkTarget_Properties_PA             = new TableColumn<>("PA");
        tableCol_LinkTarget_Properties_PA.setCellValueFactory( new PropertyValueFactory<>("ltPA"));
        tableCol_LinkTarget_Properties_PA.setMinWidth(50);
        TableColumn <LinkTarget, String> tableCol_LinkTarget_Properties_Stat             = new TableColumn<>("Status");
        tableCol_LinkTarget_Properties_Stat.setCellValueFactory( new PropertyValueFactory<>("ltStatus"));
        tableCol_LinkTarget_Properties_Stat.setMinWidth(150);
        this.tableView_LT_Properties.getColumns().addAll( tableCol_LinkTarget_Properties_Art,
                tableCol_LinkTarget_Properties_DA,
                tableCol_LinkTarget_Properties_PA,
                tableCol_LinkTarget_Properties_Stat );

        TableColumn <Article, String> tableCol_Article_Tit             = new TableColumn<>("Title");
        tableCol_Article_Tit.setCellValueFactory( new PropertyValueFactory<>("artArticleTitle"));
        tableCol_Article_Tit.setMinWidth(300);
        TableColumn <Article, String> tableCol_Article_PageLink        = new TableColumn<>("Link");
        tableCol_Article_PageLink.setCellValueFactory( new PropertyValueFactory<>("artPageLinkForArticle"));
        tableCol_Article_PageLink.setMinWidth(350);
        this.tableView_Articles.getColumns().addAll( tableCol_Article_Tit,
                tableCol_Article_PageLink);


        TableColumn <LinkTarget, String> tableCol_ColCampaign_ArticleTit             = new TableColumn<>("Post");
        tableCol_ColCampaign_ArticleTit.setCellValueFactory( new PropertyValueFactory<>("ltArticleTitle"));
        tableCol_ColCampaign_ArticleTit.setMinWidth(150);
        TableColumn <LinkTarget, String> tableCol_ColCampaign_BlogName             = new TableColumn<>("Blog Name");
        tableCol_ColCampaign_BlogName.setCellValueFactory( new PropertyValueFactory<>("ltBloggerName"));
        tableCol_ColCampaign_BlogName.setStyle("-fx-text-fill: #ff7778");
        tableCol_ColCampaign_BlogName.setMinWidth(150);
        TableColumn <LinkTarget, String> tableCol_ColCampaign_LinkTargetPage          = new TableColumn<>("Targeted Page");
        tableCol_ColCampaign_LinkTargetPage.setCellValueFactory( new PropertyValueFactory<>("ltPageLinkTargetTheirs"));
        tableCol_ColCampaign_LinkTargetPage.setStyle("-fx-text-fill: #ff7778");
        tableCol_ColCampaign_LinkTargetPage.setMinWidth(250);
        TableColumn <LinkTarget, String> tableCol_ColCampaign_DA             = new TableColumn<>("DA");
        tableCol_ColCampaign_DA.setCellValueFactory( new PropertyValueFactory<>("ltDA"));
        tableCol_ColCampaign_DA.setMinWidth(8);
        TableColumn <LinkTarget, String> tableCol_ColCampaign_PA             = new TableColumn<>("PA");
        tableCol_ColCampaign_PA.setCellValueFactory( new PropertyValueFactory<>("ltPA"));
        tableCol_ColCampaign_PA.setMinWidth(8);
        TableColumn <LinkTarget, String> tableCol_ColCampaign_Stat             = new TableColumn<>("Status");
        tableCol_ColCampaign_Stat.setCellValueFactory( new PropertyValueFactory<>("ltStatus"));
        tableCol_ColCampaign_Stat.setMinWidth(30);
        this.tableView_CollectionCampaign.getColumns().addAll( tableCol_ColCampaign_ArticleTit,
                tableCol_ColCampaign_BlogName,
                tableCol_ColCampaign_LinkTargetPage,
                tableCol_ColCampaign_DA,
                tableCol_ColCampaign_PA,
                tableCol_ColCampaign_Stat );

        this.populate_ChoiceBox_With_LtStatus();

    }                                  // IS WORKING !!!

    public void setFileLocations (String fileLocation) {
        // ---------- Press Open File Button
        this.FILE_LOCATION = fileLocation;
        this.FILE_LOCATION_SAVE = fileLocation;

    }                                               // IS WORKING !!!

    private void createListOfBloggers (String fileLocation) {

        this.virtualWorkbook = new VirtualWorkbook(FILE_LOCATION);
        this.listwork = new Listwork(virtualWorkbook);
    }                                          // IS WORKING !!!

    // ------------------------------------------------ SELECTING ------------------------------------------------------


    @FXML
    public void select_Article ()   {

        if ( this.tableView_Articles.getSelectionModel().getSelectedItem() != null )    {

            textField_artArticleTitle.setText( this.tableView_Articles.getSelectionModel().getSelectedItem().getArtArticleTitle() );
            textField_artArticlePageLink.setText( this.tableView_Articles.getSelectionModel().getSelectedItem().getArtPageLinkForArticle() );
        }
    }                                                                  // IS WORKING !!!

    @FXML
    public void select_Blog_Fill_All_TextFields ()   {
        // ---------- Press Open File Button

        if ( this.comboBox_BlogData.getSelectionModel().isEmpty() == false )    {
            String nameSelectedBlog = this.comboBox_BlogData.getValue().getBlogBlogName();

            for (BloggerData bloggerData : listwork.getListOfBloggerData())
                if (bloggerData.getBlogBlogName().equalsIgnoreCase( nameSelectedBlog )) {

                    //this.comboBox_tableView_LinkTarget_PopulateWithLT(bloggerData);
                    this.populate_ComboBox_Blog_With_LinkTargets ( bloggerData );
                    this.populate_TableView_Blog_With_LinkTargets ( bloggerData );

                    this.clear_Lt_TextFields_When_Selecting_NewBlogger();
                    this.fill_And_Update_BlogData_TextFields(bloggerData);
                    this.textField_SearchBlog.clear();
                }
            }
    }                                                 // IS WORKING !!!

    @FXML
    public void select_Article_For_List_Of_LinkTargets ()   {
        if (    this.tableView_LT_Articles.getSelectionModel().isEmpty() == false &&
                this.tableView_LT_Articles.getSelectionModel().getSelectedCells() != null )   {

            Article article = this.tableView_LT_Articles.getSelectionModel().getSelectedItem();
            this.populate_TableView_LT_Properties_With_LinkTargets( article );
        }
    }                                          // IS WORKING !!!

    @FXML
    public void select_Blog_from_ComboBox_for_LinkCampaignCreation ()  {

        if (this.comboBox_CampaignBlog.getValue() != null){
            BloggerData campaignedBlogFromCombo = this.comboBox_CampaignBlog.getValue();
            this.clear_Controls_After_Selecting_Blog_for_LBC();
        }
    }                               // IS WORKING !!!

    @FXML
    public void select_Article_from_ComboBox_for_LinkCampaignCreation ()  {

        if (this.comboBox_Articles.getValue() != null){
            Article campaignedArticleFromCombo = this.comboBox_Articles.getValue();
            this.textField_ltPageLinkSourceMine.setEditable(true);
            this.textField_ltPageLinkSourceMine.setText( campaignedArticleFromCombo.getArtPageLinkForArticle() );
            this.textField_ltPageLinkSourceMine.setEditable(false);
        }
        else if (this.comboBox_Articles.getValue() == null){
            this.textField_ltPageLinkSourceMine.setEditable(true);
            this.textField_ltPageLinkSourceMine.clear();
            this.textField_ltPageLinkSourceMine.setEditable(false);
        }


    }                            // IS WORKING

    @FXML
    public void select_Link_Campaign () {

        if ( this.tableView_LT_Properties.getSelectionModel().getSelectedItem() != null )    {

            LinkTarget selectedLinkTarget = this.tableView_LT_Properties.getSelectionModel().getSelectedItem();
            BloggerData bloggerData = this.listwork.find_Blog_With_This_LinkTarget( selectedLinkTarget );
//            System.out.println(selectedLinkTarget);
//            System.out.println(bloggerData);

            if ( bloggerData != null ) {
                this.comboBox_CampaignBlog.getSelectionModel().select(bloggerData);
                this.fill_TextFields_For_LinkTarget( selectedLinkTarget );
                this.choiceBox_EnumLtStatus.getSelectionModel().select( selectedLinkTarget.getLtStatus() );

            }
        }
    }                                                              // IS WORKING !!!

    @FXML
    public void select_LinkTarget_from_BlogComboBox ()  {

        if (this.comboBox_LinkTargets.getValue() != null){
            LinkTarget ltFromCombo = this.comboBox_LinkTargets.getValue();
            this.tableView_LinkTarget.getSelectionModel().select( ltFromCombo );
        }
    }                                              // IS WORKING !!!

    @FXML
    public void select_LinkTarget_from_BlogTableView ()    {

        if ( this.tableView_LinkTarget.getSelectionModel().getSelectedCells() != null)      {
            LinkTarget ltFromTableView = this.tableView_LinkTarget.getSelectionModel().getSelectedItem();
            this.comboBox_LinkTargets.getSelectionModel().select( ltFromTableView );
        }
    }                                           // IS WORKING !!!

    @FXML
    public void select_Link_Campaign_From_Collection_Campaign () {

        if ( this.tableView_CollectionCampaign.getSelectionModel().getSelectedItem() != null)    {

            LinkTarget selectedLinkTarget = this.tableView_CollectionCampaign.getSelectionModel().getSelectedItem();

            this.textArea_CollCampaign.setText ( selectedLinkTarget.getLtEmailThread() );

        }
    }                                     // IS WORKING !!!


    //---------------------------------------------------- OPERATIONS --------------------------------------------------
    @FXML
    public void add_New_Blog ()  {

        String blogName             = this.textField_blogName.getText().trim();
        String blogWebsiteURL       = this.textField_blogWebsiteURL.getText().trim();
        String blogSiteDescription  = this.textArea_blogSiteDescription.getText().trim();
        String blogValuesInfo       = this.textArea_blogValuesInfo.getText().trim();
        String blogTagcrowd         = this.textArea_blogTagcrowd.getText().trim();
        String blogOwner            = this.textField_blogOwner.getText().trim();
        String blogEmailContact     = this.textField_blogEmailContact.getText().trim();
        String blogWhenToSend       = this.textField_blogWhenToSend.getText().trim();
        String blogStrategiesIntel  = this.textArea_blogStrategiesIntel.getText().trim();
        String blogCommentsOnSite   = this.textArea_blogCommentsOnSite.getText().trim();

        if ( listwork != null ) {

            if ( blogName.isEmpty() == false && blogName.length() != 0 &&
                 blogWebsiteURL.isEmpty() == false && blogWebsiteURL.length() != 0 ) {

                BloggerData newBlogData = new BloggerData(
                        listwork.getListOfBloggerData().size()+1,
                        blogName,
                        blogWebsiteURL,
                        blogSiteDescription,
                        blogValuesInfo,
                        blogTagcrowd,
                        blogOwner,
                        blogEmailContact,
                        blogWhenToSend,
                        blogStrategiesIntel,
                        blogCommentsOnSite);

                if ( this.listwork.would_Blog_Be_Valid_Unique( newBlogData, blogName, blogWebsiteURL )) {

                        this.listwork.add_Blog( newBlogData );
                        this.refresh_Controls_After_Updating_DATA_Blog();
                }
            }
        }
    }                                                                     // IS WORKING !!!

    @FXML
    public void modify_Blog () {

        if (    this.comboBox_BlogData.getSelectionModel().getSelectedItem() != null ) {

            this.listwork.modify_Blog ( this.comboBox_BlogData.getSelectionModel().getSelectedItem(),
                    this.textField_blogName.getText().trim(),
                    this.textField_blogWebsiteURL.getText().trim(),
                    this.textArea_blogSiteDescription.getText().trim(),
                    this.textArea_blogValuesInfo.getText().trim(),
                    this.textArea_blogTagcrowd.getText().trim(),
                    this.textField_blogOwner.getText().trim(),
                    this.textField_blogEmailContact.getText().trim(),
                    this.textField_blogWhenToSend.getText().trim(),
                    this.textArea_blogStrategiesIntel.getText().trim(),
                    this.textArea_blogCommentsOnSite.getText().trim()  );

            this.refresh_Controls_After_Updating_DATA_Blog();
        }
    }                                                                       // IS WORKING !!!

    @FXML
    public void remove_Blog_from_List ()    {

        if (this.comboBox_BlogData.getSelectionModel().isEmpty() == false)  {

            BloggerData bloggerData = this.comboBox_BlogData.getValue();
            if (bloggerData != null)    {

                // PREP the UI
                this.comboBox_BlogData.getSelectionModel().clearSelection();
                this.clear_TextFields_on_Removing_Blog();
                this.empty_TableView_And_ComboBox_LinkTarget();

                 // REMOVE
                this.listwork.remove_Blog( bloggerData );

                // UPDATE the UI
                this.refresh_Controls_After_Updating_DATA_Blog();
            }
        }
    }                                                          // IS WORKING !!!

    //------------------------------------------------------
    @FXML
    public void add_New_Article ()  {

        String articleTitle = this.textField_artArticleTitle.getText().trim();
        String articlePageLink = this.textField_artArticlePageLink.getText().trim();

        if (listwork != null)   {

            if ( !articleTitle.isEmpty() && articleTitle.trim().length() != 0 &&
                 !articlePageLink.isEmpty() && articlePageLink.trim().length() != 0   ) {

                Article newArticle =  new Article ( this.listwork.getListOfArticles().size()+1 ,
                                                     articleTitle,
                                                     articlePageLink);

                if ( this.listwork.would_Article_Be_Valid_Unique( newArticle, articleTitle, articlePageLink ) ) {

                    this.listwork.add_Article( newArticle );
                    this.refresh_Controls_After_Updating_DATA_Article();
                }

            }
        }
    }                                                                  // IS WORKING !!!

    @FXML
    public void modify_Article ()   {

        if ( this.tableView_Articles.getSelectionModel().getSelectedItem() != null )    {

            Article article = this.tableView_Articles.getSelectionModel().getSelectedItem();
            String newArtTitle = this.textField_artArticleTitle.getText().trim();
            String newArtPageLink = this.textField_artArticlePageLink.getText().trim();

            this.listwork.modify_Article( article, newArtTitle, newArtPageLink );

            this.refresh_Controls_After_Updating_DATA_Article();
        }
    }                                                                  // IS WORKING !!!

    @FXML
    public void remove_Article ()   {

        if ( this.tableView_Articles.getSelectionModel().getSelectedItem() != null ) {

            Article article = this.tableView_Articles.getSelectionModel().getSelectedItem();

            this.listwork.remove_Article( article );

            this.textField_artArticleTitle.clear();
            this.textField_artArticlePageLink.clear();
            this.refresh_Controls_After_Updating_DATA_Article();
            this.tableView_Articles.refresh();
        }
    }                                                                  // IS WORKING !!!

    //------------------------------------------------------
    @FXML
    public void add_New_Link_Campaign ()  {
        if (    this.comboBox_Articles.getSelectionModel().getSelectedItem() != null &&
                this.comboBox_CampaignBlog.getSelectionModel().getSelectedItem() != null &&
                !this.textField_ltPageLinkTargetTheirs.getText().trim().isEmpty() &&
                !this.textField_ltDA.getText().trim().isEmpty() &&
                !this.textField_ltPA.getText().trim().isEmpty() )    {

            if ( this.is_DA_PA_Valid( this.textField_ltDA.getText().trim(), this.textField_ltPA.getText().trim() )) {

                BloggerData targetedBlog = this.listwork.findBlogWithName (this.comboBox_CampaignBlog.getSelectionModel().getSelectedItem().getBlogBlogName());
                Article targetedArticle = this.listwork.findArticleWithTitle (this.comboBox_Articles.getSelectionModel().getSelectedItem().getArtArticleTitle());
                Article selectedArticle = this.comboBox_Articles.getSelectionModel().getSelectedItem();

                LinkTarget newLTCampaign = new LinkTarget(  this.listwork.getListOfLinkTargets().size()+1,
                                                            targetedBlog.getBlogBlogName(),
                                                            targetedArticle.getArtArticleTitle(),
                                                            this.textField_ltPageLinkTargetTheirs.getText(),
                                                            this.conversion_DA_PA(this.textField_ltDA.getText()),
                                                            this.conversion_DA_PA(this.textField_ltPA.getText()),
                                                            EnumLtStatus.status_ToStart, "");

                this.listwork.add_Link_Target ( newLTCampaign );

                this.refresh_Controls_After_Updating_DATA_LinkTarget();

                this.refresh_Controls_After_Updating_DATA_LinkTarget();
                this.tableView_LT_Articles.getSelectionModel().select( selectedArticle );
                this.select_Article_For_List_Of_LinkTargets();
            }
        }
    }                                                            // IS WORKING !!!

    @FXML
    public void modify_Link_Campaign()  {

        if (    this.tableView_LT_Articles.getSelectionModel().getSelectedItem() != null &&
                this.tableView_LT_Properties.getSelectionModel().getSelectedItem() != null &&
                this.textField_ltPageLinkTargetTheirs.getText().trim().length() != 0    ) {

            Article selArticle = this.tableView_LT_Articles.getSelectionModel().getSelectedItem();
            LinkTarget adressedLT = this.tableView_LT_Properties.getSelectionModel().getSelectedItem();


            if ( this.is_DA_PA_Valid ( this.textField_ltDA.getText().trim(), this.textField_ltPA.getText().trim() ) ) {

                this.listwork.modify_Link_Target( adressedLT,
                                                  this.textField_ltPageLinkTargetTheirs.getText(),
                                                  selArticle.getArtArticleTitle(),
                                                  this.conversion_DA_PA( this.textField_ltDA.getText()),
                                                  this.conversion_DA_PA( this.textField_ltPA.getText()),
                                                  this.choiceBox_EnumLtStatus.getSelectionModel().getSelectedItem(),
                                                  this.textArea_ltEmailExchanges.getText() );

                this.refresh_Controls_After_Updating_DATA_LinkTarget();
            }
        }
    }                                                              // IS WORKING !!!

    @FXML
    public void remove_Link_Campaign () {

        if ( this.tableView_LT_Articles.getSelectionModel().getSelectedItem() != null ) {

            Article articleSel = this.tableView_LT_Articles.getSelectionModel().getSelectedItem();

            if ( this.tableView_LT_Properties.getSelectionModel().getSelectedItem() != null ) {

                LinkTarget linkTargetToRem = this.tableView_LT_Properties.getSelectionModel().getSelectedItem();
                this.listwork.remove_LinkTarget( linkTargetToRem );

                this.refresh_Controls_After_Updating_DATA_LinkTarget();
                this.tableView_LT_Articles.getSelectionModel().select( articleSel );
                this.select_Article_For_List_Of_LinkTargets();

            }
        }
    }                                                              // IS WORKING !!!

    @FXML
    public void append_Email_Thread_to_Link_Target ()   {

        if ( this.tableView_LinkTarget.getSelectionModel().getSelectedItem() != null )  {

            if ( !this.textArea_blLtEmailThread.getText().isEmpty() &&
                    this.textArea_blLtEmailThread.getText().trim().length() != 0 )   {

                LinkTarget linkTarget = this.tableView_LinkTarget.getSelectionModel().getSelectedItem();

                String strEmailThread = this.textArea_blLtEmailThread.getText();
                String strPadding     = "----------";
                String strEnding     = "----------------------------------------------";

                DateFormat dateFormat = new SimpleDateFormat(">   dd MMM yyyy, HH:mm   <");
                Calendar cal = Calendar.getInstance();

                StringBuilder stringBuilder = new StringBuilder();

                stringBuilder.append( System.lineSeparator() );
                stringBuilder.append( "----------" );
                stringBuilder.append( dateFormat.format(cal.getTime()) );
                stringBuilder.append( "----------" );
                stringBuilder.append( System.lineSeparator() );
                stringBuilder.append( System.lineSeparator() );
                stringBuilder.append( strEmailThread );
                stringBuilder.append( System.lineSeparator() );
                stringBuilder.append( strEnding );
                stringBuilder.append( System.lineSeparator() );
                stringBuilder.append( System.lineSeparator() );

                String strOldThread = linkTarget.getLtEmailThread();
                linkTarget.setLtEmailThread( strOldThread + stringBuilder.toString() );
                //System.out.println( linkTarget.getLtEmailThread() );

                BloggerData updateThisBloggerData = this.listwork.find_Blog_With_This_LinkTarget( linkTarget );
                this.populate_ComboBox_Blog_With_LinkTargets( updateThisBloggerData );
                this.populate_TableView_Blog_With_LinkTargets( updateThisBloggerData );

                this.tableView_LT_Properties.refresh();
                this.comboBox_LinkTargets.getSelectionModel().select( linkTarget );

                this.textArea_blLtEmailThread.clear();

                this.refresh_Controls_After_Updating_DATA_LinkTarget();
            }
        }

    }                                              // IS WORKING

    @FXML
    public void clear_Controls_Blog ()   {

        this.comboBox_BlogData.getSelectionModel().clearSelection();
        this.comboBox_LinkTargets.getSelectionModel().clearSelection();
        this.comboBox_LinkTargets.getItems().removeAll();

        this.tableView_LinkTarget.getSelectionModel().clearSelection();
        this.tableView_LinkTarget.getItems().removeAll();

        this.textField_blogName.clear();
        this.textField_blogWebsiteURL.clear();
        this.textArea_blogSiteDescription.clear();
        this.textArea_blogValuesInfo.clear();
        this.textArea_blogTagcrowd.clear();
        this.textField_blogOwner.clear();
        this.textField_blogEmailContact.clear();
        this.textField_blogWhenToSend.clear();
        this.textArea_blogStrategiesIntel.clear();
        this.textArea_blogCommentsOnSite.clear();

        this.textArea_blLtEmailThread.clear();
    }

    @FXML
    public void clear_Controls_Link_Targets ()   {

        this.comboBox_Articles.getSelectionModel().clearSelection();
        this.textField_ltPageLinkSourceMine.clear();
        this.comboBox_CampaignBlog.getSelectionModel().clearSelection();
        this.textField_ltPageLinkTargetTheirs.clear();
        this.textField_ltDA.clear();
        this.textField_ltPA.clear();
        this.choiceBox_EnumLtStatus.getSelectionModel().clearSelection();
        this.textArea_ltEmailExchanges.clear();
    }                                                     // IS WORKING !!!

    @FXML
    public void see_All ()  {
        this.listwork.displayListOfBloggers();
        this.listwork.displayListOLinkTargets();
        this.listwork.displayListOfArticle();
    }

    //------------------------------------------------------------------------------------------------------------------

    // ------------------------------------------- DATA MANIPULATION ---------------------------------------------------

    @FXML
    public void searchBlogData_From_TextField ()  {

        String blogItemSearch;
        BloggerData bloggerDataFound;

        if (this.textField_SearchBlog.getText() != null)    {

            blogItemSearch = this.textField_SearchBlog.getText();
            if (blogItemSearch.trim().length() != 0)    {

                if (this.listwork != null) {

                    blogItemSearch = blogItemSearch.toLowerCase().trim();
                    bloggerDataFound = this.listwork.findBlogWithName(blogItemSearch);

                    if (bloggerDataFound != null) {
                        this.comboBox_BlogData.getSelectionModel().select( this.listwork.findBlog(bloggerDataFound) );
                    }
                }
            }
        }
    }                                                    // IS WORKING !!!

    @FXML
    public void search_LinkTarget_From_TextField ()  {

        LinkTarget linkTargetFound;
        ArrayList <LinkTarget> arrayFound = new ArrayList<>();

        if (this.listwork != null && this.textField_SearchLinkTarget.getText() != null)    {

            String searchTerm = this.textField_SearchLinkTarget.getText().trim();;
            if ( searchTerm.trim().length() != 0 )    {

                LinkTarget foundLinkTarget = this.listwork.findLinkTarget( searchTerm );

                if (foundLinkTarget != null)    {
                    this.tableView_CollectionCampaign.getSelectionModel().select( foundLinkTarget );

//                for (LinkTarget linkTarget : this.listwork.getListOfLinkTargets())  {
//
//                    if ( linkTarget.getLtArticleTitle().trim().equalsIgnoreCase( searchedLinkTarge ) &&
//                            linkTarget.getLtPageLinkTargetTheirs().trim().equalsIgnoreCase ( searchedLinkTarge ))  {
//
//                        arrayFound.add( linkTarget );
//                    }
//
////                    BloggerData blogSelected = this.comboBox_BlogData.getValue();
////
//                    searchedLinkTarge = searchedLinkTarge.toLowerCase().trim();
//                    linkTargetFound = blogSelected.findLinkTargetWithString ( searchedLinkTarge );
//
//                    if (linkTargetFound != null) {
//                        this.comboBox_LinkTargets.getSelectionModel().select( linkTargetFound );
                }
            }
        }
    }                                                  // IS WORKING

    private void autocomplete_Update_Search_Blogs ()   {

        TextFields.bindAutoCompletion( textField_SearchBlog, this.listwork.getListOfBloggerData() );
    }                                               // IS WORKING !!!

    private void autocomplete_Update_Search_LinkTargets()   {

        TextFields.bindAutoCompletion( textField_SearchLinkTarget, this.tableView_CollectionCampaign.getItems() );
    }                                          // IS WORKING

    private void fill_And_Update_BlogData_TextFields (BloggerData bloggerData) {
        this.textField_blogName.setText             ( bloggerData.getBlogBlogName() );
        this.textField_blogWebsiteURL.setText       ( bloggerData.getBlogWebSiteURL() );
        this.textArea_blogSiteDescription.setText  ( bloggerData.getBlogSiteDescription() );
        this.textArea_blogValuesInfo.setText       ( bloggerData.getBlogSiteValuesInfo() );
        this.textArea_blogTagcrowd.setText         ( bloggerData.getBlogTagcrowd() );
        this.textField_blogOwner.setText            ( bloggerData.getBlogOwner() );
        this.textField_blogEmailContact.setText     ( bloggerData.getBlogEmailContact() );
        this.textField_blogWhenToSend.setText       ( bloggerData.getBlogWhenToSend() );
        this.textArea_blogStrategiesIntel.setText  ( bloggerData.getBlogStrategiesIntel() );
        this.textArea_blogCommentsOnSite.setText   ( bloggerData.getBlogCommentsOnSite() );

    }                       // IS WORKING !!!

    private void clear_Lt_TextFields_When_Selecting_NewBlogger()  {

        this.textField_ltPageLinkTargetTheirs.clear();
        this.textField_ltDA.clear();
        this.textField_ltPA.clear();
        this.choiceBox_EnumLtStatus.getSelectionModel().clearSelection();
    }                                    // IS WORKING !!!

    private void fill_TextFields_For_LinkTarget (LinkTarget linkTarget) {
        if (linkTarget != null) {

            this.textField_ltPageLinkTargetTheirs.setText (linkTarget.getLtPageLinkTargetTheirs());
            this.textField_ltDA.setText (linkTarget.getLtDA());
            this.textField_ltPA.setText (linkTarget.getLtPA());
            this.textArea_ltEmailExchanges.setText (linkTarget.getLtEmailThread());
        }
    }                              // IS WORKING

    private void clear_Controls_After_Selecting_Blog_for_LBC () {
        this.textField_ltPageLinkTargetTheirs.clear();
        this.textField_ltDA.clear();
        this.textField_ltPA.clear();
        this.choiceBox_EnumLtStatus.getSelectionModel().clearSelection();
    }                                      // IS WORKING

    private void clear_TextFields_on_Removing_LinkTarget () {

        this.textField_ltPageLinkTargetTheirs.clear();
        this.textField_ltDA.clear();
        this.textField_ltPA.clear();
        this.choiceBox_EnumLtStatus.getSelectionModel().clearSelection();
    }                                          // IS WORKING

    private void clear_TextFields_on_Removing_Blog ()   {
        this.textField_blogName.clear();
        this.textField_blogWebsiteURL.clear();
        this.textArea_blogSiteDescription.clear();
        this.textArea_blogValuesInfo.clear();
        this.textArea_blogTagcrowd.clear();
        this.textField_blogOwner.clear();
        this.textField_blogEmailContact.clear();
        this.textField_blogWhenToSend.clear();
        this.textArea_blogStrategiesIntel.clear();

        this.clear_TextFields_on_Removing_LinkTarget(); // or Blog

    }                                              // IS WORKING

    private void empty_TableView_And_ComboBox_LinkTarget () {

        ObservableList<LinkTarget> optionsLinkTargets = this.comboBox_LinkTargets.getItems();

        optionsLinkTargets.removeAll();
        optionsLinkTargets.clear();

        this.comboBox_LinkTargets.setItems( optionsLinkTargets );     // them we add new items to the combo box
        this.tableView_LinkTarget.setItems( optionsLinkTargets );     // and to the table view
        this.tableView_LinkTarget.refresh();
        this.autocomplete_Update_Search_LinkTargets();
    }                                          // IS WORKING

    private String conversion_DA_PA (String rankValue)   {

        int foo;
        String bar;
        try {
            foo = Integer.parseInt( rankValue );
            if ( foo < 1 )
                foo = 1;
            if (foo > 100 )
                foo = 100;
        } catch (NumberFormatException e) {
            foo = 9999;
        }

        bar = String.valueOf( foo );
        return bar;
    }                                             // IS WORKING !!!

    private boolean is_DA_PA_Valid (String daValue, String paValue)   {

        int da, pa;
        if ( !daValue.isEmpty() && !paValue.isEmpty()) {
            try {
                da = Integer.parseInt(daValue);
            } catch (NumberFormatException e) {
                da = 9999;
            }

            try {
                pa = Integer.parseInt(paValue);
            } catch (NumberFormatException e) {
                pa = 9999;
            }

            if (da < 1 || da > 100 || pa < 1 || pa > 100)
                return false;
            else
                return true;
        }
        else return false;
    }                                // IS WORKING !!!

// ------------------------------------- REFRESH ---------------------------------------

    private void refresh_Controls_After_Updating_DATA_Blog ()    {

        this.populate_ComboBox_BlogData_With_Blogs();
        this.populate_TableView_CollectionsCampaign_With_LinkTargets();
        this.tableView_CollectionCampaign.refresh();

        this.populate_TableView_LT_Articles_For_List_LinkTargets();
        this.populate_ComboBox_NewCampaignBlog_With_Blogs();
        this.autocomplete_Update_Search_Blogs();

    }                                     // IS WORKING !!!

    private void refresh_Controls_After_Updating_DATA_LinkTarget()    {

        this.populate_ComboBox_TableView_With_LinkTargets();
        this.tableView_LinkTarget.refresh();

        this.populate_TableView_LT_Articles_For_List_LinkTargets();
        this.tableView_LT_Articles.refresh();

        this.populate_TableView_CollectionsCampaign_With_LinkTargets();
        this.tableView_CollectionCampaign.refresh();

        this.tableView_LT_Properties.getSelectionModel().clearSelection();
        this.tableView_LT_Articles.getSelectionModel().clearSelection();

        this.autocomplete_Update_Search_LinkTargets();
    }                                // IS WORKING !!!

    private void refresh_Controls_After_Updating_DATA_Article()  {

        //this.comboBox_tableView_LinkTarget_PopulateWithLT();
        this.tableView_LinkTarget.refresh();

        //this.tableView_LinkTarget_Articles_PopulateWithLT();
        this.populate_TableView_LT_Articles_For_List_LinkTargets();
        this.tableView_LT_Articles.refresh();

        this.populate_TableView_CollectionsCampaign_With_LinkTargets();
        this.tableView_CollectionCampaign.refresh();

        this.populate_TableView_Articles_With_Articles();
        this.tableView_Articles.refresh();

        this.tableView_LT_Properties.getSelectionModel().clearSelection();
        this.tableView_LT_Articles.getSelectionModel().clearSelection();

        //this.comboBox_tableView_LinkTarget_PopulateWithLT();
        this.tableView_LinkTarget.refresh();
        this.populate_comboBox_Articles_With_Articles();

        this.populate_ComboBox_TableView_With_LinkTargets();
    }                                     // IS WORKING !!!


// ------------------------------------ POPULATE ---------------------------------------


    private void populate_ComboBox_BlogData_With_Blogs ()    {

        ObservableList<BloggerData> optionsBlogData = FXCollections.observableArrayList();

        for (BloggerData bloggerData : this.listwork.getListOfBloggerData()){
            optionsBlogData.add( bloggerData );
        }
        this.comboBox_BlogData.setItems( optionsBlogData );
        this.autocomplete_Update_Search_Blogs();

    }                                         // IS WORKING !!!

    private void populate_ComboBox_TableView_With_LinkTargets ()   {

        if ( this.comboBox_BlogData.getSelectionModel().getSelectedItem() != null) {

            BloggerData bloggerData =  this.comboBox_BlogData.getSelectionModel().getSelectedItem();

            this.comboBox_LinkTargets.getSelectionModel().clearSelection();
            this.comboBox_LinkTargets.getItems().removeAll();

            this.tableView_LinkTarget.getSelectionModel().clearSelection();
            this.tableView_LinkTarget.getItems().removeAll();

            ObservableList<LinkTarget> optionsLinkTargets = FXCollections.observableArrayList();
            for ( LinkTarget linkTarget : this.listwork.pull_List_LinkTargets_For_Blog ( bloggerData ) ) {
                optionsLinkTargets.add( linkTarget );
            }
            this.comboBox_LinkTargets.setItems(optionsLinkTargets);
            this.tableView_LinkTarget.setItems(optionsLinkTargets);
            this.tableView_LinkTarget.refresh();
        }
    }                                   // IS WORKING !!!

    private void populate_ComboBox_Blog_With_LinkTargets (BloggerData bloggerData)    {

        if ( this.comboBox_BlogData.getSelectionModel().getSelectedItem() != null) {

            this.comboBox_LinkTargets.getSelectionModel().clearSelection();
            this.comboBox_LinkTargets.getItems().removeAll();            // first we empty the combo box

            ObservableList<LinkTarget> optionsLinkTargets = FXCollections.observableArrayList();

            for ( LinkTarget linkTarget : this.listwork.pull_List_LinkTargets_For_Blog ( bloggerData ) ) {
                optionsLinkTargets.add(linkTarget);
            }
            this.comboBox_LinkTargets.setItems(optionsLinkTargets);     // them we add new items to the combo box
        }
    }                // IS WORKING !!!

    private void populate_TableView_Blog_With_LinkTargets (BloggerData bloggerData)    {

        if ( this.comboBox_BlogData.getSelectionModel().getSelectedItem() != null) {

            this.tableView_LinkTarget.getSelectionModel().clearSelection();
            this.tableView_LinkTarget.getItems().removeAll();

            ObservableList<LinkTarget> optionsLinkTargets = FXCollections.observableArrayList();
            for ( LinkTarget linkTarget : this.listwork.pull_List_LinkTargets_For_Blog ( bloggerData ) ) {
                optionsLinkTargets.add( linkTarget );
            }

            this.tableView_LinkTarget.setItems(optionsLinkTargets);     // and to the table view
            this.tableView_LinkTarget.refresh();
        }
    }               // IS WORKING !!!

    // -------------------------------

    private void populate_ComboBox_NewCampaignBlog_With_Blogs ()    {

        ObservableList<BloggerData> optionsBlogData = FXCollections.observableArrayList();

        for (BloggerData bloggerData : this.listwork.getListOfBloggerData()){
            optionsBlogData.add( bloggerData );
        }
        this.comboBox_CampaignBlog.setItems( optionsBlogData );

    }                                  // IS WORKING !!!

    private void populate_TableView_Articles_With_Articles ()    {

        this.tableView_Articles.getSelectionModel().clearSelection();
        this.tableView_Articles.getItems().removeAll();

        ObservableList <Article> optionsArticles = FXCollections.observableArrayList();

        for ( Article article : this.listwork.getListOfArticles() )  {
            optionsArticles.add( article );
        }
        this.tableView_Articles.setItems( optionsArticles );     // and to the table view
        this.tableView_Articles.refresh();


    }                                     // IS WORKING !!!

    private void populate_TableView_LT_Articles_For_List_LinkTargets ()    {

        this.tableView_LT_Articles.getSelectionModel().clearSelection();
        this.tableView_LT_Articles.getItems().removeAll();

        ObservableList<Article> optionsArticles = FXCollections.observableArrayList();

        for ( Article article : this.listwork.getListOfArticles() )  {
            optionsArticles.add( article );
        }
        this.tableView_LT_Articles.setItems( optionsArticles );     // and to the table view
        this.tableView_LT_Articles.refresh();

    }                           // IS WORKING !!!

    private void populate_TableView_LT_Properties_With_LinkTargets (Article article)    {

        this.tableView_LT_Properties.getSelectionModel().clearSelection();
        this.tableView_LT_Properties.getItems().removeAll();

        ObservableList<LinkTarget> optionsLTforArt = FXCollections.observableArrayList();
        for (LinkTarget linkTarget : this.listwork.pull_List_LinkTargets_For_Article( article )) {
            optionsLTforArt.add( linkTarget );
        }

        this.tableView_LT_Properties.setItems(optionsLTforArt);     // and to the table view
        this.tableView_LT_Properties.refresh();
    }              // IS WORKING !!!

    private void populate_TableView_CollectionsCampaign_With_LinkTargets ()    {

        ObservableList<LinkTarget> optionsLinkTargets = FXCollections.observableArrayList();

        this.tableView_CollectionCampaign.getSelectionModel().clearSelection();
        this.tableView_CollectionCampaign.getItems().removeAll();

        for (Article article : this.listwork.getListOfArticles() ) {

            for (LinkTarget linkTarget : this.listwork.pull_List_LinkTargets_For_Article( article )) {

                optionsLinkTargets.add(linkTarget);
            }
        }
        this.tableView_CollectionCampaign.setItems( optionsLinkTargets );     // and to the table view
        this.tableView_CollectionCampaign.refresh();
        this.autocomplete_Update_Search_LinkTargets();
    }                       // IS WORKING !!!

    private void populate_comboBox_Articles_With_Articles ()    {

        ObservableList<Article> optionsArticles = FXCollections.observableArrayList();

        for (Article article : this.listwork.getListOfArticles()){
            optionsArticles.add( article );
        }
        this.comboBox_Articles.setItems( optionsArticles );

    }                                      // IS WORKING !!!

    private void populate_ChoiceBox_With_LtStatus()   {

        ObservableList<EnumLtStatus> optionsLtStatusChoice = FXCollections.observableArrayList();
        for (EnumLtStatus stat : EnumLtStatus.values()) {
            optionsLtStatusChoice.add( stat );
        }
        this.choiceBox_EnumLtStatus.getItems().addAll( optionsLtStatusChoice );

    }                                                // IS WORKING !!!

// ---------------------------------------------------------------------------------------------------------------------


}

