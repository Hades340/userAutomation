package Flows;

import static org.testng.Assert.assertTrue;

import java.util.Map;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import Actions.LoginFacebookActions;
import readExcel.dataMap;
import readExcel.excelFile;
import utils.contains;
import utils.setup;

public class LoginFacebook extends setup{
	private LoginFacebookActions login = new LoginFacebookActions();
	@Test(dataProvider = "login",priority = 1)
	public void testForLoginPage(String No, String Testname, String Username, String Password, String Alert) {
		int row = Integer.parseInt(No);
		try {
			assertTrue(login.checkLoginPage(driver));
			testLogs.log(LogStatus.PASS, "Check page facebook success. ", "");
			assertTrue(login.checkLogin(driver,Username,Password));
			testLogs.log(LogStatus.PASS, "Input username and password success", "");
			assertTrue(login.checkMessage(driver,Alert));
			testLogs.log(LogStatus.PASS, "Check alert: "+Alert+" success", "");
			excelFile.setPass(dataMap.login.Result.ordinal(), row, dataMap.login.Error.ordinal());
		} catch (AssertionError e) {
			// TODO: handle exception
			excelFile.setFail(dataMap.login.Result.ordinal(), row, dataMap.login.Error.ordinal());
			testLogs.log(LogStatus.FAIL, testName+" is failed", "");
			assertTrue(false);
		}
	}
	@DataProvider(name ="login")
	public static Object[][] getData(ITestContext context){
		Map<String, String> testParams = context.getCurrentXmlTest().getLocalParameters();
		String testData = testParams.get("exelData");
		System.out.println(testData);
		excelFile.getSetUp(testData);

		excelFile.setExcelInfo(contains.dataFolder + contains.fileExcelName, contains.sheetName);
		Object[][] reArr = excelFile.getData(contains.testCaseName,
				dataMap.login.Result.ordinal(),
				dataMap.login.Testname.ordinal());
		return (reArr);
	}
}
