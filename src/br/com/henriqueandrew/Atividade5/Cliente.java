package br.com.henriqueandrew.Atividade5;

// @author Henrique Andrew da Silva
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Cliente {

    private JFrame janela;
    int larg = 500, alt = 400;

    private JLabel rot1;
    private TextArea piada;
    private JButton solic;
    private JButton sair;

    public Cliente() {

        inicializaComponentes();
        addListeners();

    }

    public void inicializaComponentes() {

        janela = new JFrame();
        janela.setSize(larg, alt);
        janela.setTitle("Sistema de Piadas");
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        rot1 = new JLabel();
        rot1.setText("Retorno de Piadas");
        janela.add(rot1);

        piada = new TextArea();
        piada.setText(null);
        piada.setEditable(false);
        janela.add(piada);

        solic = new JButton();
        solic.setText("Solicitar Piada");
        solic.setForeground(Color.BLACK);
        janela.add(solic);

        sair = new JButton();
        sair.setText("Sair");
        sair.setForeground(Color.BLACK);
        janela.add(sair);

        janela.setLayout(new FlowLayout());
        janela.setVisible(true);

    }

    public void addListeners() {

        solic.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Solicitando piada...");
                solicitacaoPiadaActionPerformed(e);
            }
        });

        sair.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Sistema encerrado!");
                sairSistemaActionPerformed(e);
            }
        });
    }

    public void solicitacaoPiadaActionPerformed(ActionEvent evt) {
        solicitarpiada();
    }

    public void sairSistemaActionPerformed(ActionEvent evt) {
        System.exit(0);
    }

    public void solicitarpiada() {

        try {

            //Solicitando a pidada
            DatagramSocket clienteConexao = new DatagramSocket();
            byte[] dados = new byte[256];

            String solicitacao = "Piada solicitada!";
            //piada.setText(solicitacao);
            dados = solicitacao.getBytes();

            InetAddress endereco = InetAddress.getByName("localhost");

            DatagramPacket pacote = new DatagramPacket(dados, dados.length, endereco, 30000);
            clienteConexao.send(pacote);

            //retorno da piada
            dados = new byte[256];

            pacote = new DatagramPacket(dados, dados.length);
            clienteConexao.receive(pacote);

            String retPiada = new String(pacote.getData());
            piada.setText(retPiada + "\n");
            if(retPiada.contains("Não há mais piada!!!")){
                solic.setEnabled(false);
            }

        } catch (Exception e) {

        }

    }

    public static void main(String args[]) {
        new Cliente();
    }

}
