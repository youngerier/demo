package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: wp
 * @date: 2018-11-22 15:57
 * @description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class JdbcTest {

    @Autowired
    private ProcedureManager procedureManager;

    @Test
    public void test() {

        String proc = "logStringValue";
        HashMap<String, BeanPropertyRowMapper> map = new HashMap<>();
        map.put("result", new BeanPropertyRowMapper<>(ReportResultParam.class));
        HashMap<String, Integer> p = new HashMap<>();
        p.put("userId", 12);
        Map<String, Object> s = procedureManager.execProcedure(proc, p, map);
    }
}
