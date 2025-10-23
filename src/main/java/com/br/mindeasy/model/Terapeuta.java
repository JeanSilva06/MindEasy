package com.br.mindeasy.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("TERAPEUTA")
public class Terapeuta extends Usuario {

    @Column(length = 10)
    private String crm;

    @Column(length = 200)
    private String especialidade;

    public Terapeuta() {
    }

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }
    
    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }
}