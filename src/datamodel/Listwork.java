package datamodel;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.LinkedList;


public class Listwork {

    private LinkedList <BloggerData> listOfBloggerData = new LinkedList <>();
    private LinkedList <LinkTarget> listOfLinkTargets = new LinkedList<>();
    private LinkedList <Article> listOfArticles = new LinkedList <>();
    private VirtualWorkbook workbook;

    private final String placeholderEmpty = "_EMPTY_";

    public Listwork (VirtualWorkbook virtualWorkbook) {
        /*
        *       Go through BloggerData sheet of created workbook.
        *       Go until the wee find a completely empty row. If ROW empty, we stop.
        *       If ROW has data (website name and blogger), we create a new BloggerData object from the row. We add it to the list.
        *
        * */
        this.workbook = virtualWorkbook;

        XSSFWorkbook workbook = virtualWorkbook.getWorkbookRead();
        BloggerData blogFromRow;
        LinkTarget ltFromRow;
        Article articleFromRow;

        LinkedList<BloggerData> listInProgressBlogData = new LinkedList<>();
        LinkedList<LinkTarget>  listInProgressLinkTargets = new LinkedList<>();
        LinkedList<Article>     listInProgressArticles = new LinkedList<>();

        if (workbook != null) {

            if ( workbook.getSheet( _GLOBAL_constants._SHEET_BLOGGER_ ) != null) {
                 XSSFSheet sheet = workbook.getSheet( _GLOBAL_constants._SHEET_BLOGGER_ );

                    for (int i=0; /* No CONDITION HERE */ ; i++){

                        if (sheet.getRow(i) != null)        {
                            XSSFRow row = sheet.getRow(i);
                            blogFromRow = new BloggerData( row, this.workbook.getColumnsInBlogData() );

                            if (this.isBloggerRowEmpty( blogFromRow) == false) {
                                listInProgressBlogData.add( blogFromRow );
                            }
                            else break;
                        }
                        else break;
                    }
                    this.listOfBloggerData = listInProgressBlogData;
                }

            if ( workbook.getSheet( _GLOBAL_constants._SHEET_LINK_TARGET_ ) != null) {
                 XSSFSheet sheet = workbook.getSheet( _GLOBAL_constants._SHEET_LINK_TARGET_ );

                for (int i=0; /* No CONDITION HERE */ ; i++){

                    if (sheet.getRow(i) != null)        {
                        XSSFRow row = sheet.getRow(i);
                        ltFromRow = new LinkTarget ( row, this.workbook.getColumnsInLinkTargets() );

                        if (this.isLinkTargetRowEmpty( ltFromRow) == false) {
                            listInProgressLinkTargets.add( ltFromRow );
                        }
                        else break;
                    }
                    else break;
                }
//                this.listOfBloggerData = listInProgressBlogData;
//                this.listOfLinkTargets = listInProgressLinkTargets;
            }

            if (workbook.getSheet( _GLOBAL_constants._SHEET_ARTICLES_ ) != null) {
                XSSFSheet sheet = workbook.getSheet( _GLOBAL_constants._SHEET_ARTICLES_ );

                for (int i=0; /* No CONDITION HERE */ ; i++){

                    if (sheet.getRow(i) != null)        {
                        XSSFRow row = sheet.getRow(i);
                        articleFromRow = new Article ( row, this.workbook.getColumnsInArticles() );

                        if (this.isArticleRowEmpty( articleFromRow ) == false) {
                            //articleFromRow.setArtListLinkTarget( this.getListLTperArticle( articleFromRow ) );
                            listInProgressArticles.add( articleFromRow );
                        }
                        else break;
                    }
                    else break;
                }
            }

            this.listOfBloggerData = listInProgressBlogData;
            this.listOfLinkTargets = listInProgressLinkTargets;
            this.listOfArticles = listInProgressArticles;

            this.displayListOfBloggers();
            this.displayListOLinkTargets();
            this.displayListOfArticle();
        }
    }                                                // IS WORKING !!!

    public XSSFWorkbook workbookFromList ()     {

        XSSFWorkbook workbookFromList = new XSSFWorkbook();
        int nbr_Sheets = this.workbook.getWorkbookRead().getNumberOfSheets();

        XSSFSheet sheet;
        String sheetName = null;

        if (workbook != null){
            if (workbook.getWorkbookRead() != null) {

                for (int i = 0; i < nbr_Sheets; i++)    {
                    sheetName = this.workbook.getWorkbookRead().getSheetName(i);
                    sheet = workbookFromList.createSheet( sheetName );

                    if ( sheet != null )    {

                        if ( sheet.getSheetName().trim().equalsIgnoreCase(_GLOBAL_constants._SHEET_BLOGGER_))   {
                            for (int j=0; j < 1+ this.listOfBloggerData.size(); j++){
                                XSSFRow row = sheet.createRow( j );

                                if (j == 0)
                                    row = this.workbook.getFirstRowBlogData(row);
                                else
                                    row = BloggerData.convertBlogToXSSFRow (row, this.listOfBloggerData.get(j - 1));
                            }
                        }

                        if ( sheet.getSheetName().trim().equalsIgnoreCase(_GLOBAL_constants._SHEET_LINK_TARGET_))   {
                            for (int j=0; j < 1+ this.listOfLinkTargets.size(); j++){
                                XSSFRow row = sheet.createRow( j );

                                if (j == 0)
                                    row = this.workbook.getFirstRowLinkTarget(row);
                                else
                                    row = LinkTarget.convertLinkTargetToXSSFRow (row, this.listOfLinkTargets.get(j - 1));
                            }
                        }

                        if ( sheet.getSheetName().trim().equalsIgnoreCase(_GLOBAL_constants._SHEET_ARTICLES_))   {
                            for (int j=0; j < 1+ this.listOfArticles.size(); j++){
                                XSSFRow row = sheet.createRow( j );

                                if (j == 0)
                                    row = this.workbook.getFirstRowArticle(row);
                                else
                                    row = Article.convertArticleToXSSFRow (row, this.listOfArticles.get(j - 1));
                            }
                        }
                    }
                }
            }
        }
        return workbookFromList;
    }                                                      // IS WORKING

    // ------------------------------------------- OPERATIONs ----------------------------------------------------------

    public void add_Blog (BloggerData bloggerData)  {

        this.listOfBloggerData.add (bloggerData);
    }                                                  // IS WORKING !!!

    public void modify_Blog (BloggerData bloggerData, String blogNewName, String blogNewUrl,
                                                      String blogNewSideDesc, String blogNewValInfo,
                                                      String blogNewTag, String blogNewOwn,
                                                      String blogNewEmail, String blogNewWhenSend,
                                                      String blogNewStrat, String blogNewComment)  {

        if ( this.would_Blog_Be_Valid_Unique (bloggerData, blogNewName, blogNewUrl)  ) {

            if ( ! bloggerData.getBlogBlogName().trim().equalsIgnoreCase( blogNewName ) ) {
                for (LinkTarget linkTarget : this.pull_List_LinkTargets_For_Blog( bloggerData ))    {
                    linkTarget.setLtBloggerName( blogNewName );
                }
                this.displayListOLinkTargets();
            }
            bloggerData.setBlogBlogName(blogNewName);
            bloggerData.setBlogWebSiteURL(blogNewUrl);
            bloggerData.setBlogSiteDescription(blogNewSideDesc);
            bloggerData.setBlogSiteValuesInfo(blogNewValInfo);
            bloggerData.setBlogTagcrowd(blogNewTag);
            bloggerData.setBlogOwner(blogNewOwn);
            bloggerData.setBlogEmailContact(blogNewEmail);
            bloggerData.setBlogWhenToSend(blogNewWhenSend);
            bloggerData.setBlogStrategiesIntel(blogNewStrat);
            bloggerData.setBlogCommentsOnSite(blogNewComment);
        }
    }   // IS WORKING !!!

    public void remove_Blog (BloggerData remBloggerData)  {

        for (LinkTarget linkTarget : this.pull_List_LinkTargets_For_Blog( remBloggerData ))    {
                linkTarget.setLtBloggerName( placeholderEmpty );
             }
        int index = this.listOfBloggerData.indexOf( remBloggerData );

        for (int i = index +1; i < this.listOfBloggerData.size(); i++) {
            this.listOfBloggerData.get(i).setBlogNbrCrt( i );
        }


        this.listOfBloggerData.remove (remBloggerData);
    }                                            // IS WORKING !!!

    //-------------------

    public void add_Article (Article newArticle)    {

        this.listOfArticles.add ( newArticle );
    }                                                  // IS WORKING !!!

    public void modify_Article (Article article, String artNewTitle, String artNewUrl)  {

        if ( this.would_Article_Be_Valid_Unique (article, artNewTitle, artNewUrl)  ) {

            if ( ! article.getArtArticleTitle().trim().equalsIgnoreCase( artNewTitle ) ) {
                for (LinkTarget linkTarget : this.pull_List_LinkTargets_For_Article ( article ))    {
                    linkTarget.setLtArticleTitle( artNewTitle );
                }
                //this.displayListOLinkTargets();
            }
           article.setArtArticleTitle( artNewTitle );
           article.setArtPageLinkForArticle( artNewUrl );
        }
    }              // IS WORKING !!!

    public void remove_Article (Article remArticle)  {

        for (LinkTarget linkTarget : this.pull_List_LinkTargets_For_Article( remArticle ))  {
            linkTarget.setLtArticleTitle( placeholderEmpty );
        }

        int index = this.listOfArticles.indexOf( remArticle );

        for (int i = index +1; i < this.listOfArticles.size(); i++) {
            this.listOfArticles.get(i).setArtNbrCrt( i );
        }

        this.listOfArticles.remove( remArticle );
    }                                                 // IS WORKING !!!

    //--------------------

    public void add_Link_Target (LinkTarget newLinkTarget)    {

        if ( !this.isLinkTargetDuplicate( newLinkTarget ) )
            this.listOfLinkTargets.add ( newLinkTarget );
    }                                        // IS WORKING

    public void modify_Link_Target ( LinkTarget linkTarget,
                                     String newLTLinkTheir, String newLTArticle,
                                     String newDA, String newPA, EnumLtStatus newLtStatus,
                                     String newLtEmailThread)  {

        if ( this.would_Link_Target_Be_Valid_Unique (linkTarget, newLTLinkTheir, newLTArticle)  ) {

            linkTarget.setLtPageLinkTargetTheirs( newLTLinkTheir );
            linkTarget.setLtDA( newDA );
            linkTarget.setLtPA( newPA );
            linkTarget.setLtStatus( newLtStatus );
            linkTarget.setLtEmailThread( newLtEmailThread );
        }
    }                                       // IS WORKING !!!

    public void remove_LinkTarget (LinkTarget remLinkTarget) {

        int index = this.listOfLinkTargets.indexOf( remLinkTarget );

        for (int i = index +1; i < this.listOfLinkTargets.size(); i++) {
            this.listOfLinkTargets.get(i).setLtCrt( i );
        }

        this.listOfLinkTargets.remove (remLinkTarget);
    }                                         // IS WORKING !!!

    // ------------------------------------------- LISTS ----------------------------------------------------------

    public LinkedList<LinkTarget> pull_List_LinkTargets_For_Blog (BloggerData blog)    {

        LinkedList<LinkTarget> list = new LinkedList<>();
        String blogName = blog.getBlogBlogName().trim();

        for (LinkTarget linkTarget : this.listOfLinkTargets)    {
            if ( linkTarget.getLtBloggerName().trim().equalsIgnoreCase( blogName ) )   {

                list.add( linkTarget );
            }
        }

        return list;
    }               // IS WORKING !!!

    public LinkedList<LinkTarget> pull_List_LinkTargets_For_Article (Article article)    {

        LinkedList<LinkTarget> list = new LinkedList<>();
        String articleTitle = article.getArtArticleTitle().trim();

        for (LinkTarget linkTarget : this.listOfLinkTargets)    {
            if ( linkTarget.getLtArticleTitle().trim().equalsIgnoreCase( articleTitle ) )   {

                list.add( linkTarget );
            }
        }

        return list;
    }             // IS WORKING !!!

    // ------------------------------------- DUPLICATION CHECK ---------------------------------------------------------

    public boolean would_Blog_Be_Valid_Unique (BloggerData adressedBlog,
                                               String newBlogName, String newBlogUrl)    {

        boolean isBlogNameOK = true;
        boolean isBlogUrlOK = true;
        boolean areNameUrlComplete = true;

        //BloggerData bloggerData = this.findBlog( adressedBlog );

        if ( newBlogName.length() ==0 || newBlogUrl.length() ==0 ) {
            areNameUrlComplete = false;
            //System.out.println("!!!!!!!! Name or URL are --NULL--");
            return areNameUrlComplete;
        }

        for (BloggerData blog : this.listOfBloggerData) {

            if ( ! blog.equals( adressedBlog ) ) {
                if (blog.getBlogBlogName().trim().equalsIgnoreCase(newBlogName)) {
                    //System.out.println("!!!!!!!!!! Existing Name!");
                    isBlogNameOK = false;
                    break;
                }
                if (blog.getBlogWebSiteURL().trim().equalsIgnoreCase(newBlogUrl)) {
                    //System.out.println("!!!!!!!!!! Existing URL!");
                    isBlogUrlOK = false;
                    break;
                }
            }
        }
        return ( isBlogNameOK && isBlogUrlOK && areNameUrlComplete );
    }             // IS WORKING !!!

    public boolean would_Article_Be_Valid_Unique (Article adressedArticle,
                                                  String newArtTitle, String newArtLink)    {

        boolean isArticleTitleOK = true;
        boolean isArticleUrlOK = true;
        boolean areTitleUrlComplete = true;

        if ( newArtTitle.length() ==0 || newArtLink.length() ==0 ) {
            areTitleUrlComplete = false;
            System.out.println("!!!!!!!! Name or URL are --NULL--");
            return areTitleUrlComplete;
        }

        for (Article article : this.listOfArticles) {

            if ( ! article.equals( adressedArticle ) ) {
                if ( article.getArtArticleTitle().trim().equalsIgnoreCase(newArtTitle) ) {
                    System.out.println("!!!!!!!!!! Existing Name!");
                    isArticleTitleOK = false;
                    break;
                }
                if ( article.getArtPageLinkForArticle().trim().equalsIgnoreCase(newArtLink) ) {
                    System.out.println("!!!!!!!!!! Existing URL!");
                    isArticleUrlOK = false;
                    break;
                }
            }
        }
        return ( isArticleTitleOK && isArticleUrlOK && areTitleUrlComplete );
    }          // IS WORKING !!!


    public boolean would_Link_Target_Be_Valid_Unique (LinkTarget adressedLT,
                                                  String newLTLinkTheir, String newLTArticle)    {

        boolean isPageUrlTheirsOK = true;
        boolean areTitleUrlComplete = true;

        if ( newLTLinkTheir.length() ==0 || newLTArticle.length() ==0 ) {
            areTitleUrlComplete = false;
            System.out.println("!!!!!!!! Name or URL are --NULL--");
            return areTitleUrlComplete;
        }

        for (LinkTarget linkTarget : this.pull_List_LinkTargets_For_Article( this.findArticleWithTitle( newLTArticle ) )) {

            if ( ! linkTarget.equals( adressedLT ) ) {
                if ( linkTarget.getLtPageLinkTargetTheirs().trim().equalsIgnoreCase( newLTLinkTheir ) ) {
                    System.out.println("!!!!!!!!!! This LT already points tot his ARTicle!");
                    isPageUrlTheirsOK = false;
                    break;
                }
            }
        }
        return ( isPageUrlTheirsOK && areTitleUrlComplete );
    }     // IS WORKING !!!



    // -------------------------------------- UTILITARY METHODS --------------------------------------------------------

    public BloggerData findBlog (BloggerData bloggerData)  {

        if ( bloggerData != null ) {
            if (this.listOfBloggerData.contains(bloggerData))   {
                return bloggerData;
            }
        }
        return null;
    }                                           // IS WORKING

    public LinkTarget findLinkTarget (String searchTerm)    {

        searchTerm = searchTerm.trim();

        for (LinkTarget linkTarget : this.listOfLinkTargets)    {

            if ( linkTarget.getLtArticleTitle().trim().equalsIgnoreCase( searchTerm ) ||
                 linkTarget.getLtBloggerName().trim().equalsIgnoreCase( searchTerm ) ||
                 linkTarget.getLtPageLinkTargetTheirs().trim().equalsIgnoreCase( searchTerm ) ||
                 linkTarget.getLtEmailThread().trim().equalsIgnoreCase( searchTerm ) )

                return linkTarget;
        }
        return null;
    }

    private boolean isBloggerRowEmpty (BloggerData bloggerData) {
        boolean isEmpty = false;
        String strBlog = bloggerData.getBlogBlogName();

        if ( strBlog == null    || strBlog.trim().isEmpty()    || strBlog.trim().length() ==0) {
            isEmpty = true;
        }
        return isEmpty;
    }                                      // IS WORKING

    private boolean isLinkTargetRowEmpty (LinkTarget linkTarget) {
        boolean isEmpty = false;
        String strLT = linkTarget.getLtBloggerName();

        if ( strLT == null    || strLT.trim().isEmpty()    || strLT.trim().length() ==0) {
            isEmpty = true;
        }
        return isEmpty;
    }                                     // IS WORKING

    private boolean isArticleRowEmpty (Article article) {
        boolean isEmpty = false;
        String strArticleTit = article.getArtArticleTitle();

        if ( strArticleTit == null    || strArticleTit.trim().isEmpty()    || strArticleTit.trim().length() ==0) {
            isEmpty = true;
        }
        return isEmpty;
    }                                              // IS WORKING

    public BloggerData findBlogWithName (String bloggerName)   {
        for (BloggerData bloggerData : this.listOfBloggerData)   {
            if (bloggerData.getBlogBlogName().trim().equalsIgnoreCase( bloggerName ))
                return bloggerData;
        }
        return null;
   }                                       // IS WORKING

    public Article findArticleWithTitle (String articleTitle)   {
        for (Article article : this.listOfArticles)   {
            if (article.getArtArticleTitle().trim().equalsIgnoreCase( articleTitle ))
                return article;
        }
        return null;
    }                                      // IS WORKING

    private boolean isLinkTargetDuplicate (LinkTarget testLinkTarget)   {

        boolean isDuplicated = false;

        for ( LinkTarget linkTarget : this.listOfLinkTargets )  {

            if ( linkTarget.getLtArticleTitle().trim().equalsIgnoreCase( testLinkTarget.getLtArticleTitle().trim()) &&
                 linkTarget.getLtPageLinkTargetTheirs().trim().equalsIgnoreCase( testLinkTarget.getLtPageLinkTargetTheirs().trim())   )   {
                    // the two LINK CAMPAIGNS ask for SAME Link Target for the same POST
                isDuplicated = true;            // LTs have same article for the same Link Target Page
                return isDuplicated;
            }
        }
        return isDuplicated;
    }                              // IS WORKING

    public BloggerData find_Blog_With_This_LinkTarget (LinkTarget linkTarget)   {

        if (linkTarget != null) {

            String blogNameFromLt = linkTarget.getLtBloggerName().trim();

            for (BloggerData bloggerData : this.listOfBloggerData)  {
                if ( bloggerData.getBlogBlogName().trim().equalsIgnoreCase( blogNameFromLt )) {
                    return bloggerData;
                }
            }
        }
        return null;
    }                      // IS WORKING


    // --------------------------------------------- DISPLAY -----------------------------------------------------------

    public void displayListOfBloggers ()    {
        System.out.println("Blogs are: ");
        for (BloggerData blog : listOfBloggerData ) {
            blog.displayBloggerData();
        }
    }                                                          // IS WORKING

    public void displayListOLinkTargets ()   {
        System.out.println("LinkTargets are: ");
        for ( LinkTarget lt : this.listOfLinkTargets )
            lt.displayLinkTarget();
    }                                                         // IS WORKING

    public void displayListOfArticle ()   {
        System.out.println("Articles are: ");
        for ( Article article : this.listOfArticles)
            article.displayArticleData();
    }                                                            // IS WORKING



    public LinkedList<BloggerData> getListOfBloggerData() {
        return listOfBloggerData;
    }                                            // IS WORKING

    public LinkedList<Article> getListOfArticles() {
        return listOfArticles;
    }                                                   // IS WORKING

    public LinkedList<LinkTarget> getListOfLinkTargets() {
        return listOfLinkTargets;
    }                                             // IS WORKING

}
