package DAO;

import beans.Equipe;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javabd01.conexao.Conexao;

public class EquipeDAO {
    private Conexao conexao;
    private Connection conn;

    public EquipeDAO() {
        this.conexao = new Conexao();
        this.conn = this.conexao.getConexao();
    }

    public List<Equipe> getEquipes() {
        String sql = "SELECT * FROM equipes ORDER BY nome_equipe";
        List<Equipe> lista = new ArrayList<>();
        if (conn == null) {
            System.out.println("Erro: conexão nula ao listar equipes.");
            return lista;
        }
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Equipe e = new Equipe();
                e.setId(rs.getInt("id"));
                e.setNomeEquipe(rs.getString("nome_equipe"));
                e.setLider(rs.getString("lider"));
                e.setDescricao(rs.getString("descricao"));
                lista.add(e);
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao listar equipes: " + ex.getMessage());
        }
        return lista;
    }

    public Equipe buscarPorId(int id) {
        String sql = "SELECT * FROM equipes WHERE id = ?";
        if (conn == null) {
            return null;
        }
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Equipe e = new Equipe();
                    e.setId(rs.getInt("id"));
                    e.setNomeEquipe(rs.getString("nome_equipe"));
                    e.setLider(rs.getString("lider"));
                    e.setDescricao(rs.getString("descricao"));
                    return e;
                }
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao buscar equipe por ID: " + ex.getMessage());
        }
        return null;
    }

    public void inserir(Equipe e) {
        String sql = "INSERT INTO equipes (nome_equipe, lider, descricao) VALUES (?, ?, ?)";
        if (conn == null) {
            System.out.println("Erro: conexão nula ao inserir equipe.");
            return;
        }
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, e.getNomeEquipe());
            stmt.setString(2, e.getLider());
            stmt.setString(3, e.getDescricao());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Erro ao inserir equipe: " + ex.getMessage());
        }
    }
}