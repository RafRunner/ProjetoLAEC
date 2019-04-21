package View

import Controllers.ControllerCriarConfiguracao
import Controllers.CriadorConfiguracao
import Dominio.Classe
import Dominio.ConfiguracaoGeral
import Services.ClasseService

import javax.swing.BorderFactory
import javax.swing.BoxLayout
import javax.swing.DefaultListModel
import javax.swing.JButton
import javax.swing.JLabel
import javax.swing.JList
import javax.swing.JPanel
import javax.swing.JScrollPane
import javax.swing.JTextField
import javax.swing.ListSelectionModel
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.event.ActionEvent
import java.awt.event.ActionListener

class ConfiguracoesComuns extends JPanel implements CriadorConfiguracao, ActionListener {

    List<Classe> classesExistentes

    DefaultListModel<String> listaClasses
    JTextField fieldNome
    JButton botaoCriarNovasClasses
    JButton botaoConfigurarFases

    ClasseService classeService = ClasseService.instancia

    ConfiguracoesComuns() {
        this.classesExistentes = classeService.obtemTodasAsClasses()

        setLayout(new GridBagLayout())
        setBorder(BorderFactory.createTitledBorder('Configurações Comuns'))

        GridBagConstraints gb = ViewUtils.getGb()
        gb.fill = GridBagConstraints.HORIZONTAL

        List<String> classesExistentes = this.classesExistentes.collect { it.montaNomeArquivo() }

        JLabel labelNome = new JLabel('Nome configuração: ')
        JLabel labelClasses = new JLabel('Classes:')

        fieldNome = new JTextField()
        this.listaClasses = new DefaultListModel<>()
        this.listaClasses.addAll(classesExistentes)
        JList listClasses = new JList(this.listaClasses)
        listClasses.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION)
        JScrollPane scrollClasses = new JScrollPane()
        scrollClasses.setViewportView(listClasses)

        botaoCriarNovasClasses = new JButton('Criar nova classe')
        botaoCriarNovasClasses.addActionListener(this)
        botaoConfigurarFases = new JButton('Configurar Fases')
        botaoConfigurarFases.addActionListener(this)

        JPanel painelBotoes = new JPanel()
        painelBotoes.setLayout(new BoxLayout(painelBotoes, BoxLayout.X_AXIS))

        painelBotoes.add(botaoCriarNovasClasses)
        painelBotoes.add(botaoConfigurarFases)

        add(labelNome, gb); ++gb.gridy
        add(fieldNome, gb); ++gb.gridy
        add(labelClasses, gb); ++gb.gridy
        add(scrollClasses, gb); ++gb.gridy
        gb.fill = GridBagConstraints.NONE
        gb.weighty = 10
        add(painelBotoes, gb)
    }

    @Override
    void modificaConfiguracao(ConfiguracaoGeral configuracaoGeral) {
        configuracaoGeral.tituloConfiguracao = fieldNome.getText()
        configuracaoGeral.classes = classesExistentes.findAll { it.montaNomeArquivo() in listClasses.getSelectedValuesList() }
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

    static void main(String[] args) {
        ControllerCriarConfiguracao configuracao = new ControllerCriarConfiguracao(new ConfiguracoesComuns())
    }
}
