package javabd01;
import Dashboard.DashboardBugs;
import DAO.BugDAO;
import beans.Bug;
import com.formdev.flatlaf.FlatLightLaf;

/**
 *
 * @author gabri
 */
public class JavaBD01 {

    public static void main(String[] args) {
       FlatLightLaf.setup();
       
       com.formdev.flatlaf.FlatLightLaf.setup();

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DashboardBugs().setVisible(true);
            }
        });
       
        // Criando um objeto Bug para testar se o banco de dados está gravando
        Bug b = new Bug();
        b.setNome("Tela branca ao clicar em salvar");
        b.setDescricao("Ao preencher o formulário e clicar em salvar, a requisição trava e a tela fica totalmente em branco sem retornar erro no console.\"");
        b.setModulo("Front-end");
        b.setPrioridade("Alta");
       
        // Instanciando o DAO e executando a inserção
        BugDAO bDAO = new BugDAO();
        bDAO.inserir(b);
        
        System.out.println("Bug inserido com sucesso para teste!");
    }
}