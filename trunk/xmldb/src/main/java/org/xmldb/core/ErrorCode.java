/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.xmldb.core;

/**
 *
 * @author Giacomo Stefano Gabriele
 */
public enum ErrorCode {

    ESITO_POSITIVO("Z00","Esito Positivo"),

    RESOURCE_BUNDLE_NON_TROVATO("Z10","Il file di {0}.properties non è stato trovato nel classpath!"),
    PROPRIETA_MANCANTE("Z11","La proprietà  \"{0}\" non e' stata configurata!!!"),
    
    CAMPO_NON_TROVATO("Z30","Il campo [{0}] non esiste"),


    ERRORE_MARSHAL_XML("Z50","Errore Marshal Xml"),

    ERRORE_ANNOTATION_REQUIRED("Z60","La classe {0} deve avere l'annotation {1}"),



    ERRORE_XPATH("Z70","Errore Xpath"),
    ERRORE_XPATH_FUNCTION_OR("Z71","OR Function:una delle due sintassi xpath è null"),
    ERRORE_XPATH_FUNCTION_BEETWEN("Z72","Errore between date. pattern={0}, dataInizio={1}, dataFine{2}"),

    
    ERRORE_VALIDAZIONE_XML("Z80","Errore Validazione"),


    ERRORE_TRANSACTION_NOT_SUPPORTED("Z93","Transazione non supportata!!"),
    ERRORE_TRANSACTION_NOT_ACTIVE("Z94","Transazione non attiva!!"),
    ERRORE_ELEMENTO_NON_TROVATO("Z95","L'elemento non è stato trovato! classe:{0}, id:{1}, xpath:{2}"),
    ERRORE_DATABASE_URL("Z96","Errore URL, Usa <engine>:<path o nome db>!! "),
    ERRORE_ENGINE("Z97","Engine \"{0}\" non supportato. Supportati \""+XmlDBConstants.ENGINE_LOCALE+"\",\""+XmlDBConstants.ENGINE_MEMORY+"\""),
    ERRORE_NON_SUPPORTATO("Z98","Not supported yet"),
    ERRORE_INTERNO("Z99","Errore Interno");



    private String code;
    private String description;

    private ErrorCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    
}
