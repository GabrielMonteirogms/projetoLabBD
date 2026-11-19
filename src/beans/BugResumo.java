package beans;

public class BugResumo {
    private int idBug;
    private String nomeBug;
    private String status;
    private String prioridade;
    private String nomeProjeto;
    private String nomeEquipe;
    private String funcionariosEquipe;

    public int getIdBug() {
        return idBug;
    }

    public void setIdBug(int idBug) {
        this.idBug = idBug;
    }

    public String getNomeBug() {
        return nomeBug;
    }

    public void setNomeBug(String nomeBug) {
        this.nomeBug = nomeBug;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(String prioridade) {
        this.prioridade = prioridade;
    }

    public String getNomeProjeto() {
        return nomeProjeto;
    }

    public void setNomeProjeto(String nomeProjeto) {
        this.nomeProjeto = nomeProjeto;
    }

    public String getNomeEquipe() {
        return nomeEquipe;
    }

    public void setNomeEquipe(String nomeEquipe) {
        this.nomeEquipe = nomeEquipe;
    }

    public String getFuncionariosEquipe() {
        return funcionariosEquipe;
    }

    public void setFuncionariosEquipe(String funcionariosEquipe) {
        this.funcionariosEquipe = funcionariosEquipe;
    }
}