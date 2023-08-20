package br.com.fiap.pettech.dominio.categoria.entity;

import br.com.fiap.pettech.dominio.produto.entity.Produto;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "tb_categoria")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;

    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Instant createdAt;

    @ManyToMany(mappedBy = "categorias")
    private Set<Produto> produtos = new HashSet<>();

    public Categoria() {
    }

    public Categoria(Long id, String nome, Instant createdAt) {
        this.id = id;
        this.nome = nome;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public Categoria setId(Long id) {
        this.id = id;
        return this;
    }

    public String getNome() {
        return nome;
    }

    public Categoria setNome(String nome) {
        this.nome = nome;
        return this;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Categoria setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Set<Produto> getProdutos() {
        return produtos;
    }

    @PrePersist
    public void prePersist(){
        this.createdAt = Instant.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Categoria categoria = (Categoria) o;
        return Objects.equals(id, categoria.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
