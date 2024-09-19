import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Date;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    static public Conta carregarArquivo(String agencia, int num) throws IOException, ClassNotFoundException{
        String arquivo = agencia + "-" + num + ".ser";
        FileInputStream arqEntrada = null;

        try {
            arqEntrada = new FileInputStream(arquivo);
        } catch (FileNotFoundException e) {
            System.out.println("<< Conta nao encontrada! [Agencia: " + agencia + " || Conta:" + num + "] >>");
        }

        ObjectInputStream arqSer = new ObjectInputStream(arqEntrada);
        Conta conta = (Conta)arqSer.readObject();

        arqSer.close();
        if (arqEntrada  != null) arqEntrada.close();

        return conta;
    }

    public static void main(String[] args) {
        Date data = new Date();

        PessoaFisica pF1 = new PessoaFisica("Julia", "rua das engracadinhas", data, "155.495.816-47", 16, 'F');
        PessoaFisica pF2 = new PessoaFisica("Mariana", "rua das princesas", data, "107.010.076-56", 16, 'F');
        PessoaFisica pF3 = new PessoaFisica("Juliaa", "rs", data, "155.495.816-47", 16, 'F');
        PessoaJuridica pJ = new PessoaJuridica("CHURRASIC PARK", "rua dos dinossauros", data, "24 405 890/0001-86", "Alimenticio", 5);

        Conta contaPF1 = new ContaCorrente("1010-0", pF1, 1000000, 10, 100000, 0);
        Conta contaPF2 = new ContaCorrente("1110-0", pF2, 1000000, 20, 500000, 1);
        Conta contaPF3 = new ContaUniversitaria("1010-1", pF3, 200, 10, 200, 3);
        Conta contaPJ = new ContaCorrente("1234-9", pJ, 1000000, 5000, 500000, 2);

        System.out.println("<< BANCO JMSZ >>\n<< IMPRIMINDO INFORMACOES DOS CLIENTES >>");
        System.out.println(contaPF1.toString());
        System.out.println(contaPF2.toString());
        System.out.println(contaPF3.toString());
        System.out.println(contaPJ.toString());

        System.out.println("\n<< REALIZANDO OPERACOES >>");
        try {
            contaPF1.depositar(5000);
            //contaPF1.depositar(-10);
            contaPF2.depositar(2000);
            contaPJ.depositar(10000);
            contaPJ.depositar(2000);

            contaPF1.sacar(200);
            contaPF2.sacar(1500);
            contaPF2.sacar(200);
            contaPJ.sacar(130);
            contaPJ.sacar(200);
            //contaPJ.sacar(-300);
            contaPJ.sacar(1640);

            contaPF1.extratoTaxas();
            contaPF2.extratoTaxas();
            contaPJ.extratoTaxas();
        } catch (Exception i){
            i.printStackTrace();
        }

        System.out.println("\n<< IMPRIMINDO EXTRATO... >>");
        contaPF1.extrato(1);
        contaPF2.extrato(1);
        contaPJ.extrato(1);

        System.out.println("\nImprimindo a media...");
        //int media = Operacao.totalOperacoes/Conta.totalContas;

        //System.out.println("Media: " +media);

        //verificacao pelo metodo equals
        System.out.println("\n<< VERIFICANDO SE HA CONTAS IGUAIS (cpf ou cnpj) >>");
        System.out.println("\nConta " + pF1.getNome() + " equals (igual) Conta " + pF2.getNome() + "? ");
        System.out.println(pF1.autenticar(pF2.getCpf()));
        System.out.println("\nConta " + pF1.getNome() + " equals (igual) Conta " + pF3.getNome() + "? ");
        System.out.println(pF1.autenticar(pF3.getCpf()));

        try{
            contaPF2.salvarArquivo();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        Conta loadedConta = null;

        try{
            loadedConta = carregarArquivo(contaPF2.getAgencia(), contaPF2.getNum());
        }catch(IOException | ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
    }
}