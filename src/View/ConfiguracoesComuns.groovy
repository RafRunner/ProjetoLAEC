package View


import Controllers.PossuidorListaAtualizavel
import Dominio.Classe
import Dominio.ConfiguracaoGeral
import Dominio.Enums.Ordens
import Dominio.Exceptions.EntradaInvalidaException
import Dominio.Instrucao
import Services.ClasseService
import Services.ConfiguracaoGeralService
import Services.InstrucaoService
import groovy.transform.CompileStatic

import javax.swing.BorderFactory
import javax.swing.BoxLayout
import javax.swing.DefaultListModel
import javax.swing.JButton
import javax.swing.JComboBox
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JList
import javax.swing.JOptionPane
import javax.swing.JPanel
import javax.swing.JScrollPane
import javax.swing.JTextField
import javax.swing.ListSelectionModel
import java.awt.Dimension
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.Toolkit
import java.awt.event.ActionEvent
import java.awt.event.ActionListener

class ConfiguracoesComuns extends JFrame implements PossuidorListaAtualizavel, ActionListener {

    private List<Classe> classesExistentes

    private DefaultListModel<String> listaClasses
    private JList<String> jListClasses
    private JTextField fieldNome
    private JComboBox<String> ordem
    private JComboBox<String> instrucaoInicial
    private JComboBox<String> instrucaoFinal

    private List<Instrucao> instrucoes

    private JButton botaoCriarNovasClasses
    private JButton botaoCriarNovasInstrucoes
    private JButton botaoConfigurarFases
    private JButton botaoCancelar

    private PossuidorListaAtualizavel possuidorListaAtualizavel

    private ClasseService classeService = ClasseService.instancia
    private ConfiguracaoGeralService configuracaoGeralService = ConfiguracaoGeralService.instancia
    private InstrucaoService instrucaoService = InstrucaoService.instancia

    private ConfiguracaoGeral configuracaoGeral = new ConfiguracaoGeral()

    private static Dimension tamanhoTela = Toolkit.defaultToolkit.screenSize
    private static String StringTamanhoMax = 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa'

    ConfiguracoesComuns(PossuidorListaAtualizavel possuidorListaAtualizavel) {
        classesExistentes = classeService.obtemTodasAsClasses()
        this.possuidorListaAtualizavel = possuidorListaAtualizavel

        instrucoes = instrucaoService.obtenhaTodasAsInstrucoes()

        JPanel panel = new JPanel()

        panel.setLayout(new GridBagLayout())
        panel.setBorder(BorderFactory.createTitledBorder('Configurações Comuns'))

        GridBagConstraints gb = ViewUtils.getGb()
        gb.fill = GridBagConstraints.HORIZONTAL

        List<String> classesExistentes = this.classesExistentes.collect { it.montaNomeArquivo() }

        JLabel labelNome = new JLabel('Título configuração: ')
        JLabel labelClasses = new JLabel('Classes:')
        JLabel labelOrdem = new JLabel('Ordem:')
        JLabel labelInstrucaoInicial = new JLabel('Instrução Inicial')
        JLabel labelInstrucaoFinal = new JLabel('Instrução Final')

        fieldNome = new JTextField()
        this.listaClasses = new DefaultListModel<>()
        classesExistentes.each { this.listaClasses.addElement(it) }
        jListClasses = new JList(this.listaClasses)
        jListClasses.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION)
        JScrollPane scrollClasses = new JScrollPane()
        scrollClasses.setViewportView(jListClasses)

        ordem = new JComboBox<>(Ordens.values().collect { it.nomeOrdem } as String[])
        ordem.setPrototypeDisplayValue(StringTamanhoMax)
        instrucaoInicial = new JComboBox<>(instrucoes.texto as String[])
        instrucaoInicial.setPrototypeDisplayValue(StringTamanhoMax)
        instrucaoInicial.setSelectedIndex(-1)
        instrucaoFinal = new JComboBox<>(instrucoes.texto as String[])
        instrucaoFinal.setPrototypeDisplayValue(StringTamanhoMax)
        instrucaoFinal.setSelectedIndex(-1)

        botaoCriarNovasClasses = new JButton('Criar nova classe')
        botaoCriarNovasClasses.addActionListener(this)
        botaoCriarNovasInstrucoes = new JButton('Criar nova Instrucao')
        botaoCriarNovasInstrucoes.addActionListener(this)
        botaoConfigurarFases = new JButton('Configurar Fases')
        botaoConfigurarFases.addActionListener(this)
        botaoCancelar = new JButton('Cancelar')
        botaoCancelar.addActionListener(this)

        JPanel painelBotoes = new JPanel()
        painelBotoes.setLayout(new BoxLayout(painelBotoes, BoxLayout.X_AXIS))

        painelBotoes.add(botaoCancelar)
        painelBotoes.add(botaoCriarNovasClasses)
        painelBotoes.add(botaoCriarNovasInstrucoes)
        painelBotoes.add(botaoConfigurarFases)

        panel.add(labelNome, gb); ++gb.gridy
        panel.add(fieldNome, gb); ++gb.gridy
        panel.add(labelClasses, gb); ++gb.gridy
        panel.add(scrollClasses, gb); ++gb.gridy
        panel.add(labelOrdem, gb); ++gb.gridy
        panel.add(ordem, gb); ++gb.gridy
        panel.add(labelInstrucaoInicial, gb); ++gb.gridy
        panel.add(instrucaoInicial, gb); ++gb.gridy
        panel.add(labelInstrucaoFinal, gb); ++gb.gridy
        panel.add(instrucaoFinal, gb); ++gb.gridy
        panel.add(scrollClasses, gb); ++gb.gridy
        gb.fill = GridBagConstraints.NONE
        gb.weighty = 10
        panel.add(painelBotoes, gb)

        add(panel)
        ViewUtils.configuraJFrame(this, tamanhoTela, 'Criando Configuração')
    }

    private void criarInstrucao() {
        String texto = JOptionPane.showInputDialog(null, 'Texto da instrução:')
        Instrucao instrucao = new Instrucao(texto)
        instrucaoService.salvarInstrucoes([instrucao])
        instrucaoFinal.addItem(instrucao.texto)
        instrucaoInicial.addItem(instrucao.texto)
        repaint()
    }

    void modificaConfiguracao(ConfiguracaoGeral configuracaoGeral) {
        String titulo = fieldNome.getText()
        List<String> classes = jListClasses.getSelectedValuesList()

        if (configuracaoGeralService.existeConfiguracao(titulo)) {
            throw new EntradaInvalidaException('Já existe configuração com esse título')
        }

        configuracaoGeral.tituloConfiguracao = titulo
        configuracaoGeral.classes = classesExistentes.findAll { it.montaNomeArquivo() in classes }
        if (instrucaoInicial.getSelectedItem()) {
            configuracaoGeral.instrucaoInicial = new Instrucao(instrucaoInicial.getSelectedItem().toString())
        }
        if  (instrucaoFinal.getSelectedItem()) {
            configuracaoGeral.instrucaoFinal = new Instrucao(instrucaoFinal.getSelectedItem().toString())
        }
        configuracaoGeral.ordem = Ordens.values().find { it.nomeOrdem == ordem.getSelectedItem().toString() }
    }

    @Override
    void atualizar() {
        List<Classe> classesAnteriores = classesExistentes
        classesExistentes = classeService.obtemTodasAsClasses()
        classesExistentes.findAll { !(it.montaNomeArquivo() in classesAnteriores.collect { it.montaNomeArquivo() }) }?.each {
            listaClasses.addElement(it.montaNomeArquivo())
        }
        revalidate()
        repaint()
    }

    @Override
    void actionPerformed(ActionEvent e) {
        Object origem = e.getSource()

        if (origem == botaoConfigurarFases) {
            modificaConfiguracao(configuracaoGeral)
            
            new ConfiguracaoFases(configuracaoGeral, possuidorListaAtualizavel)
            dispose()
        }
        else if (origem == botaoCancelar) {
            dispose()
        }
        else if (origem == botaoCriarNovasInstrucoes) {
            criarInstrucao()
        }
        else {
            new CriarClasse(this)
        }
    }
}
