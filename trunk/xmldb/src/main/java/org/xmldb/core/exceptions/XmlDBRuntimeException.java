/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.xmldb.core.exceptions;

import org.xmldb.core.ErrorCode;

/**
 *
 * @author Giacomo Stefano Gabriele
 */
public class XmlDBRuntimeException extends RuntimeException{

    private ErrorCode errorCode;

    public XmlDBRuntimeException(ErrorCode errorCode,String ...args) {
        super(getMessage(errorCode.getDescription(), args));
        this.errorCode = errorCode;
    }

     public XmlDBRuntimeException(ErrorCode errorCode,Throwable cause,String ...args) {
        super(getMessage(errorCode.getDescription(), args),cause);
        this.errorCode = errorCode;
    }

    public XmlDBRuntimeException(ErrorCode errorCode,Throwable cause) {
        super(errorCode.getDescription(), cause);
        this.errorCode = errorCode;
    }

    public XmlDBRuntimeException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public XmlDBRuntimeException(Throwable cause) {
        super(ErrorCode.ERRORE_INTERNO.getDescription(),cause);
        this.errorCode = ErrorCode.ERRORE_INTERNO;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }


    


    protected static String getMessage(final String template,String ...args){
        String message = template;
        int i=0;
        for(String a:args){
            message = message.replaceAll("{"+i+"}", a);
            i++;
        }

        return message;
    }

}
