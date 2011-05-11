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
package org.xmldb.core.commons.log;

/**
 * 
 */
import org.apache.log4j.Logger;

public class LogHelper {

    // returns the classname of the caller's caller
    protected static String getCallerClassName() {
        return Thread.currentThread().getStackTrace()[4].getClassName();
    }

    /* --------------------------------------------------------------------- */
    public static void debug(Object obj) {
        debug(obj, getCallerClassName());
    }

    public static void info(Object obj) {
        info(obj, getCallerClassName());
    }

    public static void warn(Object obj) {
        warn(obj, getCallerClassName());
    }

    public static void error(Throwable e) {
        error(e, getCallerClassName());
    }

    public static void fatal(Object obj) {
        fatal(obj, getCallerClassName());
    }

    /* --------------------------------------------------------------------- */
    public static void debug(Object obj, Class<? extends Object> theClass) {
        Logger.getLogger(theClass).debug(obj);
    }

    public static void info(Object obj, Class<? extends Object> theClass) {
        Logger.getLogger(theClass).info(obj);
    }

    public static void warn(Object obj, Class<? extends Object> theClass) {
        Logger.getLogger(theClass).warn(obj);
    }

    public static void error(Throwable e, Class<? extends Object> theClass) {
        String message = e.toString();
        Logger.getLogger(theClass).error(message.replace('\t', ' '));
        Logger.getLogger(theClass).error(message, e);
    }

    public static void fatal(Object obj, Class<? extends Object> theClass) {
        Logger.getLogger(theClass).fatal(obj);
    }

    /* --------------------------------------------------------------------- */
    public static void debug(Object obj, String name) {
        if(Logger.getLogger(name).isDebugEnabled()){
            Logger.getLogger(name).debug(obj);
        }
    }

    public static void info(Object obj, String name) {
        Logger.getLogger(name).info(obj);
    }

    public static void warn(Object obj, String name) {
        Logger.getLogger(name).warn(obj);
    }

    public static void error(Throwable e, String name) {
        String message = e.toString();
        Logger.getLogger(name).error(message.replace('\t', ' '));
        Logger.getLogger(name).error(message, e);
    }

    public static void fatal(Object obj, String name) {
        Logger.getLogger(name).fatal(obj);
    }
}
