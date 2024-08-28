package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountRegistionPage;
import pageObjects.HomePage;

public class TC001_AccountRegistionTest extends BaseClass{

	

	@Test(groups={"Regression","Master"})
	public void verify_account_registration() {
		
		logger.info("******Strating TC001_AccountRegistionTest*****");
		try {
		HomePage hp = new HomePage(driver);
		hp.clickMyAccount();
		logger.info("clicked on MyAccount link");

		hp.clickRegister();
		logger.info("clicked on Register link");

		AccountRegistionPage regpage = new AccountRegistionPage(driver);
		
		logger.info("Providing Customer details ");

		regpage.setFirstName(randomString().toUpperCase());
		regpage.setLastName(randomString().toUpperCase());
		regpage.setEmail(randomString()+"@gmail.com"); //randomly generated the email 
		regpage.setTelephone(randomNumber());
		
		String password=randomAlpaNumberic();
		
		regpage.setPassword(password);
		regpage.setConfimPassword(password);
		regpage.setPrivacyPolicy();
		regpage.clickContinue();
		
		logger.info("Validating expected massege ");
		String confmsg = regpage.getConfimationMsg();
         if(confmsg.equals("Your Account Has Been Created!"))
         {
        	 Assert.assertTrue(true);
         }else {
        	 
        	 logger.error("Test Failed..");
        	 logger.debug("Debug logs...");
        	 Assert.assertTrue(false);
        	 
         }
     
		//Assert.assertEquals(confmsg, "Your Account Has Been Created!!!");
		}
		catch(Exception e) {
			
			Assert.fail();
		}
		logger.info("******Finished TC001_AccountRegistionTest*****");

	}
	
	
}
