/*
 * Copyright 2011 Giacomo Stefano Gabriele
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.xmldb.core.commons.util;

import java.util.concurrent.atomic.AtomicBoolean;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.ArrayConverter;
import org.apache.commons.beanutils.converters.StringConverter;

/**
 *
 * @author Giacomo Stefano Gabriele
 */
public class BeanConvert {

    private static AtomicBoolean configurated = new AtomicBoolean(false);

    public static void configure(){
        if(!configurated.get()){
            BeanUtilsBean.setInstance(new BeanUtilsBean2());

            registerArray(String[].class);
            registerArray(boolean[].class);
            registerArray(Boolean[].class);
            registerArray(byte[].class);
            registerArray(Byte[].class);
            registerArray(short[].class);
            registerArray(Short[].class);
            registerArray(int[].class);
            registerArray(Integer[].class);
            registerArray(long[].class);
            registerArray(Long[].class);
            registerArray(float[].class);
            registerArray(Float[].class);
            registerArray(double[].class);
            registerArray(Double[].class);
            registerArray(char[].class);
            registerArray(Character[].class);
            registerArray(java.util.Date[].class);
            registerArray(java.sql.Date[].class);
            registerArray(java.sql.Timestamp[].class);

            configurated.set(true);
        }
    }

    private static void registerArray(Class klass){
        ArrayConverter converter = new ArrayConverter(klass, new StringConverter(), 0);
        converter.setOnlyFirstToString(false);
        ConvertUtils.register(converter, klass);

    }
}
