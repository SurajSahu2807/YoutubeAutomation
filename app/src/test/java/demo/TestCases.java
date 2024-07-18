package demo;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.List;

public class TestCases {

    WebDriver driver;

    @BeforeTest
    public void CreateDriver(){
        WebDriverManager.chromedriver().timeout(30).setup();
        ChromeOptions options = new ChromeOptions();
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

    @Test(enabled = false , priority = 1)
    public void TestCase01(){
        try {
            System.out.println("Start Test case: TestCase01");
            //Waiting mechanism ---> Explicit wait implementation
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            WrapperMethods Methods_Wrap = new WrapperMethods();
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

    @Test(enabled = false,priority = 2)
    public void TestCase02(){
        try{
            System.out.println("Start Test case: TestCase02");
            //Waiting mechanism ---> Explicit wait implementation
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            WrapperMethods Methods_Wrap = new WrapperMethods();
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
            // Assert all to report any soft assertion failures
//            softAssert.assertAll();
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

    @Test(enabled = false , priority = 3)
    public void TestCase03(){
        try{
            System.out.println("Start Test case: TestCase03");
            //Waiting mechanism ---> Explicit wait implementation
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            WrapperMethods Methods_Wrap = new WrapperMethods();
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
    public void TestCase04(){
        try{
            System.out.println("Start Test case: TestCase04");
            //Waiting mechanism ---> Explicit wait implementation
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            WrapperMethods Methods_Wrap = new WrapperMethods();
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

}
