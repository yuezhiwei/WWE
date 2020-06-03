package com.nc.user.test;

import com.nc.common.utils.CodecUtils;
import com.nc.user.pojo.User;
import org.junit.Test;

public class TestDemo {
    public static void main(String[] args) {

    }
    @Test
    public void testDemo() throws Exception {
        User user = new User();
        user.setSalt("f7bf1d3fb4cc4dbd8194d528de80531d");
        user.setPassword("123456");
        user.setPassword(CodecUtils.md5Hex(user.getPassword(), user.getSalt()));

        System.out.println(user);
    }
}
