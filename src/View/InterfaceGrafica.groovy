package View

import java.awt.*
import java.awt.event.*
import javax.swing.*

class InterfaceGrafica extends JFrame{

    private JTextField campoTexto
    private JTextArea areaTexto
    private JLabel rotulo
    private JRadioButton botaoGrupo1, botaoGrupo2

    private static Dimension tamanhoTela = Toolkit.defaultToolkit.screenSize

    InterfaceGrafica(){

        // *** Introdução ***

        JPanel painelPai = new JPanel()
        painelPai.setLayout(new BoxLayout(painelPai, BoxLayout.X_AXIS))

        setDefaultCloseOperation(EXIT_ON_CLOSE)
        setTitle("Criando Configuração")
        setSize(new Dimension((int) (tamanhoTela.width / 2),(int) (tamanhoTela.height / 1.5)))
        setLocation((int) (tamanhoTela.width/2 - getSize().width/2), (int) (tamanhoTela.height/2 - getSize().height/2))

        JPanel painel1 = new JPanel()
        painel1.setLayout(new BoxLayout(painel1, BoxLayout.Y_AXIS))
        JPanel painel2 = new JPanel()
        painel2.setLayout(new BoxLayout(painel2, BoxLayout.Y_AXIS))
        JPanel painel3 = new JPanel()
        painel3.setLayout(new BoxLayout(painel3, BoxLayout.Y_AXIS))

        rotulo = new JLabel("LAEC Psi")
        painel1.add(rotulo)

        rotulo = new JLabel("Nome do participante: ")
        painel2.add(rotulo)
        campoTexto = new JTextField(32)
        campoTexto.setEditable(true)
        painel2.add(campoTexto)

        rotulo = new JLabel("Idade: ")
        painel2.add(rotulo)
        campoTexto = new JTextField(3)
        campoTexto.setEditable(true)
        painel2.add(campoTexto)

        rotulo = new JLabel("Sexo: ")
        painel2.add(rotulo)
        campoTexto = new JTextField(9)
        campoTexto.setEditable(true)
        painel2.add(campoTexto)

        rotulo = new JLabel("Nome do experimentador: ")
        painel2.add(rotulo)
        campoTexto = new JTextField(32)
        campoTexto.setEditable(true)
        painel2.add(campoTexto)

        rotulo = new JLabel("Grupo:")
        painel3.add(rotulo)
        botaoGrupo1 = new JRadioButton("Grupo 1")
        painel3.add(botaoGrupo1)
        botaoGrupo2 = new JRadioButton("Grupo 2")
        painel3.add(botaoGrupo2)

        boolean grupo1 = false, grupo2 = false

        botaoGrupo1.addActionListener(new ActionListener() {
            @Override
            void actionPerformed(ActionEvent e) {

                grupo1 = true
                if(grupo2)
                    !grupo2
                botaoGrupo2.setSelected(false)
            }
        })

        botaoGrupo2.addActionListener(new ActionListener() {
            @Override
            void actionPerformed(ActionEvent e) {

                grupo2 = true
                if(grupo1)
                    !grupo1
                botaoGrupo1.setSelected(false)
            }
        })

        painelPai.add(painel1)
        painelPai.add(painel2)
        painelPai.add(painel3)

        // *** Linha de Base ***

        JPanel painel4 = new JPanel()
        painel4.setLayout(new BoxLayout(painel4, BoxLayout.Y_AXIS))

        rotulo = new JLabel("Instruções: ")
        painel4.add(rotulo)
        areaTexto = new JTextArea()
        areaTexto.setEditable(true)
        painel4.add(areaTexto)

        rotulo = new JLabel("Tempo de Apresentação(em segundos): ") // 0 a 120 segundos
        painel4.add(rotulo)
        campoTexto = new JTextField(3)
        campoTexto.setEditable(true)
        painel4.add(campoTexto)

        painelPai.add(painel4)

        // *** Condição 1 ***

        JPanel painel5 = new JPanel()
        painel5.setLayout(new BoxLayout(painel5, BoxLayout.Y_AXIS))

        rotulo = new JLabel("Instruções: ")
        painel5.add(rotulo)
        areaTexto = new JTextArea()
        areaTexto.setEditable(true)
        painel5.add(areaTexto)

        rotulo = new JLabel("Número de Repetições: ") // 1 a 10 repetições
        painel5.add(rotulo)
        campoTexto = new JTextField(2)
        campoTexto.setEditable(true)
        painel5.add(campoTexto)

        painelPai.add(painel5)

        // *** Condição 2 / Treino ***

        JPanel painel6 = new JPanel()
        painel6.setLayout(new BoxLayout(painel6, BoxLayout.Y_AXIS))

        rotulo = new JLabel("Instruções(Treino A): ")
        painel6.add(rotulo)
        areaTexto = new JTextArea()
        areaTexto.setEditable(true)
        painel6.add(areaTexto)

        rotulo = new JLabel("Instruções(Treino B): ")
        painel6.add(rotulo)
        areaTexto = new JTextArea()
        areaTexto.setEditable(true)
        painel6.add(areaTexto)

        rotulo = new JLabel("Esquema de Acertos: ") // 0 a 50 acertos
        painel6.add(rotulo)
        campoTexto = new JTextField(2)
        campoTexto.setEditable(true)
        painel6.add(campoTexto)

        rotulo = new JLabel("Esquema de Erros: ") // 0 a 50 erros
        painel6.add(rotulo)
        campoTexto = new JTextField(2)
        campoTexto.setEditable(true)
        painel6.add(campoTexto)

        rotulo = new JLabel("Critério de Fase: ") // Número de acertos consecutivos que o participante deverá ter para passar para a próxima tela.
        painel6.add(rotulo)
        campoTexto = new JTextField(2)
        campoTexto.setEditable(true)
        painel6.add(campoTexto)

        rotulo = new JLabel("Critério de Encerramento: ") // Número de tentativas(sem o valor de acertos consecutivos do critério de fase) que fará com que o experimento encerre.
        painel6.add(rotulo)
        campoTexto = new JTextField(2)
        campoTexto.setEditable(true)
        painel6.add(campoTexto)

        painelPai.add(painel6)

        // *** Teste 1 ***

        JPanel painel7 = new JPanel()
        painel7.setLayout(new BoxLayout(painel7, BoxLayout.Y_AXIS))

        rotulo = new JLabel("Instruções: ")
        painel7.add(rotulo)
        areaTexto = new JTextArea()
        areaTexto.setEditable(true)
        painel7.add(areaTexto)

        rotulo = new JLabel("Tempo de Apresentação(em segundos): ") // 0 a 120 segundos
        painel7.add(rotulo)
        campoTexto = new JTextField(3)
        campoTexto.setEditable(true)
        painel7.add(campoTexto)

        painelPai.add(painel7)

        //*** Teste 2 ***

        JPanel painel8 = new JPanel()
        painel8.setLayout(new BoxLayout(painel8, BoxLayout.Y_AXIS))

        rotulo = new JLabel("Instruções: ")
        painel8.add(rotulo)
        areaTexto = new JTextArea()
        areaTexto.setEditable(true)
        painel8.add(areaTexto)

        rotulo = new JLabel("Número de Repetições: ") // 1 a 10 repetições
        painel8.add(rotulo)
        campoTexto = new JTextField(2)
        campoTexto.setEditable(true)
        painel8.add(campoTexto)

        painelPai.add(painel8)
        add(painelPai)
        setVisible(true)
    }

    static void main(String[] args) {
        new CriarClasse()
    }
}