package com.framework.report;

import com.framework.util.PropertiesUtils;
import org.apache.commons.io.FileUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.testng.*;
import org.testng.xml.XmlSuite;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class TestNgReporter implements ITestListener, IExecutionListener, ISuiteListener, IReporter {
	
	private final PropertiesUtils utils = new PropertiesUtils("src/main/Resources/test.properties");
    private final String HTML_REPORT_FILE = "html.report.file";
    /**
     * Invoked before the TestNG run starts.
     */
    @Override
    public void onExecutionStart() {
        File file = new File(utils.getProperty(HTML_REPORT_FILE));
        if(!file.exists()){
            try {
                FileUtils.forceMkdir(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Invoked once all the suites have been run.
     */
    @Override
    public void onExecutionFinish() {

    }

    /**
     * Generate a report for the given suites into the specified output directory.
     *
     * @param xmlSuites
     * @param suites
     * @param outputDirectory
     */
    @Override
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
        if(utils.getProperty("generate.extent.report").equalsIgnoreCase("true")){
            ExtentReporter reporter = new ExtentReporter("./target/html-report/index.html");
            reporter.generateReport(suites);
        }
    }

    /**
     * This method is invoked before the SuiteRunner starts.
     *
     * @param suite
     */
    @Override
    public void onStart(ISuite suite) {

    }

    /**
     * This method is invoked after the SuiteRunner has run all
     * the test suites.
     *
     * @param suite
     */
    @Override
    public void onFinish(ISuite suite) {

    }

    /**
     * Invoked each time before a test will be invoked.
     * The <code>ITestResult</code> is only partially filled with the references to
     * class, method, start millis and status.
     *
     * @param result the partially filled <code>ITestResult</code>
     * @see ITestResult#STARTED
     */
    @Override
    public void onTestStart(ITestResult result) {
        Reporter.log("started test : "+result.getMethod().getMethodName(),true);
        result.setAttribute("test_start",getDate());
    }

    /**
     * Invoked each time a test succeeds.
     *
     * @param result <code>ITestResult</code> containing information about the run test
     * @see ITestResult#SUCCESS
     */
    @Override
    public void onTestSuccess(ITestResult result) {
        Reporter.log("status : Passed",true);
        result.setAttribute("log",Reporter.getOutput(result));
        result.setAttribute("test_end",getDate());
    }

    /**
     * Invoked each time a test fails.
     *
     * @param result <code>ITestResult</code> containing information about the run test
     * @see ITestResult#FAILURE
     */
    @Override
    public void onTestFailure(ITestResult result) {
        Reporter.log("status : Failed",true);
        result.setAttribute("log",Reporter.getOutput(result));
        result.setAttribute("test_end",getDate());
    }

    /**
     * Invoked each time a test is skipped.
     *
     * @param result <code>ITestResult</code> containing information about the run test
     * @see ITestResult#SKIP
     */
    @Override
    public void onTestSkipped(ITestResult result) {

    }

    /**
     * Invoked each time a method fails but has been annotated with
     * successPercentage and this failure still keeps it within the
     * success percentage requested.
     *
     * @param result <code>ITestResult</code> containing information about the run test
     * @see ITestResult#SUCCESS_PERCENTAGE_FAILURE
     */
    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }

    /**
     * Invoked after the test class is instantiated and before
     * any configuration method is called.
     *
     * @param context
     */
    @Override
    public void onStart(ITestContext context) {

    }

    /**
     * Invoked after all the tests have run and all their
     * Configuration methods have been called.
     *
     * @param context
     */
    @Override
    public void onFinish(ITestContext context) {

    }

    public String getDate(){
        DateTimeFormatter dateFormat = DateTimeFormat.forPattern("dd:MM:yyyy-HH:mm:ss");
        DateTime time = new DateTime();
        return dateFormat.print(time);
    }
}
