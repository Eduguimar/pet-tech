package br.com.fiap.pettech.dominio.pessoa.controller;

import br.com.fiap.pettech.dominio.pessoa.entity.Pessoa;
import br.com.fiap.pettech.dominio.pessoa.repository.IPessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("pessoas")
public class PessoaController {

    private final IPessoaRepository pessoaRepository;

    @Autowired
    public PessoaController(IPessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    @GetMapping
    public ResponseEntity<List<Pessoa>> findAll(
            @RequestParam(value = "pagina", defaultValue = "1") Integer pagina,
            @RequestParam(value = "tamanho", defaultValue = "10") Integer tamanho
    ) {
        var pessoas = pessoaRepository.findAll(pagina, tamanho);
        return ResponseEntity.ok(pessoas);
    }
}
