package org.example.testng;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

    private int startPoint = 0;
    private static final int MAX_TRY_COUNT = 1;

    @Override
    public boolean retry(ITestResult iTestResult) {
        if (!iTestResult.isSuccess()) {
            if (startPoint < MAX_TRY_COUNT) {
                startPoint++;
                return true;
            }
        }
        return false;
    }
}
