package desafioqa;

import cucumber.api.DataTable;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.Given;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;

import static org.junit.Assert.*;

import javax.naming.OperationNotSupportedException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CoveredStepsDefs {

    private WebDriver driver;
    private PhotoGalleriesPage galleriesPage;
    private DesiredCapabilities capabilities;
    private HashMap<String, String> pageHashMap;
    private HashMap<String, List<String>> listHashMap;

    @Before
    public void setUp() {
        this.capabilities = DesiredCapabilities.chrome();
        this.pageHashMap = new HashMap<String, String>();
        this.listHashMap = new HashMap<String, List<String>>();
    }

    @After
    public void cleanUp() {
        this.driver.quit();
    }

    @Given("^a page named \"([^\"]*)\" located at \"([^\"]*)\"$")
    public void a_page_named_located_at(String arg1, String arg2) {
        this.pageHashMap.put(arg1, arg2);
    }

    @Given("^a list of \"([^\"]*)\":$")
    public void a_list_of(String arg1, DataTable arg2) {
        this.listHashMap.put(arg1, arg2.asList(String.class));
    }

    @Given("^using one \"([^\"]*)\" mobile device$")
    public void using_one_mobile_device(String arg1) {
        Map<String, String> mobileEmulation = new HashMap<String, String>();
        mobileEmulation.put("deviceName", arg1);

        Map<String, Object> chromeOptions = new HashMap<String, Object>();
        chromeOptions.put("mobileEmulation", mobileEmulation);

        this.capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
    }

    @Given("^I navigate to \"([^\"]*)\" as a visitor$")
    public void i_navigate_to_as_a_visitor(String arg1) {
        String url = this.pageHashMap.get(arg1);

        this.driver = new ChromeDriver(capabilities);
        this.driver.get(url);
        this.galleriesPage = PageFactory.initElements(this.driver, PhotoGalleriesPage.class);
    }

    @Then("^I should see the header \"([^\"]*)\"$")
    public void i_should_see_the_header(String arg1) throws Throwable {
        this.galleriesPage.findHeader(arg1);
    }

    @Then("^Under the section \"([^\"]*)\" I should see a list of (\\d+) items, each containing:$")
    public void under_the_section_I_should_see_a_list_of_items_each_containing(String arg1, int arg2, DataTable arg3) throws Throwable {
        WebElement section = this.galleriesPage.findSection(arg1);
        List<WebElement> listItems = section.findElement(By.tagName("ul")).findElements(By.tagName("li"));

        assertEquals(listItems.size(), arg2);

        List<String> criteria = arg3.asList(String.class);

        while (--arg2 >= 0) {
            WebElement listItem = listItems.get(arg2);

            for (String criterion : criteria) {
                switch (criterion) {
                    case "a link":
                        listItem.findElement(By.tagName("a"));
                        break;
                    case "an image":
                        listItem.findElement(By.tagName("img"));
                        break;
                    case "a short text":
                        assertTrue(listItem.getText().length() > 0);
                        break;
                    default:
                        throw new OperationNotSupportedException("Can not search lists for: " + criterion);
                }
            }
        }
    }

    @Then("^I should see an advertisement panel with (\\d+)px of width by (\\d+)px of height$")
    public void i_should_see_an_advertisement_panel_with_px_of_width_by_px_of_height(int arg1, int arg2) throws Throwable {
        this.galleriesPage.findAdvertisementPanelBySize(arg1, arg2);
    }

    @Then("^Under the section \"([^\"]*)\" I should see a select containing items from \"(.+?)\" list$")
    public void under_the_section_I_should_see_a_select_containing_items_from_list(String arg1, String arg2) throws Throwable {
        WebElement section = this.galleriesPage.findSection(arg1);
        WebElement select = section.findElement(By.tagName("select"));

        List<String> criteriaItems = this.listHashMap.get(arg2);
        List<WebElement> selectOptions = select.findElements(By.tagName("option"));

        for (int i = 0; i < criteriaItems.size(); i++) {
            String criterion = criteriaItems.get(i);
            WebElement selectOption = selectOptions.get(i);
            assertEquals(criterion, selectOption.getText());
        }
    }

    @Then("^I should see a link \"([^\"]*)\" which references \"([^\"]*)\"$")
    public void i_should_see_a_link_which_references(String arg1, String arg2) throws Throwable {
        WebElement link = this.driver.findElement(By.linkText(arg1));
        assertEquals(arg2, link.getAttribute("href"));
    }
}
