package com.backbase.uitest;

import com.backbase.utils.Config;

/**
 * Created by BigFoot on 02.03.2016.
 */
public class BaseTest {
    String backbaseUrl = Config.getProperty("baseUrl");
    int driverTimout = Integer.parseInt(Config.getProperty("timeout"));
    String driver = Config.getProperty("driver");
}
