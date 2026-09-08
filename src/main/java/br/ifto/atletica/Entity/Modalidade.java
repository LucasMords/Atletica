package br.ifto.atletica.Entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Modalidade {

    @Id
    private Long id;

    @Column(nullable = false)
    private String nome;

    @OneToMany(mappedBy = "modalidade")
    private List<Atleta> atletas;


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
