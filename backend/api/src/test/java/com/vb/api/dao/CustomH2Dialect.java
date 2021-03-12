package com.vb.api.dao;

import org.hibernate.dialect.H2Dialect;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.StandardBasicTypes;

public class CustomH2Dialect extends H2Dialect {

    public CustomH2Dialect() {
        super();
        registerFunction("json_extract_string", new StandardSQLFunction("json_extract", StandardBasicTypes.STRING));
    }

}
