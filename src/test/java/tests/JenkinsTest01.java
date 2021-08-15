package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.Driver;

public class JenkinsTest01 {


    @Test
    public void test01(){
        Driver.getDriver().get("https://google.com");
        String title = Driver.getDriver().getTitle();
        System.out.println(title);
        Assert.assertTrue(title.contains("Google"));
        System.out.println("Test Basarılı");
    }



}
