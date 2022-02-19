package br.com.henriqueandrew.Atividade5;

// @author Henrique Andrew da Silva
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ProcessaPiada extends Thread {

    protected DatagramSocket conexao = null;
    protected BufferedReader buf = null;
    protected boolean solicitarPiada = true;

    public ProcessaPiada() throws IOException {
        this("ProcessaPiada");
    }

    public ProcessaPiada(String nome) throws IOException {
        super(nome);
        conexao = new DatagramSocket(30000);

        try {
            buf = new BufferedReader(new FileReader("C:\\piadas.txt"));
        } catch (Exception e) {
            System.out.println("Arquivo não encontrado!");
        }
    }

    public void run() {
        while (solicitarPiada) {
            try {
                byte[] dados = new byte[256];
                DatagramPacket pacote = new DatagramPacket(dados, dados.length);
                conexao.receive(pacote);

                String piada = null;
                if (buf == null) {
                    piada = "Não foi encontrado o arquivo de piadas!";
                } else {
                    piada = novaPiada();
                }
                dados = piada.getBytes();
                
                InetAddress endereco = pacote.getAddress();
                int porta = pacote.getPort();
                pacote = new DatagramPacket(dados, dados.length, endereco, porta);
                conexao.send(pacote);
            } catch (IOException e) {
                e.printStackTrace();
                solicitarPiada = false;
            }

        }
    }
    
    protected String novaPiada(){
        String retornaPiada = null;
        try{
            if((retornaPiada = buf.readLine()) == null){
                buf.close();
                solicitarPiada = false;
                retornaPiada = "Não há mais piada!!!";                
            }            
        }
        catch(IOException e){
            retornaPiada = "Ocorreu erro no Servidor";
        }
        return retornaPiada;
    }

}
