package demo;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.io.FileInputStream;
import java.time.Duration;
import java.util.List;
import java.util.logging.Level;

import demo.utils.ExcelDataProvider;
// import io.github.bonigarcia.wdm.WebDriverManager;
import demo.wrappers.Wrappers;

public class TestCases extends ExcelDataProvider{ // Lets us read the data
        ChromeDriver driver;

        /*
         * TODO: Write your tests here with testng @Test annotation.
         * Follow `testCase01` `testCase02`... format or what is provided in
         * instructions
         */

        /*
         * Do not change the provided methods unless necessary, they will help in
         * automation and assessment
         */
        @BeforeTest
        public void startBrowser() {
                System.setProperty("java.util.logging.config.file", "logging.properties");

                // NOT NEEDED FOR SELENIUM MANAGER
                // WebDriverManager.chromedriver().timeout(30).setup();

                ChromeOptions options = new ChromeOptions();
                LoggingPreferences logs = new LoggingPreferences();

                logs.enable(LogType.BROWSER, Level.ALL);
                logs.enable(LogType.DRIVER, Level.ALL);
                options.setCapability("goog:loggingPrefs", logs);
                options.addArguments("--remote-allow-origins=*");

                System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log");

                driver = new ChromeDriver(options);
                PageFactory.initElements(new AjaxElementLocatorFactory(driver, 20), this);
                driver.manage().window().maximize();
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
                driver.get("https://www.google.co.in/");
        }

        @FindBy(xpath = "//div[@id='guide-links-primary']//a[text()='About']")
        WebElement AboutEle;

        @FindBy(xpath = "//main[contains(@class,'ytabout__main')]//section/child::h1")
        WebElement msg1;

        @FindBy(xpath = "//main[contains(@class,'ytabout__main')]//section/child::h1/parent::section/child::p[1]")
        WebElement msg2;

         @Test(enabled = true , priority = 1)
        public void testCase01(){
                try {
                System.out.println("Start Test case: TestCase01");
                //Waiting mechanism ---> Explicit wait implementation
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
                Wrappers Methods_Wrap = new Wrappers();
                //Navigating to url
                Methods_Wrap.NavigateToUrl(driver, "https://www.youtube.com/");
                SoftAssert softAssert = new SoftAssert();
                // Hard Assertion :- Verifying wheather the current url matches with the expected url
                Assert.assertEquals(driver.getCurrentUrl(), "https://www.youtube.com/");
                // Waiting until the Element AboutEle is become Clickable
                wait.until(ExpectedConditions.elementToBeClickable(AboutEle));
                // Action Class implementation to move and click on the About Link
                Actions action = new Actions(driver);
                action.moveToElement(AboutEle).click().build().perform();
                // Create an instance of JavaScript Executor to scroll down
                JavascriptExecutor js = (JavascriptExecutor) driver;
                // Scroll down by 1000 pixels
                js.executeScript("window.scrollBy(0,800)");
                // printing message 1
                System.out.println("Message 1 : " +msg1.getText());
                // printing message 2
                System.out.println("Message 2 : " +msg2.getText());
                System.out.println("End Test case: TestCase01");
                }catch(Exception e){
                e.printStackTrace();
                }
        }

        @FindBy(xpath = "//yt-formatted-string[text()='Movies']")
        WebElement MoviesEle;
    
        @FindBy(xpath = "//span[text()='Movies']")
        WebElement MoviesPageEle;
    
        @FindBy(xpath = "//div[@id='contents']//child::div[@id='right-arrow']//button[@aria-label='Next']")
        WebElement RightCarouselEle;
    
        @FindBy(xpath = "//ytd-grid-movie-renderer[@class='style-scope yt-horizontal-list-renderer']//a[contains(@class,'ytd-grid-movie-renderer')]//child::span")
        List<WebElement> AllMoviesGenreEle;
    
        @FindBy(xpath = "//*[@class='badges style-scope ytd-grid-movie-renderer']//div[@role='img']//p[@class='style-scope ytd-badge-supported-renderer']")
        List<WebElement> AllMoviesTypeEle;

        @Test(enabled = true,priority = 2)
        public void testCase02(){
            try{
                System.out.println("Start Test case: TestCase02");
                //Waiting mechanism ---> Explicit wait implementation
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
                Wrappers Methods_Wrap = new Wrappers();
                SoftAssert softAssert = new SoftAssert();
                //Navigating to url
                Methods_Wrap.NavigateToUrl(driver, "https://www.youtube.com/");
                Assert.assertEquals(driver.getCurrentUrl(), "https://www.youtube.com/");
                // Action Class implementation to move and click on the About Link
                Actions action = new Actions(driver);
                action.moveToElement(MoviesEle).click().build().perform();
                Assert.assertEquals(MoviesPageEle.getText(),"Movies");
                while(true)
                {
                    // Wait until the right arrow button is visible and clickable
                    WebElement rightArrowButton = wait.until(ExpectedConditions.elementToBeClickable(RightCarouselEle));
                    // Click the right arrow button
                    rightArrowButton.click();
                    // Wait for a short period to allow for any animations or page updates
                    // This will wait until the right arrow button is clickable again, or timeout
                    try {
                        wait.until(ExpectedConditions.elementToBeClickable(RightCarouselEle));
                    }catch (Exception e) {
                        // If the right arrow button is no longer clickable, it might be invisible
                        break;
                    }
                    if(!isElementVisible(driver,RightCarouselEle)){
                        break;
                    }
                }
                String actualGenre = AllMoviesGenreEle.get(AllMoviesGenreEle.size()-1).getText();
                String actualMovieType = AllMoviesTypeEle.get(AllMoviesTypeEle.size()-1).getText();
                boolean isComedyOrAnimation = actualGenre.contains("Comedy") || actualGenre.contains("Animation");
                boolean MovieTypeStatus = actualMovieType.contains("A");
                softAssert.assertTrue(isComedyOrAnimation, "The movie genre is neither Comedy nor Animation. Actual genre: " + actualGenre);
                softAssert.assertTrue(MovieTypeStatus, "The movie type is not marked as 'A'. Actual Type : " + actualMovieType);
                System.out.println("End Test case: TestCase02");
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        
        @FindBy(xpath = "//yt-formatted-string[text()='Music']")
        WebElement MusicTabEle;
    
        @FindBy(xpath = "//h1[@class='style-scope ytd-topic-channel-details-renderer']//yt-formatted-string[text()='Music']")
        WebElement MusicPageEle;
    
        @FindBy(xpath = "(//div[@id='contents']//child::div[@id='right-arrow']//button[@aria-label='Next'])[1]")
        WebElement RightCarouselEle1;
    
        @FindBy(xpath = "(//yt-horizontal-list-renderer)[1]//div[@id='scroll-outer-container'][1]//a[contains(@class,' ytd-compact-station-renderer')]/child::h3")
        List<WebElement> AllFirstRowMovieTitle;
    
        @FindBy(xpath = "(//yt-horizontal-list-renderer)[1]//div[@id='scroll-outer-container'][1]//a[contains(@class,' ytd-compact-station-renderer')]//p[@id='video-count-text']")
        List<WebElement> AllFirstRowMoviesTrackCount;

        @Test(enabled = true , priority = 3)
        public void testCase03(){
        try{
            System.out.println("Start Test case: TestCase03");
            //Waiting mechanism ---> Explicit wait implementation
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            Wrappers Methods_Wrap = new Wrappers();
            SoftAssert softAssert = new SoftAssert();
            //Navigating to url
            Methods_Wrap.NavigateToUrl(driver, "https://www.youtube.com/");
            Assert.assertEquals(driver.getCurrentUrl(), "https://www.youtube.com/");
            // Action Class implementation to move and click on the About Link
            Actions action = new Actions(driver);
            action.moveToElement(MusicTabEle).click().build().perform();
            wait.until(ExpectedConditions.elementToBeClickable(MusicPageEle));
            // Create an instance of JavaScript Executor to scroll down
            JavascriptExecutor js = (JavascriptExecutor) driver;
            // Scroll down by 1000 pixels
            js.executeScript("window.scrollBy(0,500)");
            Assert.assertEquals(MusicPageEle.getText(),"Music");
            while(true)
            {
                // Wait until the right arrow button is visible and clickable
                WebElement rightArrowButton = wait.until(ExpectedConditions.elementToBeClickable(RightCarouselEle1));
                // Click the right arrow button
                rightArrowButton.click();
                // Wait for a short period to allow for any animations or page updates
                // This will wait until the right arrow button is clickable again, or timeout
                try {
                    wait.until(ExpectedConditions.elementToBeClickable(RightCarouselEle1));
                }catch (Exception e) {
                    // If the right arrow button is no longer clickable, it might be invisible
                    break;
                }
                if(!isElementVisible(driver,RightCarouselEle1)){
                    break;
                }
            }
            String MovieTitle = AllFirstRowMovieTitle.get(AllFirstRowMovieTitle.size()-1).getText();
            System.out.println(MovieTitle);
            String[] arr = AllFirstRowMoviesTrackCount.get(AllFirstRowMoviesTrackCount.size()-1).getText().trim().split(" ");
            int count = Integer.parseInt(arr[0]);
            boolean isCountisLessThanEqualTo = (count <= 50);
            softAssert.assertTrue(isCountisLessThanEqualTo,"Track Count is not less than or equal to 50 ");
            // Assert all to report any soft assertion failures
            softAssert.assertAll();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private boolean isElementVisible(WebDriver driver,WebElement RightCarouselEle) {
        try {
            return RightCarouselEle.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    @FindBy(xpath = "//yt-formatted-string[text()='News']")
    WebElement NewsTabEle;

    @FindBy(xpath = "//h1[@class='dynamic-text-view-model-wiz__h1']//*[text()='News']")
    WebElement NewsPageEle;

    @FindBy(xpath = "//*[text()='Latest news posts']")
    WebElement LatestPostEle;

    @FindBy(xpath = "//div[@id='dismissible' and @role='link']//a[@id='author-text']")
    List<WebElement> TitleOfTheNews;
    @FindBy(xpath = "//div[@id='dismissible' and @role='link']//div[@id='post-text']//*[@id='home-content-text']")
    List<WebElement> BodyOfTheNews;

    @FindBy(xpath = "//span[@id='vote-count-middle']")
    List<WebElement> AllLikesCountEle;

    @Test(enabled = true , priority = 4)
    public void testCase04(){
        try{
            System.out.println("Start Test case: TestCase04");
            //Waiting mechanism ---> Explicit wait implementation
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            Wrappers Methods_Wrap = new Wrappers();
            SoftAssert softAssert = new SoftAssert();
            //Navigating to url
            Methods_Wrap.NavigateToUrl(driver, "https://www.youtube.com/");
            Assert.assertEquals(driver.getCurrentUrl(), "https://www.youtube.com/");
            // Action Class implementation to move and click on the About Link
            Actions action = new Actions(driver);
            action.moveToElement(NewsTabEle).click().build().perform();
            wait.until(ExpectedConditions.elementToBeClickable(NewsPageEle));
            // Create an instance of JavaScript Executor to scroll down
            JavascriptExecutor js = (JavascriptExecutor) driver;
            // Scroll down by 1000 pixels
            js.executeScript("window.scrollBy(0,500)");
            Assert.assertEquals(NewsPageEle.getText(),"News");
            action.moveToElement(LatestPostEle).build().perform();
            for(int i=0;i<3;i++){
                System.out.println("Title : " + TitleOfTheNews.get(i).getText());
                for(int j=i;j<3;j++){
                    System.out.println("Body : " + BodyOfTheNews.get(j).getText());
                    break;
                }
            }
            int SumOfLikes = 0;
            for(int i=0;i<3;i++){
                String ss = AllLikesCountEle.get(i).getText().trim();
                SumOfLikes = SumOfLikes + getLikes(ss);
            }
            System.out.println("Sum Of Likes : " + SumOfLikes);
            System.out.println("End Test case: TestCase04");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public int getLikes(String ss){
        if(ss == null || ss.isEmpty()){
            return 0;
        }else{
            return Integer.parseInt(ss);
        }
    }

    @FindBy(xpath = "//input[@id='search' and @name='search_query']")
    WebElement SearchBarEle;

    @FindBy(xpath = "//a[@id='video-title']")
    List<WebElement> AllFetchedVideos;

    @Test(enabled = true , priority = 5)
    public void testCase05(){
        try{
             System.out.println("Start Test case: TestCase05");
             //Waiting mechanism ---> Explicit wait implementation
             WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
             Wrappers Methods_Wrap = new Wrappers();
             SoftAssert softAssert = new SoftAssert();
             //Navigating to url
             Methods_Wrap.NavigateToUrl(driver, "https://www.youtube.com/");
             Assert.assertEquals(driver.getCurrentUrl(), "https://www.youtube.com/");    
             // Implementation to read complete data from excel
             //Providing path of the excel file
             File filename = new File("E:\\Folder\\CrioProjectCodeBase01\\surajsahume19-ME_QA_XYOUTUBE_SEARCH\\src\\test\\resources\\data.xlsx");
             FileInputStream file = new FileInputStream(filename);
             //Creating workbook object
             XSSFWorkbook workbook = new XSSFWorkbook(file);
             //Get Desired worksheet name from the workbook
             XSSFSheet sheet = workbook.getSheet("Sheet1");
             //Get the last row count
             int rowcount = sheet.getLastRowNum();
             //Get the last cell number
             int colscount = sheet.getRow(1).getLastCellNum();
             Actions actions = new Actions(driver);
             //Iterating through the rows
             for(int outer=1;outer <= rowcount;outer++){
                float totalSum = 0;
                XSSFRow rows = sheet.getRow(outer);
                // inner for loop to iterate each cell
                            for(int inner=0;inner<1;inner++){
                            XSSFCell cell = rows.getCell(inner);
                            switch(cell.getCellType()){
                                    case STRING:
                                    SearchBarEle.clear();
                                    String ss = cell.getStringCellValue();
                                    SearchBarEle.sendKeys(ss);
                                    System.out.println("Sum for " + ss);
                                    actions.sendKeys(Keys.ENTER).build().perform();
                                    break;
                              }
                            }
                for(int i=0;i<AllFetchedVideos.size();i++){
                    wait.until(ExpectedConditions.visibilityOf(AllFetchedVideos.get(i)));
                    actions.moveToElement(AllFetchedVideos.get(i)).build().perform();
                    if(totalSum <= 10000000){
                        String ViewsString = AllFetchedVideos.get(i).findElement(By.xpath(
                                "./ancestor::div[contains(@class,'text-wrapper')]/descendant::div[@id='metadata-line']/span[1]")).getText();
                        if(ViewsString.contains("M")){
                            String ViewsString1 = ViewsString.replace("M", " ");
                            String[] ViewsString2 = ViewsString1.split(" ");
                            // System.out.println(ViewsString2[0]);
                            float ii = Float.parseFloat(ViewsString2[0]);
                            totalSum = totalSum + ii*100000;
                        }else if(ViewsString.contains("K")){
                            String ViewsString1 = ViewsString.replace("K", " ");
                            String[] ViewsString2 = ViewsString1.split(" ");
                            float ii = Float.parseFloat(ViewsString2[0]);
                            totalSum = totalSum + ii*1000;
                        }
                    }
                }
                System.out.println("total Sum  : " + totalSum);
             }
             System.out.println("End Test case: TestCase05");
        }catch(Exception e){
            e.printStackTrace();
        }
    }


        @AfterTest(enabled = true)
        public void endTest() {
                driver.quit();
        }
}