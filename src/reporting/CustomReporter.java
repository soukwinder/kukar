package reporting;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.testng.IReporter;
import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.xml.XmlSuite;

public class CustomReporter implements IReporter{
    @Override
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites,
        String outputDirectory) {
        //Iterating over each suite included in the test
    	System.out.println("Total suites - "+ (suites.size()));
   	//suites.remove(suites.size()-1);
        for (int suiteNum=0;suiteNum<suites.size();suiteNum++) {
        	ISuite currentSuite  = suites.get(suiteNum);
            //Following code gets the suite name
            String suiteName = currentSuite.getName();
	    //Getting the results for the said suite
	    Map<String,ISuiteResult> suiteResults = currentSuite.getResults();
	    System.out.println("Results for Suite ---- " +suiteName);
	    System.out.println(suiteResults);
	    Set<String> testCaseNames = suiteResults.keySet();
	    Iterator<String> it = testCaseNames.iterator();
	    
	    while(it.hasNext()){
	    	String testName = it.next();
	    	System.out.println("Test Case Name " + testName);
	    	ISuiteResult result = suiteResults.get(testName);
	    	
	    
	    	ITestNGMethod[] allTestMethods= result.getTestContext().getAllTestMethods();
	    	IResultMap passedTestMethods = result.getTestContext().getPassedTests();
	    	IResultMap failedTestMethods = result.getTestContext().getFailedTests();
	    	IResultMap skippedTestMethods = result.getTestContext().getSkippedTests();

	    	for(int i=0;i<allTestMethods.length;i++){
	    		String testMethodName=allTestMethods[i].getMethodName();
	    		//System.out.println(testMethodName);
	    		if(passedTestMethods.getAllMethods().contains(allTestMethods[i]))
	    			System.out.println(testMethodName +" -- Pass" );
	    		else if(failedTestMethods.getAllMethods().contains(allTestMethods[i])){
	    			System.out.println(testMethodName +" -- Fail" );
	    			
	    			Collection<ITestNGMethod>  methods = failedTestMethods.getAllMethods();
	    			
	    			Iterator<ITestNGMethod> iterator = methods.iterator();
	    			while(iterator.hasNext()){
	    				ITestNGMethod meth = iterator.next();
	    				if(testMethodName.equals(meth.getMethodName())){
	    					Set<ITestResult> results=failedTestMethods.getResults(meth);
	    					Iterator<ITestResult> result_iter =results.iterator();
	    					while(result_iter.hasNext()){
	    						ITestResult res = result_iter.next();
	    						System.out.println("Reason for FAILURE - "+res.getThrowable().getMessage());
	    					
	    					}
	    				}
	    				
	    			}
	    		}
	    		else if(skippedTestMethods.getAllMethods().contains(allTestMethods[i])){
	    			System.out.println(testMethodName +" -- Skipped" );
	    			
	    			Collection<ITestNGMethod>  methods = skippedTestMethods.getAllMethods();
	    			Iterator<ITestNGMethod> iterator = methods.iterator();
	    			while(iterator.hasNext()){
	    				ITestNGMethod meth = iterator.next();
	    				if(testMethodName.equals(meth.getMethodName())){
	    					Set<ITestResult> results=skippedTestMethods.getResults(meth);
	    					Iterator<ITestResult> result_iter =results.iterator();
	    					while(result_iter.hasNext()){
	    						ITestResult res = result_iter.next();
	    						System.out.println("Reason for SKIPPING - "+res.getThrowable().getMessage());
	    					
	    					}
	    				}
	    				
	    			}
	    			
	    		}

	    		
	    	}
	    	
	    }
	    
	    
	    
	    
	    
	    
	    
        }
    }
}
/*
for (ISuiteResult sr : suiteResults.values()) {
    ITestContext tc = sr.getTestContext();
    System.out.println("Passed tests for suite '" + suiteName +
         "' is:" + tc.getPassedTests().getAllResults().size());
    System.out.println("Failed tests for suite '" + suiteName +
         "' is:" + 
         tc.getFailedTests().getAllResults().size());
    System.out.println("Skipped tests for suite '" + suiteName +
         "' is:" + 
         tc.getSkippedTests().getAllResults().size());
  }
*/