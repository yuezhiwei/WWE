package com.nc.auth.test;

import com.nc.auth.NcAuthApplication;
import com.nc.auth.utils.RsaUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

public class TestDemo {

    String pubKeyPath = "C:\\temp\\rsa\\rsa.pub";
    String priKeyPath = "C:\\temp\\rsa\\rsa.pri";
    @Test
    public void testDemo() throws Exception {
        RsaUtils.generateKey(pubKeyPath,priKeyPath,"111");
    }


}
