package com.junwen;

import com.junwen.service.YunXiaoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;

@SpringBootTest
public class BaseTest {

    @Autowired
    private YunXiaoService yunXiaoService;

    @Test
    public void fun() throws ParseException {
        yunXiaoService.getDateData();
        //String data = dataService.getData("2021-11-15");
        //System.out.println(data);
    }


}
