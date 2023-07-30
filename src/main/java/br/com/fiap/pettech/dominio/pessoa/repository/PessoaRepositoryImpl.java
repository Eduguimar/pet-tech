package br.com.fiap.pettech.dominio.pessoa.repository;

import br.com.fiap.pettech.dominio.pessoa.entity.Pessoa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
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
        return null;
    }

    @Override
    public Pessoa save(Pessoa pessoa) {
        return null;
    }

    @Override
    public Pessoa update(Long id, Pessoa pessoa) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

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
