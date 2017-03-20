package desafioqa;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.CacheLookup;

import java.util.List;
import java.util.NoSuchElementException;

public class PhotoGalleriesPage {

    @FindBy(css = "b[itemprop=articleSection]")
    @CacheLookup
    private List<WebElement> headers;

    @FindBys({
            @FindBy(css = "p.title + ul, p.title + select"),
            @FindBy(xpath = "..")
    })
    @CacheLookup
    private List<WebElement> sections;

    @FindBy(css = ".banner iframe")
    @CacheLookup
    private List<WebElement> advertisments;

    public WebElement findHeader(String content) {
        for (WebElement header : this.headers) {
            if (header.getText().equals(content)) {
                return header;
            }
        }
        throw new NoSuchElementException("Header containing '" + content + "'");
    }

    public WebElement findSection(String sectionName) {
        for (WebElement section : this.sections) {
            WebElement heading = section.findElement(By.cssSelector("* > p"));
            if (heading.getText().equals(sectionName)) {
                return section;
            }
        }
        throw new NoSuchElementException("Section named '" + sectionName + "'");
    }

    public WebElement findAdvertisementPanelBySize(Integer width, Integer height) {
        for (WebElement ads : this.advertisments) {
            if (ads.getAttribute("width").equals(width.toString()) && ads.getAttribute("height").equals(height.toString())) {
                return ads;
            }
        }
        throw new NoSuchElementException("Advertisement with the dimensions '" + width + "x" + height + "'");
    }
}
