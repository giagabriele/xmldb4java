package org.xml.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 *
 * @author giacomo
 */
@Entity
@Table(name="dettaglio")
public class Dettaglio {

    public static final String TYPE_CELLULARE = "cellulare";
    public static final String TYPE_TELEFONO = "telefono";
    public static final String TYPE_MAIL = "email";
    @Id
    private int id;
    @Column
    private String dettaglio;

    public String getDettaglio() {
        return dettaglio;
    }

    public void setDettaglio(String dettaglio) {
        this.dettaglio = dettaglio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
