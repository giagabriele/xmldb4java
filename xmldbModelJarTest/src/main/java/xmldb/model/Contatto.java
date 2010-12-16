package xmldb.model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import xmldb.annotation.Attribute;
import xmldb.annotation.Entity;
import xmldb.annotation.ID;
import xmldb.annotation.OneToMany;

/**
 *
 * @author giacomo
 */
@Entity(name = "contatto")
public class Contatto {

    @ID
    private int id;
    @Attribute
    private String nome;
    @Attribute
    private String cognome;
    @Attribute
    private Date ultimaModifica;
    @OneToMany(classe=Dettaglio.class)
    private List<Dettaglio> dettagli = new LinkedList<Dettaglio>();

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getUltimaModifica() {
        return ultimaModifica;
    }

    public void setUltimaModifica(Date ultimaModifica) {
        this.ultimaModifica = ultimaModifica;
    }

    public List<Dettaglio> getDettagli() {
        return dettagli;
    }

    public void setDettagli(List<Dettaglio> dettagli) {
        this.dettagli = dettagli;
    }
}
