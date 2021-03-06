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
package org.xml.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
 * 
 * @author Giacomo Stefano Gabriele
 */
@Entity
public class Contatto {

    @Id
    private int id;
    private String nome;
    private String cognome;
    private Timestamp ultimaModifica;
    private String vetor[] = new String[]{"A","B","C"};
    private Byte[] valori = new Byte[]{0,1,2,3};
    private Long[] valoriLong = new Long[]{100L,200L,300L};

    private List<String> list = new ArrayList<String>();
    @Transient
    private List<Dettaglio> dettagli = new LinkedList<Dettaglio>();

    public Contatto(){
        id = 1;
        nome = "Giacomo";
        cognome = "Gabriele";
        ultimaModifica = new Timestamp(System.currentTimeMillis());
        
        list.add("LIST1");
        list.add("LIST2");
        list.add("LIST3");
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

    public String[] getVetor() {
        return vetor;
    }

    public void setVetor(String[] vetor) {
        this.vetor = vetor;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public Byte[] getValori() {
        return valori;
    }

    public void setValori(Byte[] valori) {
        this.valori = valori;
    }

    public Long[] getValoriLong() {
        return valoriLong;
    }

    public void setValoriLong(Long[] valoriLong) {
        this.valoriLong = valoriLong;
    }

    

    @Override
    public String toString() {
        return "Contatto{" + "\nid=" + id + "\nnome=" + nome + "\ncognome=" + cognome + "\nultimaModifica=" + ultimaModifica + "\nvetor=" + vetor + "\nlist=" + list + "\ndettagli=" + dettagli + '}';
    }

    

  
}
