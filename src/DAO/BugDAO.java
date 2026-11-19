package DAO;

import beans.Bug;
import beans.BugResumo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javabd01.conexao.Conexao;

public class BugDAO {
    private Conexao conexao;
    private Connection conn;

    public BugDAO() {
        this.conexao = new Conexao();
        this.conn = this.conexao.getConexao();
    }

    public void inserir(Bug bug) {
        if (conn == null) {
            System.out.println("Erro: conexão com o banco nula ao inserir bug.");
            return;
        }

        String sql = "INSERT INTO bugs (nome, descricao, status, modulo, prioridade, data_abertura, id_projeto) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = this.conn.prepareStatement(sql)) {
            stmt.setString(1, bug.getNome());
            stmt.setString(2, bug.getDescricao());
            stmt.setString(3, bug.getStatus());
            stmt.setString(4, bug.getModulo());
            stmt.setString(5, bug.getPrioridade());

            if (bug.getDataAbertura() != null) {
                stmt.setDate(6, java.sql.Date.valueOf(bug.getDataAbertura()));
            } else {
                stmt.setDate(6, java.sql.Date.valueOf(LocalDate.now()));
            }

            if (bug.getIdProjeto() != null && bug.getIdProjeto() > 0) {
                stmt.setInt(7, bug.getIdProjeto());
            } else {
                stmt.setNull(7, Types.INTEGER);
            }

            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Erro ao inserir bug: " + ex.getMessage());
        }
    }

    public Bug getBug(int id) {
        if (conn == null) {
            System.out.println("Erro: conexão nula ao consultar bug por ID.");
            return null;
        }

        String sql = "SELECT * FROM bugs WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Bug b = new Bug();
                    b.setId(rs.getInt("id"));
                    b.setNome(rs.getString("nome"));
                    b.setDescricao(rs.getString("descricao"));
                    b.setStatus(rs.getString("status"));
                    b.setModulo(rs.getString("modulo"));
                    b.setPrioridade(rs.getString("prioridade"));

                    java.sql.Date dataBanco = rs.getDate("data_abertura");
                    if (dataBanco != null) {
                        b.setDataAbertura(dataBanco.toLocalDate());
                    }

                    int projId = rs.getInt("id_projeto");
                    b.setIdProjeto(rs.wasNull() ? null : projId);

                    return b;
                }
            }
            return null;
        } catch (SQLException ex) {
            System.out.println("Erro cabuloso ao consultar bug por ID: " + ex.getMessage());
            return null;
        }
    }

 

    public List<Bug> getBugs() {
        List<Bug> listaBugs = new ArrayList<>();
        if (conn == null) {
            System.out.println("Erro: conexão nula ao consultar bugs.");
            return listaBugs;
        }

        String sql = "SELECT * FROM bugs";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Bug b = new Bug();
                b.setId(rs.getInt("id"));
                b.setNome(rs.getString("nome"));
                b.setDescricao(rs.getString("descricao"));
                b.setStatus(rs.getString("status"));
                b.setModulo(rs.getString("modulo"));
                b.setPrioridade(rs.getString("prioridade"));

                java.sql.Date dataBanco = rs.getDate("data_abertura");
                if (dataBanco != null) {
                    b.setDataAbertura(dataBanco.toLocalDate());
                }

                int projId = rs.getInt("id_projeto");
                b.setIdProjeto(rs.wasNull() ? null : projId);

                listaBugs.add(b);
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao consultar todos os bugs: " + ex.getMessage());
        }
        return listaBugs;
    }

    public List<Bug> getBugsPorModulo(String modulo) {
        List<Bug> listaBugs = new ArrayList<>();
        if (conn == null) {
            System.out.println("Erro: conexão nula ao filtrar por módulo.");
            return listaBugs;
        }

        String sql = "SELECT * FROM bugs WHERE modulo = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, modulo);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Bug b = new Bug();
                    b.setId(rs.getInt("id"));
                    b.setNome(rs.getString("nome"));
                    b.setDescricao(rs.getString("descricao"));
                    b.setStatus(rs.getString("status"));
                    b.setModulo(rs.getString("modulo"));
                    b.setPrioridade(rs.getString("prioridade"));

                    java.sql.Date dataBanco = rs.getDate("data_abertura");
                    if (dataBanco != null) {
                        b.setDataAbertura(dataBanco.toLocalDate());
                    }

                    int projId = rs.getInt("id_projeto");
                    b.setIdProjeto(rs.wasNull() ? null : projId);

                    listaBugs.add(b);
                }
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao filtrar bugs por módulo: " + ex.getMessage());
        }
        return listaBugs;
    }

    public void editar(Bug bug) {
        if (conn == null) {
            System.out.println("Erro: conexão nula ao editar bug.");
            return;
        }

        String sql = "UPDATE bugs SET nome=?, descricao=?, status=?, modulo=?, prioridade=?, id_projeto=? WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, bug.getNome());
            stmt.setString(2, bug.getDescricao());
            stmt.setString(3, bug.getStatus());
            stmt.setString(4, bug.getModulo());
            stmt.setString(5, bug.getPrioridade());

            if (bug.getIdProjeto() != null && bug.getIdProjeto() > 0) {
                stmt.setInt(6, bug.getIdProjeto());
            } else {
                stmt.setNull(6, Types.INTEGER);
            }

            stmt.setInt(7, bug.getId());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Erro ao atualizar bug: " + ex.getMessage());
        }
    }

    public void excluir(int id) {
        if (conn == null) {
            System.out.println("Erro: conexão nula ao excluir bug.");
            return;
        }

        String sql = "DELETE FROM bugs WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Erro ao excluir bug: " + ex.getMessage());
        }
    }

    public int contarBugsPorStatus(String status) {
        if (conn == null) return 0;

        String sql = "SELECT COUNT(*) AS total FROM bugs WHERE status = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total");
                }
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao contar status: " + ex.getMessage());
        }
        return 0;
    }

    public int contarBugsPorPrioridade(String prioridade) {
        if (conn == null) return 0;

        String sql = "SELECT COUNT(*) AS total FROM bugs WHERE prioridade = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, prioridade);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total");
                }
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao contar prioridade: " + ex.getMessage());
        }
        return 0;
    }

    public List<Bug> getBugsFiltrados(String modulo, String prioridade) {
        List<Bug> listaBugs = new ArrayList<>();
        if (conn == null) {
            System.out.println("Erro: conexão nula ao filtrar bugs.");
            return listaBugs;
        }

        StringBuilder sql = new StringBuilder("SELECT * FROM bugs WHERE 1=1");

        if (!modulo.equals("Todos")) {
            sql.append(" AND modulo = ?");
        }
        if (!prioridade.equals("Todas")) {
            sql.append(" AND prioridade = ?");
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            int paramIndex = 1;
            if (!modulo.equals("Todos")) {
                stmt.setString(paramIndex++, modulo);
            }
            if (!prioridade.equals("Todas")) {
                stmt.setString(paramIndex++, prioridade);
            }

            System.out.println("SQL EXECUTADO: " + stmt.toString());

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Bug b = new Bug();
                    b.setId(rs.getInt("id"));
                    b.setNome(rs.getString("nome"));
                    b.setDescricao(rs.getString("descricao"));
                    b.setStatus(rs.getString("status"));
                    b.setModulo(rs.getString("modulo"));
                    b.setPrioridade(rs.getString("prioridade"));

                    java.sql.Date dataBanco = rs.getDate("data_abertura");
                    if (dataBanco != null) {
                        b.setDataAbertura(dataBanco.toLocalDate());
                    }

                    int projId = rs.getInt("id_projeto");
                    b.setIdProjeto(rs.wasNull() ? null : projId);

                    listaBugs.add(b);
                }
            }
            System.out.println("ITENS ENCONTRADOS PELO DAO: " + listaBugs.size());
        } catch (SQLException ex) {
            System.out.println("ERRO CRÍTICO NO SQL: " + ex.getMessage());
        }
        return listaBugs;
    }

    public List<BugResumo> getVisaoCompleta() {
        List<BugResumo> lista = new ArrayList<>();
        if (conn == null) {
            System.out.println("Erro: conexão nula ao carregar visão completa.");
            return lista;
        }

        String sql = "SELECT " +
                     "b.id AS bug_id, " +
                     "b.nome AS bug_nome, " +
                     "b.status, " +
                     "b.prioridade, " +
                     "p.nome_projeto, " +
                     "e.nome_equipe, " +
                     "GROUP_CONCAT(DISTINCT f.nome ORDER BY f.nome SEPARATOR ', ') AS funcionarios_equipe " +
                     "FROM bugs b " +
                     "LEFT JOIN projetos p ON b.id_projeto = p.id " +
                     "LEFT JOIN equipes e ON p.id_equipe = e.id " +
                     "LEFT JOIN funcionarios f ON f.id_equipe = e.id " +
                     "GROUP BY b.id, b.nome, b.status, b.prioridade, p.nome_projeto, e.nome_equipe " +
                     "ORDER BY b.id DESC";

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                BugResumo item = new BugResumo();
                item.setIdBug(rs.getInt("bug_id"));
                item.setNomeBug(rs.getString("bug_nome"));
                item.setStatus(rs.getString("status") != null ? rs.getString("status") : "Não informado");
                item.setPrioridade(rs.getString("prioridade") != null ? rs.getString("prioridade") : "Não informada");
                item.setNomeProjeto(rs.getString("nome_projeto") != null ? rs.getString("nome_projeto") : "Sem projeto");
                item.setNomeEquipe(rs.getString("nome_equipe") != null ? rs.getString("nome_equipe") : "Sem equipe");
                item.setFuncionariosEquipe(rs.getString("funcionarios_equipe") != null ? rs.getString("funcionarios_equipe") : "Sem funcionários");
                lista.add(item);
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao buscar visão completa: " + ex.getMessage());
        }
        return lista;
    }
}