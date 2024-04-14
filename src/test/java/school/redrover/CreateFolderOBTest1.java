package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class CreateFolderOBTest1 extends BaseTest {
    private void createNewFolder(String folderName) throws InterruptedException {
        getDriver().findElement(By.linkText("New Item")).click();
        Thread.sleep(2000);
        getDriver().findElement(By.name("name")).sendKeys(folderName);
        scrollOnce(getDriver());
        getDriver().findElement(By.xpath("//label/span[text()='Folder']")).click();
        getDriver().findElement(By.id("ok-button")).click();
    }
    public static void scrollOnce(WebDriver driver) {
        ((JavascriptExecutor) driver).executeScript(
                "window.scrollBy(0, 500);");
    }

    private void openDashboard(){
        getDriver().findElement(By.id("jenkins-head-icon")).click();
    }
    @Test
    public void testNewlyCreatedFolderEmptyOB() throws InterruptedException {
        final String folderName = "NewProjectFolder";
        final String thisFolderIsEmptyMessage = "This folder is empty";
        final String createAJobLinkText = "Create a job";

        createNewFolder(folderName);
        openDashboard();
        getDriver().findElement(By.linkText(folderName)).click();

        final String actualFolderName = getDriver().findElement(By.xpath("//h1")).getText();
        final String actualEmptyStateMessage = getDriver().findElement(By.xpath("//section[@class='empty-state-section']/h2")).getText();
        final WebElement newJobLink = getDriver().findElement(By.xpath("//a[@href='newJob']"));
        final String actualNewJobLinkText = newJobLink.getText();

        Assert.assertEquals(actualFolderName, folderName);
        Assert.assertEquals(actualEmptyStateMessage, thisFolderIsEmptyMessage);
        Assert.assertEquals(actualNewJobLinkText, createAJobLinkText);
        Assert.assertTrue(newJobLink.isDisplayed(), "newJobLink is NOT displayed");
    }
}
