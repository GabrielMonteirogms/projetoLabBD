package DAO;

import beans.Projeto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javabd01.conexao.Conexao;

public class ProjetoDAO {
    private Conexao conexao;
    private Connection conn;

    public ProjetoDAO() {
        this.conexao = new Conexao();
        this.conn = this.conexao.getConexao();
    }

    public void inserir(Projeto p) {
        if (conn == null) {
            System.out.println("Erro: conexão com o banco nula ao inserir projeto.");
            return;
        }

        String sql = "INSERT INTO projetos (nome_projeto, descricao, cliente, data_inicio, id_equipe) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, p.getNomeProjeto());
            stmt.setString(2, p.getDescricao());
            stmt.setString(3, p.getCliente());

            if (p.getDataInicio() != null) {
                stmt.setDate(4, java.sql.Date.valueOf(p.getDataInicio()));
            } else {
                stmt.setDate(4, java.sql.Date.valueOf(LocalDate.now()));
            }

            if (p.getIdEquipe() != null) {
                stmt.setInt(5, p.getIdEquipe());
            } else {
                stmt.setNull(5, Types.INTEGER);
            }

            stmt.executeUpdate();
            System.out.println("DEBUG -> Projeto salvo com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao inserir projeto: " + e.getMessage());
        }
    }

    public List<Projeto> getProjetos() {
        List<Projeto> lista = new ArrayList<>();
        if (conn == null) {
            System.out.println("Erro: conexão com o banco nula ao listar projetos.");
            return lista;
        }

        String sql = "SELECT p.*, e.nome_equipe " +
                     "FROM projetos p " +
                     "LEFT JOIN equipes e ON p.id_equipe = e.id " +
                     "ORDER BY p.nome_projeto";

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Projeto p = new Projeto();
                p.setId(rs.getInt("id"));
                p.setNomeProjeto(rs.getString("nome_projeto"));
                p.setDescricao(rs.getString("descricao"));
                p.setCliente(rs.getString("cliente"));

                java.sql.Date dataBanco = rs.getDate("data_inicio");
                if (dataBanco != null) {
                    p.setDataInicio(dataBanco.toLocalDate());
                }

                int eqId = rs.getInt("id_equipe");
                p.setIdEquipe(rs.wasNull() ? null : eqId);
                p.setNomeEquipe(rs.getString("nome_equipe"));

                lista.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar projetos: " + e.getMessage());
        }
        return lista;
    }

    public Projeto getPorId(int id) {
        if (conn == null) {
            System.out.println("Erro: conexão com o banco nula ao buscar projeto.");
            return null;
        }

        String sql = "SELECT p.*, e.nome_equipe " +
                     "FROM projetos p " +
                     "LEFT JOIN equipes e ON p.id_equipe = e.id " +
                     "WHERE p.id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Projeto p = new Projeto();
                    p.setId(rs.getInt("id"));
                    p.setNomeProjeto(rs.getString("nome_projeto"));
                    p.setDescricao(rs.getString("descricao"));
                    p.setCliente(rs.getString("cliente"));

                    java.sql.Date dataBanco = rs.getDate("data_inicio");
                    if (dataBanco != null) {
                        p.setDataInicio(dataBanco.toLocalDate());
                    }

                    int eqId = rs.getInt("id_equipe");
                    p.setIdEquipe(rs.wasNull() ? null : eqId);
                    p.setNomeEquipe(rs.getString("nome_equipe"));

                    return p;
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar projeto por ID: " + e.getMessage());
        }
        return null;
    }

    public void editar(Projeto p) {
        if (conn == null) {
            System.out.println("Erro: conexão com o banco nula ao editar projeto.");
            return;
        }

        String sql = "UPDATE projetos SET nome_projeto = ?, descricao = ?, cliente = ?, id_equipe = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, p.getNomeProjeto());
            stmt.setString(2, p.getDescricao());
            stmt.setString(3, p.getCliente());

            if (p.getIdEquipe() != null) {
                stmt.setInt(4, p.getIdEquipe());
            } else {
                stmt.setNull(4, Types.INTEGER);
            }

            stmt.setInt(5, p.getId());
            stmt.executeUpdate();
            System.out.println("DEBUG -> Projeto atualizado com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao editar projeto: " + e.getMessage());
        }
    }

    public void excluir(int id) {
        if (conn == null) {
            System.out.println("Erro: conexão com o banco nula ao excluir projeto.");
            return;
        }

        String sql = "DELETE FROM projetos WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("DEBUG -> Projeto excluído com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao excluir projeto: " + e.getMessage());
        }
    }
}