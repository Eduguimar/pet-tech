package br.com.fiap.pettech.dominio.pessoa.repository;

import br.com.fiap.pettech.dominio.pessoa.entity.Pessoa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class PessoaRepositoryImpl implements IPessoaRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PessoaRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Pessoa> findAll(int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        String sql = "SELECT * FROM pessoas LIMIT ? OFFSET ?";

        return jdbcTemplate.query(sql, new Object[]{pageSize, offset}, new PessoaRowMapper());
    }

    @Override
    public Pessoa findById(Long id) {
        String sql = "SELECT * FROM pessoas WHERE id = ?";
        List<Pessoa> pessoas = jdbcTemplate.query(sql, new Object[]{id}, new PessoaRowMapper());

        return pessoas.isEmpty() ? null : pessoas.get(0);
    }

    @Override
    public Pessoa save(Pessoa pessoa) {
        try {
            String sql = "INSERT INTO pessoas(nome, cpf, email, nascimento) VALUES(?,?,?,?)";
            this.jdbcTemplate.update(sql,
                    pessoa.getNome(),
                    pessoa.getCpf(),
                    pessoa.getEmail(),
                    Date.valueOf(pessoa.getNascimento()));

            return pessoa;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Pessoa update(Long id, Pessoa pessoa) {
        try {
            String sql = "UPDATE pessoas SET nome=?, cpf=?, email=?, nascimento=? WHERE id = ?";
            this.jdbcTemplate.update(sql,
                    pessoa.getNome(),
                    pessoa.getCpf(),
                    pessoa.getEmail(),
                    Date.valueOf(pessoa.getNascimento()),
                    id);

            return pessoa;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM pessoas WHERE id = ?";
        this.jdbcTemplate.update(sql, id);
    }

    private static class PessoaRowMapper implements RowMapper<Pessoa> {
        @Override
        public Pessoa mapRow(ResultSet rs, int rowNum) throws SQLException {
            Pessoa pessoa = new Pessoa();
            pessoa.setId(rs.getLong("id"));
            pessoa.setNome(rs.getString("nome"));
            pessoa.setEmail(rs.getString("email"));
            pessoa.setCpf(rs.getString("cpf"));
            pessoa.setNascimento(rs.getDate("nascimento").toLocalDate());

            return pessoa;
        }
    }
}
