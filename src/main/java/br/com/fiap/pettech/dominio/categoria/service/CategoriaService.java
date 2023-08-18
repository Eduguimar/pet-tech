package br.com.fiap.pettech.dominio.categoria.service;

import br.com.fiap.pettech.dominio.categoria.dto.CategoriaDTO;
import br.com.fiap.pettech.dominio.categoria.entity.Categoria;
import br.com.fiap.pettech.dominio.categoria.repository.ICategoriaRepository;
import br.com.fiap.pettech.exception.service.ControllerNotFoundException;
import br.com.fiap.pettech.exception.service.DatabaseException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private ICategoriaRepository repository;

    public Page<CategoriaDTO> findAll(PageRequest pageRequest) {
        Page<Categoria> categorias = repository.findAll(pageRequest);

        return categorias.map(CategoriaDTO::new);
    }

    public CategoriaDTO findById(Long id) {
        Optional<Categoria> optionalCategoria = repository.findById(id);
        Categoria categoria = optionalCategoria.orElseThrow(() -> new ControllerNotFoundException("Categoria não encontrada"));

        return new CategoriaDTO(categoria);
    }

    public CategoriaDTO save(CategoriaDTO dto) {
        Categoria entity = new Categoria();
        mapperDtoToEntity(dto, entity);
        Categoria categoriaSaved = repository.save(entity);

        return new CategoriaDTO(categoriaSaved);
    }

    @Transactional
    public CategoriaDTO update(Long id, CategoriaDTO dto) {
        try {
            Categoria entity = repository.getOne(id);
            mapperDtoToEntity(dto, entity);
            entity = repository.save(entity);

            return new CategoriaDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ControllerNotFoundException("Categoria não encontrada: " + id);
        }
    }

    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ControllerNotFoundException("Categoria não encontrada: " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Violação de integridade");
        }

    }

    private void mapperDtoToEntity(CategoriaDTO dto, Categoria entity) {
        entity.setNome(dto.getNome());
    }
}
