package xmldb.model;

import org.xmldb.annotation.Attribute;

/**
 *
 * @author giacomo
 */
public class Email extends Dettaglio {

    @Attribute
    private String tipo = TYPE_MAIL;

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
