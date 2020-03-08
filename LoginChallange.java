import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.Command;
import org.openqa.selenium.remote.CommandExecutor;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.Response;
import org.openqa.selenium.remote.SessionId;
import org.openqa.selenium.remote.http.W3CHttpCommandCodec;
import org.openqa.selenium.remote.http.W3CHttpResponseCodec;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import junit.framework.Test;

public class LoginChallange {public static RemoteWebDriver createDriverFromSession(final SessionId sessionId, URL command_executor){
    CommandExecutor executor = new HttpCommandExecutor(command_executor) {

    @Override
    public Response execute(Command command) throws IOException {
        Response response = null;
        if (command.getName() == "newSession") {
            response = new Response();
            response.setSessionId(sessionId.toString());
            response.setStatus(0);
            response.setValue(Collections.<String, String>emptyMap());

            try {
                Field commandCodec = null;
                commandCodec = this.getClass().getSuperclass().getDeclaredField("commandCodec");
                commandCodec.setAccessible(true);
                commandCodec.set(this, new W3CHttpCommandCodec());

                Field responseCodec = null;
                responseCodec = this.getClass().getSuperclass().getDeclaredField("responseCodec");
                responseCodec.setAccessible(true);
                responseCodec.set(this, new W3CHttpResponseCodec());
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        } else {
            response = super.execute(command);
        }
        return response;
    }
    };

    return new RemoteWebDriver(executor, new DesiredCapabilities());
}

public static void main(String [] args) throws InterruptedException {

	WebDriver driver = new FirefoxDriver();
    HttpCommandExecutor executor = (HttpCommandExecutor) ((RemoteWebDriver) driver).getCommandExecutor();
    URL url = executor.getAddressOfRemoteServer();
    SessionId session_id = ((RemoteWebDriver) driver).getSessionId();
    
    
    RemoteWebDriver driver2 = createDriverFromSession(session_id, url);
    driver2.get("https://estore.cisco.com");
   
    driver2.manage().window().maximize();
    
    synchronized (driver2){
    	driver2.wait(20000);
    	};
   // driver2.wait(20000);
    driver2.findElement(By.xpath("//*[@id=\"userInput\"]")).click();
    System.out.println("succesfully cleared username");
    
    driver2.findElement(By.xpath("//*[@id=\"userInput\"]")).sendKeys("rrupired");
    System.out.println("succesfully entered username");
    
    driver2.findElement(By.xpath("//*[@id=\"login-button\"]")).click();
    
    System.out.println("sucessfully clicked next button");
    
    driver2.manage().timeouts().implicitlyWait(100,TimeUnit.SECONDS) ;
    WebElement element = driver2.findElement(By.xpath("//*[@id=\"passwordInput\"]"));
    WebDriverWait wait = new WebDriverWait(driver, 20); //here, wait time is 20 seconds

    wait.until(ExpectedConditions.visibilityOf(element));
    element.click();
    element.sendKeys("Jujubi%6");
    System.out.println("successfully entered password");
    
    
   
    WebElement element1 = driver2.findElement(By.xpath("//*[@id=\"login-button\"]"));
    element1.click();
    
   
    synchronized (driver2){
    	driver2.wait(50000);
    	};
   
  // driver2.findElement(By.xpath("//a[contains(text(), 'What is this?')]")).click();
  
    //	 System.out.println("successfully opened two factor");
    	// driver2.findElement(By.cssSelector("body")).click();
    	 //System.out.println("successful CLICKED ");
    driver2.findElement(By.cssSelector("body")).sendKeys(Keys.TAB);
    System.out.println("clicked TAB1");
    driver2.findElement(By.cssSelector("body")).sendKeys(Keys.TAB);
    driver2.findElement(By.cssSelector("body")).sendKeys(Keys.TAB);
    driver2.findElement(By.cssSelector("body")).sendKeys(Keys.RETURN);
    synchronized (driver2){
    	driver2.wait(10000);
    	};
   System.out.println("Sussefully loggedIn");
   
 // String text =   driver2.findElement(By.xpath("/html/body/div/div/div[1]/div/form/div[1]/fieldset/h2")).getText();
  //System.out.println(text); 
  //System.out.println("successfully clicked TAB button");
    /*
    if(element2.size() > 0)
    {
        System.out.println("Found h2 header Downloads");
        System.out.println("size of elemet is :"+element2.size());
        element2.get(0).sendKeys(Keys.TAB);
    }*/
    /*
    element1.sendKeys(Keys.TAB);
    element1.sendKeys(Keys.TAB);
    element1.sendKeys(Keys.ENTER);*/
 /*   WebDriverWait wait1 = new WebDriverWait(driver, 20); //here, wait time is 20 seconds

    wait1.until(ExpectedConditions.visibilityOf(element1));
    element1.click();*/
    System.out.println("successfully clicked call me button");
   // driver2.findElement(By.id("passwordInput")).click();
    
}


}
