package beans;

public class Equipe {
    private int id;
    private String nomeEquipe;
    private String lider;
    private String descricao;

    public Equipe() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNomeEquipe() { return nomeEquipe; }
    public void setNomeEquipe(String nomeEquipe) { this.nomeEquipe = nomeEquipe; }

    public String getLider() { return lider; }
    public void setLider(String lider) { this.lider = lider; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    @Override
    public String toString() {
        return this.nomeEquipe;
    }
}