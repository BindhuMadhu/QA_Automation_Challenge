package com.framework.report;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.relevantcodes.extentreports.model.Log;

import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestResult;

public class ExtentReporter {
	
	 // Fields
	
	private ExtentReports report;
	private String datePattern = "dd:MM:yyyy-HH:mm:ss";
	
	public ExtentReporter(String file) {
        report = new ExtentReports(file);
        report.loadConfig(new File("src/main/resources/extent-config.xml"));
    }
	
	public void generateReport(List<ISuite> suites) {
		suites.forEach((s)->{
			Map<String,ISuiteResult> results = s.getResults();
			results.values().forEach((r)->{
				ITestContext context = r.getTestContext();
				String test_name = context.getName();
                ExtentTest test = report.startTest(test_name);
                test.getTest().setStartedTime(context.getStartDate());
                test.getTest().setEndedTime(context.getEndDate());
                // for pass
                context.getPassedTests().getAllResults().forEach((p)->{
                    if(p.isSuccess())
                    createNode(test,p);
                });
                //for failed
                context.getFailedTests().getAllResults().forEach((f)->{
                    createNode(test,f);
                });
                report.endTest(test);
				});
		});
		report.flush();
	}
	
	public void createNode(ExtentTest parent, ITestResult result){
        String methodName = result.getMethod().getMethodName();
        String description = result.getMethod().getDescription();
        if(result.getAttribute("test_start") != null) {
            String start_time = (String) result.getAttribute("test_start");
            String end_time = (String) result.getAttribute("test_end");
            ExtentTest test = new ExtentTest(methodName, description);
            test.getTest().setStartedTime(getDate(start_time, datePattern));
            test.getTest().setEndedTime(getDate(end_time, datePattern));
            test.getTest().setLog(getTestMethodLog(result));
            parent.appendChild(test);
        }else{
            ExtentTest test = new ExtentTest(methodName, description);
            test.getTest().setStatus(LogStatus.SKIP);
            parent.appendChild(test);
        }

    }
	
	 public List<Log> getTestMethodLog(ITestResult result){
	        List<Log> logs = new LinkedList<>();
	        List<String> lines = (List<String>) result.getAttribute("log");
	        if(lines != null){
	            lines.forEach((l)->{
	                Log log = new Log();
	                log.setDetails(l);
	                log.setLogStatus(LogStatus.INFO);
	                logs.add(log);
	            });
	        }
	        Log log = new Log();
	        if(result.getStatus() == 1){
	            log.setDetails("test passed");
	            log.setLogStatus(LogStatus.PASS);
	        }else{
	            log.setDetails("test failed");
	            log.setLogStatus(LogStatus.FAIL);
	        }
	        logs.add(log);
	        return logs;
	    }
	 
	 public Date getDate(String date_string, String format){
	        DateFormat df = new SimpleDateFormat(format);
	        Date date = null;
	        try {
	            date = df.parse(date_string);
	        } catch (ParseException e) {
	            e.printStackTrace();
	        }
	        return date;
	    }

}
