package xmldb.model;

import xmldb.annotation.Attribute;

/**
 *
 * @author giacomo
 */
public class Telefono extends Dettaglio {

    @Attribute
    private String tipo = TYPE_TELEFONO;

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
