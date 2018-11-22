package com.example.demo;

import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Map;


/**
 * @author: wp
 * @date: 2018-08-03 14:05
 * @description:
 */

@Component
public class ProcedureManager {

    public SimpleJdbcCall getSimpleJdbcCall() {
        ApplicationContext ctx = ApplicationContextProvider.getContext();
        DataSource dataSource = (DataSource) ctx.getBean("dataSource");
        return new SimpleJdbcCall(dataSource);
    }


    /**
     * exec procedure
     *
     * @param procedureName 存储过程名
     * @param o             param
     * @return
     */
    public Map<String, Object> execProcedure(String procedureName, Object o, @Nullable Map<String, BeanPropertyRowMapper> res) {


        BeanPropertySqlParameterSource source = new BeanPropertySqlParameterSource(o);
        String[] readablePropertyNames = source.getReadablePropertyNames();
        MapSqlParameterSource params = new MapSqlParameterSource();

        if (o instanceof Map) {
            ((Map) o).forEach((i, j) -> {
                params.addValue(i.toString(), j);
            });
        } else {
            for (String property : readablePropertyNames) {
                params.addValue(property, source.getValue(property));
            }
        }

        SimpleJdbcCall call = getSimpleJdbcCall()
                .withProcedureName(procedureName);
        if (res != null) {
            res.forEach(call::addDeclaredRowMapper);
        }
        return call.execute(params);
    }


}
