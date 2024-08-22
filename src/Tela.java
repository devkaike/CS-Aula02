import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.Period;

public class Tela {
    public JPanel paiel;
    public JTextField Nome;
    public JComboBox<String> CBestadoCivil;
    public JCheckBox masculinoCheckBox;
    public JCheckBox femininoCheckBox;
    public JTextField dataNasc;
    public JTextField profissao;
    public JTextField cpf;
    public JButton cadastrarButton;

    String cpfCalc = "";

    public Tela(){
        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!(cpf.getText().isEmpty())){
                    cpfCalc = cpf.getText();
                    // Remove caracteres não numéricos
                    cpfCalc = cpfCalc.replaceAll("[^0-9]", "");

                    // Verifica se o CPF tem 11 dígitos
                    if (cpfCalc.length() != 11) {
                        System.out.println("CPF " + cpfCalc + " é inválido (não possui 11 dígitos).");
                        JOptionPane.showMessageDialog(null, "CPF invalido, digite novamente!");
                        return;
                    }

                    // Verifica se todos os dígitos são iguais (o que seria inválido)
                    if (cpfCalc.matches("(\\d)\\1{10}")) {
                        System.out.println("CPF " + cpfCalc + " é inválido (todos os dígitos são iguais).");
                        JOptionPane.showMessageDialog(null, "CPF invalido, digite novamente!");
                        return;
                    }

                    // Calcula o primeiro dígito verificador
                    int sum = 0;
                    for (int i = 0; i < 9; i++) {
                        sum += (cpfCalc.charAt(i) - '0') * (10 - i);
                    }
                    int firstDigit = 11 - (sum % 11);
                    if (firstDigit >= 10) {
                        firstDigit = 0;
                    }

                    // Verifica se o primeiro dígito verificador está correto
                    if (firstDigit != (cpfCalc.charAt(9) - '0')) {
                        System.out.println("CPF " + cpfCalc + " é inválido (primeiro dígito verificador incorreto).");
                        JOptionPane.showMessageDialog(null, "CPF invalido, digite novamente!");
                        return;
                    }

                    // Calcula o segundo dígito verificador
                    sum = 0;
                    for (int i = 0; i < 10; i++) {
                        sum += (cpfCalc.charAt(i) - '0') * (11 - i);
                    }
                    int secondDigit = 11 - (sum % 11);
                    if (secondDigit >= 10) {
                        secondDigit = 0;
                    }

                    // Verifica se o segundo dígito verificador está correto
                    if (secondDigit != (cpfCalc.charAt(10) - '0')) {
                        System.out.println("CPF " + cpfCalc + " é inválido (segundo dígito verificador incorreto).");
                        JOptionPane.showMessageDialog(null, "CPF invalido, digite novamente!");
                    } else {
                        System.out.println("CPF " + cpfCalc + " é válido.");
                    }
                }else {
                    JOptionPane.showMessageDialog(null, "Campo CPF está em branco, digite novamente!");
                }
                if(Nome.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Nome em branco, digite novamente!");
                }

                if(profissao.getText().isEmpty()){
                    profissao.setText("Desepregado(a)");
                }
                mostrarDados();
            }
        });

        CBestadoCivil.addItem("Solteiro(a)");
        CBestadoCivil.addItem("Casado(a)");
        CBestadoCivil.addItem("Divorciado(a)");
        CBestadoCivil.addItem("Viúvo(a)");
    }

    public void mostrarDados(){
        JOptionPane.showMessageDialog(null, "\t\t"+Nome.getText()+"\n"+
                calcularIdade(dataNasc.getText())+
                statusProfissao(profissao)+
                sexo(masculinoCheckBox, femininoCheckBox)+
                "CPF: "+cpfCalc+"\n"+
                obterEstadoCivil(CBestadoCivil.getSelectedItem().toString()));
    }

    public String calcularIdade(String data){
        //int idade = 0;

        LocalDate d = LocalDate.now();
        String dataAtual = d.toString().trim().replace("-", "");

        int ano = Integer.parseInt(data.substring(4, 8));
        int mes = Integer.parseInt(data.substring(2,4));
        int dia = Integer.parseInt(data.substring(0,2));

        int anoAtual = Integer.parseInt(dataAtual.substring(0, 4));
        int mesAtual = Integer.parseInt(dataAtual.substring(4, 6));
        int diaAtual = Integer.parseInt(dataAtual.substring(6, 8));

        if (mesAtual == mes && diaAtual == dia){
            return "Seu aniversario é hj feliz "+ (anoAtual-ano) +" anos\n";
        }

        LocalDate dataAtual2 = LocalDate.now();
        // Cria a data de nascimento do usuário
        LocalDate dataNascimento = LocalDate.of(ano, mes, dia);

        // Calcula a idade da pessoa
        int idade = Period.between(dataNascimento, dataAtual2).getYears();

        // Cria a data do próximo aniversário
        LocalDate proximoAniversario = dataNascimento.withYear(dataAtual2.getYear());

        // Se o aniversário já passou neste ano, ajusta para o próximo ano
        if (proximoAniversario.isBefore(dataAtual2) || proximoAniversario.isEqual(dataAtual2)) {
            proximoAniversario = proximoAniversario.plusYears(1);
        }

        // Calcula a diferença em meses e dias até o próximo aniversário
        Period periodo = Period.between(dataAtual2, proximoAniversario);
        int mesesRestantes = periodo.getMonths();
        int diasRestantes = periodo.getDays();

        return "Você tem "+idade+" anos e faltam "+ mesesRestantes+ " meses e "+diasRestantes+" dias para o seu próximo aniversário.\n";
    }

    public String statusProfissao(JTextField profissao){
        if(profissao.getText().equals("Engenheiro") || profissao.getText().equals("Analista de Sistemas")){
            return "Profissão: "+ profissao.getText() +"e tem vagas disponíveis na área\n";
        }
        return "Profissão: "+ profissao.getText() +"\n";
    }

    public String sexo(JCheckBox masculino, JCheckBox feminino){
        if(masculino.isSelected()){
            return "Sexo: Masculino\n";
        }
        return "Sexo: Feminino\n";
    }

    public String obterEstadoCivil(String status){
        return "Estado Civil: "+ status;
    }
}
