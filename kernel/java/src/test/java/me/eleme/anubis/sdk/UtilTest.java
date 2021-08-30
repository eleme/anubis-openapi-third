package me.eleme.anubis.sdk;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class UtilTest {
    @Test
    public void test() throws Exception {
        Context context = new Context(null);
        Client client = new Client(context);
        Map<String,String> map = new HashMap<>();
        map.put("a","2");
        map.put("b","1");
        map.put("c","3");
        String sign = client.sign(map, null, null, "123456");
        System.out.println(sign);
    }
}
