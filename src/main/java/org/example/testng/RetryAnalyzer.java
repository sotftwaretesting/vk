package org.example.testng;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

    private int counter = 0;
    private static final int MAX_TRY_COUNT = 1;

    @Override
    public boolean retry(ITestResult iTestResult) {
        if (!iTestResult.isSuccess()) {
            if (counter < MAX_TRY_COUNT) {
                counter++;
                return true;
            }
        }
        return false;
    }
}
