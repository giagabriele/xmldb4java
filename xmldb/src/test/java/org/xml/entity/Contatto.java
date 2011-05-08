/*
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
package org.xml.entity;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
 *
 * @author giacomo
 */
@Entity
public class Contatto {

    @Id
    @GeneratedValue
    private int id;
    @Column
    private String nome;
    @Column
    private String cognome;
    @Column
    private Timestamp ultimaModifica;
    @Transient
    private List<Dettaglio> dettagli = new LinkedList<Dettaglio>();

    public Contatto(){
        id = 1;
        nome = "Giacomo";
        cognome = "Gabriele";
        ultimaModifica = new Timestamp(System.currentTimeMillis());
    }

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

    public Timestamp getUltimaModifica() {
        return ultimaModifica;
    }

    public void setUltimaModifica(Timestamp ultimaModifica) {
        this.ultimaModifica = ultimaModifica;
    }

    

    public List<Dettaglio> getDettagli() {
        return dettagli;
    }

    public void setDettagli(List<Dettaglio> dettagli) {
        this.dettagli = dettagli;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Contatto other = (Contatto) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + this.id;
        return hash;
    }

    

    @Override
    public String toString() {
        return "Contatto{" + "\nid=" + id + "\nnome=" + nome + "\ncognome=" + cognome + "\nultimaModifica=" + ultimaModifica + "\ndettagli=" + dettagli + "\n}";
    }


}
