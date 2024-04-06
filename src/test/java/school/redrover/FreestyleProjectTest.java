package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.ProjectUtils;

import java.util.List;

// Navigation test
public class FreestyleProjectTest extends BaseTest {
    @Test
    public void testCreateNewJobArrowIconNavigatesToNewJob(){ //navigation test
        final String expectedUrl = ProjectUtils.getBaseUrl() + "/newJob";
        final String expectedTitle = "New Item [Jenkins]";

        final String oldUrl = getDriver().getCurrentUrl();
        final String oldTitle = getDriver().getTitle();

        getDriver().findElement(By.xpath("//a[@href='newJob']/span[@class='trailing-icon']")).click();

        final String newUrl = getDriver().getCurrentUrl();
        final String newTitle = getDriver().getTitle();

        Assert.assertNotEquals(newUrl, oldUrl);
        Assert.assertNotEquals(newTitle, oldTitle);
        Assert.assertEquals(newUrl, expectedUrl);
        Assert.assertEquals(newTitle, expectedTitle);
    }

    //Functional CREATE FREESTYLE PROJECT JOB TEST
    @Test(dependsOnMethods = "testCreateNewJobArrowIconNavigatesToNewJob")
    public void testCreateFreestyleProject() {
        final  int expectedAmountOfJobsCreated = 1;
        final  String expectedJobNameCreated = "Test";

        getDriver().findElement(By.xpath("//a[@href='newJob']/span[@class='trailing-icon']")).click();
        getDriver().findElement(By.id("name")).sendKeys("Test");
        getDriver().findElement(By.xpath("//span[text()='Freestyle project']")).click(); // //li[@class='hudson_model_FreeStyleProject']
        getDriver().findElement(By.id("ok-button")).click();

        getDriver().findElement(By.id("jenkins-name-icon")).click();

        List<WebElement> jobs = getDriver().findElements(By.xpath("//table[@id='projectstatus']/tbody/tr"));

        Assert.assertEquals(jobs.size(), expectedAmountOfJobsCreated);

        WebElement jobNameElement = jobs.get(0).findElement(By.xpath("//td/a/span"));

        final String actualJobName = jobNameElement.getText();

        Assert.assertTrue(jobNameElement.isDisplayed());
        Assert.assertEquals(actualJobName, expectedJobNameCreated);
    }
}
