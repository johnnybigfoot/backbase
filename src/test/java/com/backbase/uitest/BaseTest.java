package com.backbase.uitest;

import com.backbase.utils.Config;

/**
 * Created by BigFoot on 02.03.2016.
 */
public class BaseTest {
    String backbaseUrl = Config.getProperty("baseUrl");
    int driverTimeoutInSeconds = Integer.parseInt(Config.getProperty("timeout"));
    String browserName = Config.getProperty("driver");
}
