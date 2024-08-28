package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import utilities.DataProviders;

/*
 * data is valid -- login success -test pass --logout login failed --test fail
 * 
 * data is invalid -- login success --test fail --logout login failed --test
 * pass
 */

public class TC003_LoginDDT extends BaseClass {

	@Test(dataProvider = "loginData", dataProviderClass = DataProviders.class,groups="Datadriven")
	public void verify_loginDDT(String email, String pwd, String exp) throws InterruptedException {

		logger.info("******** Strating TC003_LoginDDT ********");
		try {
			// HomePage
			HomePage hp = new HomePage(driver);
			hp.clickMyAccount();
			hp.clickLogin();

			// LoginPage
			LoginPage lp = new LoginPage(driver);
			lp.setEmail(email);
			lp.setPassword(pwd);
			lp.clickLogin();

			// MyAccountPage
			MyAccountPage macc = new MyAccountPage(driver);
			boolean targetpage = macc.isMyAccountPageExists();

			if (exp.equalsIgnoreCase("valid")) {

				if (targetpage == true) {

					macc.clickLogout();
					Assert.assertTrue(true);

				} else {
					Assert.assertTrue(false);
				}
			}
			if (exp.equalsIgnoreCase("Invalid")) {

				if (targetpage == true) {

					macc.clickLogout();
					Assert.assertTrue(false);

				} else {
					Assert.assertTrue(true);

				}

			}

		} catch (Exception e) {
			Assert.fail();
		}
        
		Thread.sleep(3);
		logger.info("******** Finished TC003_LoginDDT ********");

	}

}
