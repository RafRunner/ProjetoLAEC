package View


import Controllers.CriadorConfiguracao
import Dominio.Classe
import Dominio.ConfiguracaoGeral
import Dominio.Exceptions.EntradaInvalidaException
import Services.ClasseService
import Services.ConfiguracaoGeralService

import javax.swing.BorderFactory
import javax.swing.BoxLayout
import javax.swing.DefaultListModel
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JList
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

class ConfiguracoesComuns extends JFrame implements CriadorConfiguracao, ActionListener {

    List<Classe> classesExistentes

    DefaultListModel<String> listaClasses
    JList<String> jListClasses
    JTextField fieldNome
    JButton botaoCriarNovasClasses
    JButton botaoConfigurarFases

    private ClasseService classeService = ClasseService.instancia
    private ConfiguracaoGeralService configuracaoGeralService = ConfiguracaoGeralService.instancia

    private static Dimension tamanhoTela = Toolkit.defaultToolkit.screenSize

    ConfiguracoesComuns() {
        this.classesExistentes = classeService.obtemTodasAsClasses()

        JPanel panel = new JPanel()

        panel.setLayout(new GridBagLayout())
        panel.setBorder(BorderFactory.createTitledBorder('Configurações Comuns'))

        GridBagConstraints gb = ViewUtils.getGb()
        gb.fill = GridBagConstraints.HORIZONTAL

        List<String> classesExistentes = this.classesExistentes.collect { it.montaNomeArquivo() }

        JLabel labelNome = new JLabel('Título configuração: ')
        JLabel labelClasses = new JLabel('Classes:')

        fieldNome = new JTextField()
        this.listaClasses = new DefaultListModel<>()
        this.listaClasses.addAll(classesExistentes)
        jListClasses = new JList(this.listaClasses)
        jListClasses.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION)
        JScrollPane scrollClasses = new JScrollPane()
        scrollClasses.setViewportView(jListClasses)

        botaoCriarNovasClasses = new JButton('Criar nova classe')
        botaoCriarNovasClasses.addActionListener(this)
        botaoConfigurarFases = new JButton('Configurar Fases')
        botaoConfigurarFases.addActionListener(this)

        JPanel painelBotoes = new JPanel()
        painelBotoes.setLayout(new BoxLayout(painelBotoes, BoxLayout.X_AXIS))

        painelBotoes.add(botaoCriarNovasClasses)
        painelBotoes.add(botaoConfigurarFases)

        panel.add(labelNome, gb); ++gb.gridy
        panel.add(fieldNome, gb); ++gb.gridy
        panel.add(labelClasses, gb); ++gb.gridy
        panel.add(scrollClasses, gb); ++gb.gridy
        gb.fill = GridBagConstraints.NONE
        gb.weighty = 10
        panel.add(painelBotoes, gb)

        add(panel)
        ViewUtils.configuraJFrame(this, tamanhoTela, 'Criando Configuração')
    }

    @Override
    void modificaConfiguracao(ConfiguracaoGeral configuracaoGeral) {
        String nome = fieldNome.getText()
        List<String> classes = jListClasses.getSelectedValuesList()

        if (!nome || !classes) {
            throw new EntradaInvalidaException('A configuração deve um título e pelo menos Uma classe!')
        }
        else if (configuracaoGeralService.existeCOnfiguracao(nome)) {
            throw new EntradaInvalidaException('Já existe configuração com esse título')
        }

        configuracaoGeral.tituloConfiguracao = fieldNome.getText()
        configuracaoGeral.classes = classesExistentes.findAll { it.montaNomeArquivo() in jListClasses.getSelectedValuesList() }
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
            ConfiguracaoGeral configuracaoGeral = new ConfiguracaoGeral()
            modificaConfiguracao(configuracaoGeral)
        } else {
            new CriarClasse(this)
        }
    }
}
