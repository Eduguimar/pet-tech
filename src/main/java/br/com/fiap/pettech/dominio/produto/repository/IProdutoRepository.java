package br.com.fiap.pettech.dominio.produto.repository;

import br.com.fiap.pettech.dominio.produto.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IProdutoRepository extends JpaRepository<Produto, UUID> {
}
