package DAO;

import beans.Funcionario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import javabd01.conexao.Conexao;

public class FuncionarioDAO {
    private Conexao conexao;
    private Connection conn;

    public FuncionarioDAO() {
        this.conexao = new Conexao();
        this.conn = this.conexao.getConexao();
    }

    public void inserir(Funcionario f) {
        if (conn == null) {
            System.out.println("Erro: conexão com o banco nula ao inserir funcionário.");
            return;
        }

        String sql = "INSERT INTO funcionarios (nome, cargo, email, id_equipe) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, f.getNome());
            stmt.setString(2, f.getCargo());
            stmt.setString(3, f.getEmail());

            if (f.getIdEquipe() != null) {
                stmt.setInt(4, f.getIdEquipe());
            } else {
                stmt.setNull(4, Types.INTEGER);
            }

            stmt.executeUpdate();
            System.out.println("DEBUG -> Funcionário salvo com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao inserir funcionário: " + e.getMessage());
        }
    }

    public List<Funcionario> getFuncionarios() {
        List<Funcionario> lista = new ArrayList<>();
        if (conn == null) {
            System.out.println("Erro: conexão com o banco nula ao listar funcionários.");
            return lista;
        }

        String sql = "SELECT f.*, e.nome_equipe " +
                     "FROM funcionarios f " +
                     "LEFT JOIN equipes e ON f.id_equipe = e.id " +
                     "ORDER BY f.nome";

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Funcionario f = new Funcionario();
                f.setId(rs.getInt("id"));
                f.setNome(rs.getString("nome"));
                f.setCargo(rs.getString("cargo"));
                f.setEmail(rs.getString("email"));

                int eqId = rs.getInt("id_equipe");
                f.setIdEquipe(rs.wasNull() ? null : eqId);
                f.setNomeEquipe(rs.getString("nome_equipe"));

                lista.add(f);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar funcionários: " + e.getMessage());
        }
        return lista;
    }

    public Funcionario buscarPorId(int id) {
        if (conn == null) {
            System.out.println("Erro: conexão com o banco nula ao buscar funcionário.");
            return null;
        }

        String sql = "SELECT f.*, e.nome_equipe " +
                     "FROM funcionarios f " +
                     "LEFT JOIN equipes e ON f.id_equipe = e.id " +
                     "WHERE f.id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Funcionario f = new Funcionario();
                    f.setId(rs.getInt("id"));
                    f.setNome(rs.getString("nome"));
                    f.setCargo(rs.getString("cargo"));
                    f.setEmail(rs.getString("email"));

                    int eqId = rs.getInt("id_equipe");
                    f.setIdEquipe(rs.wasNull() ? null : eqId);
                    f.setNomeEquipe(rs.getString("nome_equipe"));

                    return f;
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar funcionário por ID: " + e.getMessage());
        }
        return null;
    }

    public void editar(Funcionario f) {
        if (conn == null) {
            System.out.println("Erro: conexão com o banco nula ao editar funcionário.");
            return;
        }

        String sql = "UPDATE funcionarios SET nome = ?, cargo = ?, email = ?, id_equipe = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, f.getNome());
            stmt.setString(2, f.getCargo());
            stmt.setString(3, f.getEmail());

            if (f.getIdEquipe() != null) {
                stmt.setInt(4, f.getIdEquipe());
            } else {
                stmt.setNull(4, Types.INTEGER);
            }

            stmt.setInt(5, f.getId());
            stmt.executeUpdate();
            System.out.println("DEBUG -> Funcionário atualizado com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao editar funcionário: " + e.getMessage());
        }
    }

    public void excluir(int id) {
        if (conn == null) {
            System.out.println("Erro: conexão com o banco nula ao excluir funcionário.");
            return;
        }

        String sql = "DELETE FROM funcionarios WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("DEBUG -> Funcionário excluído com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao excluir funcionário: " + e.getMessage());
        }
    }
}