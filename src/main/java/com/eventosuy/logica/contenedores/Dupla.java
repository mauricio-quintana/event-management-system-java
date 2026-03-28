package com.eventosuy.logica.contenedores;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class Dupla {
    private String first;
    private String second;

    public Dupla(String str1, String str2) {
        this.first = str1;
        this.second = str2;
    }

    public String getFirst() {
        return first;
    }

    public String getSecond() {
        return second;
    }

}
