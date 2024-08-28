package utilities;

import java.awt.Desktop;
import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.ImageHtmlEmail;
import org.apache.commons.mail.resolver.DataSourceUrlResolver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testCases.BaseClass;

public class ExtentReportManager implements ITestListener {

	public ExtentSparkReporter sparkReporter;
	public ExtentReports extent;
	public ExtentTest test;

	String repName;

	public void onTestStart(ITestResult testContext) {
		/*
		 * SimpleDateFormat df=new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss"); Date dt=new
		 * Date();
		 * 
		 * String currentdatetimestamp=df.format(dt);
		 */
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		repName = "Test-Report-" + timeStamp + ".html";

		sparkReporter = new ExtentSparkReporter(".\\reports\\" + repName); // specify location of the report file

		sparkReporter.config().setDocumentTitle("opencart Automation Report");// title of report
		sparkReporter.config().setReportName("opencart Functional Testing");// name of the report
		sparkReporter.config().setTheme(Theme.DARK);

		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);
		extent.setSystemInfo("Application", "opencart");
		extent.setSystemInfo("Module", "Admin");
		extent.setSystemInfo("Sub Module", "Customers");
		extent.setSystemInfo("User Name", System.getProperty("user.name"));
		extent.setSystemInfo("Enviroment", "QA");
		
		 ITestContext context = testContext.getTestContext();
	     String os = context.getCurrentXmlTest().getParameter("os");
	     extent.setSystemInfo("Operating System",os);
	     
	     String browser = context.getCurrentXmlTest().getParameter("browser");
	     extent.setSystemInfo(" Browser",browser);
	     
	     List<String>includeGroups=context.getCurrentXmlTest().getIncludedGroups();
	     if(!includeGroups.isEmpty()) {
	    	 extent.setSystemInfo("Groups", includeGroups.toString());
	     }
	}

	public void onTestSuccess(ITestResult result) {
		test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups()); // to display group in report
		test.log(Status.PASS, result.getName() + "got successfully executed");

	}

	public void onTestFailure(ITestResult result) {
		test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());

		test.log(Status.FAIL, result.getName() + "got failed");
		test.log(Status.INFO, result.getThrowable().getMessage());

		try {
			String imgPath = new BaseClass().captureScreen(result.getName());
			test.addScreenCaptureFromPath(imgPath);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onTestSkipped(ITestResult result) {
		test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.SKIP, result.getName() + "got skipped");
		test.log(Status.INFO, result.getThrowable().getMessage());

	}

	public void onFinish(ITestContext textContext) {

		extent.flush(); // it onFinish method sufficient

		/*
		// it automatically open in report or
		String pathofExtentReport = System.getProperty("user.dir") + "\\reports\\" + repName;
		File extentreport = new File(pathofExtentReport);

		try {
			Desktop.getDesktop().browse(extentreport.toURI());

		} catch (Exception e1) {
			e1.printStackTrace();
		}
		*/
		
		/*
		//Automatically send the email
		try {
			URL url= new URL("file://"+System.getProperty("user.dir"+"\\reports\\"+repName));
			
			//Create the email massage
			ImageHtmlEmail email=new ImageHtmlEmail();
			email.setDataSourceResolver(new DataSourceUrlResolver(url));
			email.setHostName("smtp.googlemail.com");
			email.setSmtpPort(465);
			email.setAuthenticator(new DefaultAuthenticator("tester1908@gmail.com","password"));
			email.setFrom("tester1908@gmail.com");//sender
			email.setSubject("Test Results");
			email.setMsg("Please find Attached Report....");
			email.addTo("shiv1922@gmail.com");//receiver
			email.attach(url,"extent report", "please check report");
			email.send();//send the email
				
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		*/
	}

}
