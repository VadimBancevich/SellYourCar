package com.vb.api.dao;

import org.hibernate.dialect.MySQL8Dialect;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.StandardBasicTypes;

public class CustomMySQL8Dialect extends MySQL8Dialect {

    public CustomMySQL8Dialect() {
        super();
        registerFunction("json_extract_string", new StandardSQLFunction("json_extract", StandardBasicTypes.STRING));
    }

}
