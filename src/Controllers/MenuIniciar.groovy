package Controllers

import Dominio.ConfiguracaoGeral
import Dominio.Enums.Ordens
import Dominio.Enums.Sexo
import Dominio.Exceptions.EntradaInvalidaException
import Files.Logger
import Services.ConfiguracaoGeralService
import Services.LoggerService
import View.ConfiguracoesComuns
import View.ViewUtils
import groovy.transform.CompileStatic

import javax.swing.BorderFactory
import javax.swing.BoxLayout
import javax.swing.JButton
import javax.swing.JComboBox
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JTextField
import java.awt.Color
import java.awt.Dimension
import java.awt.Font
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.Toolkit
import java.awt.event.ActionEvent
import java.awt.event.ActionListener

@CompileStatic
class MenuIniciar extends JFrame implements ActionListener, CriadorConfiguracao {

    private List<ConfiguracaoGeral> configuracoes
    private ConfiguracaoGeralService configuracaoGeralService = ConfiguracaoGeralService.instancia

    private JTextField experimentador = new JTextField()
    private JComboBox<String> grupoParticipante
    private JTextField participante = new JTextField()
    private JTextField idadeParticipante = new JTextField()
    private JComboBox<String> sexoParticipante
    private JComboBox<String> configuracaoSelecionada
    private JButton botaoCriarConfiguracao
    private JButton iniciar

    private static Dimension tamanhoTela = Toolkit.defaultToolkit.screenSize

    MenuIniciar() {
        JPanel painel = new JPanel()

        configuracoes = configuracaoGeralService.obtemTodasAsConfiguracoes()
        String[] titulosConfiguracao = configuracoes.tituloConfiguracao as String[]
        String[] sexos = Sexo.values().collect { it.extenso } as String[]
        String[] grupo = Ordens.values().collect { it.nomeGrupo } as String[]

        JLabel labelExperimentador = new JLabel('Experimentador: ')
        JLabel labelGrupoParticipante = new JLabel('Grupo participante: ')
        JLabel labelParticipante = new JLabel('Participante: ')
        JLabel labelIdadeParticipante = new JLabel('Idade participante: ')
        JLabel labeSexoParticipante = new JLabel('Sexo participante: ')
        JLabel labelConfiguracao = new JLabel('Configuração a ser usada: ')

        sexoParticipante = new JComboBox<>(sexos)
        configuracaoSelecionada = new JComboBox<>(titulosConfiguracao)
        grupoParticipante = new JComboBox<>(grupo)
        botaoCriarConfiguracao = new JButton('Criar nova Configuração')
        botaoCriarConfiguracao.addActionListener(this)
        iniciar = new JButton('Iniciar Experimento')
        iniciar.addActionListener(this)

        painel.setLayout(new GridBagLayout())

        GridBagConstraints gb = ViewUtils.getGb()
        gb.anchor = GridBagConstraints.LINE_END

        painel.add(labelExperimentador, gb); ++gb.gridy
        painel.add(labelGrupoParticipante, gb); ++gb.gridy
        painel.add(labelParticipante, gb); ++gb.gridy
        painel.add(labelIdadeParticipante, gb); ++gb.gridy
        painel.add(labeSexoParticipante, gb); ++gb.gridy
        painel.add(labelConfiguracao, gb); ++gb.gridy
        painel.add(botaoCriarConfiguracao, gb); ++gb.gridy
        
        ++gb.gridx; gb.gridy = 0
        gb.weightx = 10
        gb.anchor = GridBagConstraints.LINE_START
        gb.fill = GridBagConstraints.HORIZONTAL

        painel.add(experimentador, gb); ++gb.gridy
        painel.add(grupoParticipante, gb); ++gb.gridy
        painel.add(participante, gb); ++gb.gridy
        painel.add(idadeParticipante, gb); ++gb.gridy
        painel.add(sexoParticipante, gb); ++gb.gridy
        painel.add(configuracaoSelecionada, gb); ++gb.gridy
        gb.anchor = GridBagConstraints.CENTER
        gb.fill = GridBagConstraints.NONE
        painel.add(iniciar, gb); ++gb.gridy

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS))
        add(criarPainelCreditos())
        add(painel)

        ViewUtils.configuraJFrame(this, tamanhoTela, 'Words and Context')
    }

    private JPanel criarPainelCreditos() {
        JPanel painel = new JPanel()
        painel.setLayout(new GridBagLayout())
        painel.setBorder(BorderFactory.createLineBorder(Color.BLACK))

        List<JLabel> texto = ['Software Words and Context',
                              'Programado por: Rafael Nunes Santana e Arthur Cintra',
                              'Experimento desenvolvido por: Luisa Fernandes'].collect { new JLabel(it) }

        GridBagConstraints gb = ViewUtils.getGb()
        gb.anchor = GridBagConstraints.CENTER

        texto.each {
            it.font = new Font('dialog', 0, 30)
            painel.add(it, gb); ++gb.gridy
        }
        JLabel espaco = new JLabel('')
        gb.weighty = 5
        painel.add(espaco, gb)
        return painel
    }

    @Override
    void actionPerformed(ActionEvent actionEvent) {
        Object origem = actionEvent.getSource()

        if (origem == iniciar) {
            iniciar()
        } else {
            new ConfiguracoesComuns()
        }
    }

    private void iniciar() {
        ConfiguracaoGeral configuracaoGeral = configuracoes.find { it.tituloConfiguracao == configuracaoSelecionada.getSelectedItem().toString() }
        String nomeExperimentador = experimentador.getText()
        Ordens ordem = Ordens.values().find { it.nomeGrupo == grupoParticipante.getSelectedItem().toString() }
        String nomeParticipante = participante.getText()
        String sexoParticipante = sexoParticipante.getSelectedItem()
        int idadeParticipante
        try {
            idadeParticipante = Integer.parseInt(this.idadeParticipante.getText().toString().trim())
        } catch (Exception ignored) {
            throw new EntradaInvalidaException('Idade deve ser um número!')
        }

        configuracaoGeral.ordem = ordem
        Logger logger = new Logger(nomeExperimentador, nomeParticipante, sexoParticipante, idadeParticipante, configuracaoGeral)
        LoggerService.instancia.criarArquivoResultado(logger)

        JanelaPrincipalController janelaPrincipalController = new JanelaPrincipalController(configuracaoGeral, logger, new JPanel())
        janelaPrincipalController.passarParaProximaFase()
    }

    @Override
    void modificaConfiguracao(ConfiguracaoGeral configuracaoGeral) {

    }

    @Override
    void atualizar() {

    }
}
