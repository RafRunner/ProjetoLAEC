package View

import Controllers.PossuidorListaAtualizavel
import Dominio.Classe
import Dominio.ConfiguracaoGeral
import Dominio.Enums.ModoCondicao2
import Dominio.Exceptions.EntradaInvalidaException
import Dominio.Instrucao
import Services.InstrucaoService

import javax.swing.BorderFactory
import javax.swing.BoxLayout
import javax.swing.DefaultListModel
import javax.swing.JButton
import javax.swing.JComboBox
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JList
import javax.swing.JPanel
import javax.swing.JScrollPane
import javax.swing.JTextField
import java.awt.Dimension
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.Toolkit
import java.awt.event.ActionEvent
import java.awt.event.ActionListener

class ConfiguracaoFases extends JFrame implements ActionListener, PossuidorListaAtualizavel {

    private DefaultListModel<String> listInstrucoesCondicao1
    private JList<String> jListInstrucoesCondicao1
    private JTextField fieldRepeticoesCondicao1
    private JTextField fieldTempoCondicao1


    private JComboBox<String> instrucaoImagem
    private JComboBox<String> instrucaoPalavra
    private JComboBox<String> modoExibicao
    private JTextField fieldCondicaoParadaAcerto
    private JTextField fieldCondicaoParadaErro
    private JTextField fieldRepeticoesCondicao2
    private JTextField fieldTempoCondicao2


    private JComboBox<String> instrucaoInicialLinhaDeBase
    private DefaultListModel<String> listInstrucoesLinhaDeBase
    private JList<String> jListInstrucoesLinhaDeBase
    private JTextField fieldRepeticoesLinhaDeBase
    private JTextField fieldTempoLinhaDeBase


    private DefaultListModel<String> listInstrucoesTeste2
    private JList<String> jListInstrucoesTeste2
    private JTextField fieldRepeticoesTeste2
    private JTextField fieldTempoTeste2

    private DefaultListModel<String> instrucoesDisponiveis
    private JList<String> jListInstrucoesDisponiveis

    private JButton botaoCancelar
    private JButton botaoCriarConfiguracao
    private JButton botaoCriarInstrucao
    
    private ConfiguracaoGeral configuracaoGeral
    private PossuidorListaAtualizavel possuidorListaAtualizavel

    private List<Instrucao> instrucoesExistentes
    private List<Classe> classes

    private static Dimension tamanhoTela = Toolkit.defaultToolkit.screenSize
    private static String StringTamanhoMax = 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa'

    private InstrucaoService instrucaoService = InstrucaoService.instancia

    ConfiguracaoFases(ConfiguracaoGeral configuracaoGeral, PossuidorListaAtualizavel possuidorListaAtualizavel) {
        this.configuracaoGeral = configuracaoGeral
        this.possuidorListaAtualizavel = possuidorListaAtualizavel

        instrucoesExistentes = instrucaoService.obtenhaTodasAsInstrucoes()
        classes = configuracaoGeral.classes

        JPanel painel = new JPanel()
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS))

        JPanel painelFases = new JPanel()
        painelFases.setLayout(new BoxLayout(painelFases, BoxLayout.X_AXIS))

        JPanel painelInstrucoes = new JPanel()
        painelInstrucoes.setLayout(new BoxLayout(painelInstrucoes, BoxLayout.PAGE_AXIS))
        painelInstrucoes.setBorder(BorderFactory.createTitledBorder('Instruções:'))

        JPanel painelBotoes = new JPanel()
        painelBotoes.setLayout(new BoxLayout(painelBotoes, BoxLayout.X_AXIS))

        JPanel painelCondicao1 = criaPainelCondicao1()
        JPanel painelCondicao2 = criaPainelCondicao2()
        JPanel painelLinhaDeBase = criarPainelLinhaDeBase()
        JPanel painelTeste1 = criarPainelTeste1()
        JPanel painelTeste2 = criarPainelTeste2()

        painelFases.add(painelCondicao1)
        painelFases.add(painelCondicao2)
        painelFases.add(painelLinhaDeBase)
        painelFases.add(painelTeste1)
        painelFases.add(painelTeste2)

        instrucoesDisponiveis = new DefaultListModel<>()
        instrucoesExistentes.each { instrucoesDisponiveis.addElement(it.texto) }
        jListInstrucoesDisponiveis = new JList<>(instrucoesDisponiveis)
        JScrollPane scrollInstrucoes = new JScrollPane()
        scrollInstrucoes.setViewportView(jListInstrucoesDisponiveis)

        botaoCancelar = new JButton('Cancelar')
        botaoCancelar.addActionListener(this)
        botaoCriarConfiguracao = new JButton('Criar Configuracao')
        botaoCriarConfiguracao.addActionListener(this)
        botaoCriarInstrucao = new JButton('Criar Instrucao')
        botaoCriarInstrucao.addActionListener(this)

        painelInstrucoes.add(scrollInstrucoes)

        painelBotoes.add(botaoCancelar)
        painelBotoes.add(botaoCriarInstrucao)
        painelBotoes.add(botaoCriarConfiguracao)

        painel.add(painelFases)
        painel.add(painelInstrucoes)
        painel.add(painelBotoes)

        JScrollPane scroller = new JScrollPane(painel)
        add(scroller)

        tamanhoTela = new Dimension((int) (tamanhoTela.width * 1.5), (int) (tamanhoTela.height))
        ViewUtils.configuraJFrame(this, tamanhoTela, 'Configurar Fases')
        painelLinhaDeBase.setSize(painelCondicao1.width, painelLinhaDeBase.height)
        painelCondicao2.setSize(painelCondicao1.width, painelCondicao2.height)
    }

    private JPanel criaPainelCondicao1() {
        JPanel painel = new JPanel()
        painel.setBorder(BorderFactory.createTitledBorder('Condição 1:'))
        painel.setLayout(new GridBagLayout())

        GridBagConstraints gb = ViewUtils.getGb()

        JLabel labelInstrucoes = new JLabel('Intruções:')
        JLabel labelRepeticoes = new JLabel('Repeticoes:')
        JLabel labelTempo = new JLabel('Tempo Limite:')
        JLabel espaco = new JLabel('')

        JButton botao = new JButton('Adicionar Instrucao')
        botao.addActionListener(new ActionListener() {
            @Override
            void actionPerformed(ActionEvent actionEvent) {
                String instrucaoSelecionada = jListInstrucoesDisponiveis.getSelectedValue()
                listInstrucoesCondicao1.addElement(instrucaoSelecionada)
                repaint()
                revalidate()
             }
        })

        listInstrucoesCondicao1 = new DefaultListModel<>()
        jListInstrucoesCondicao1 = new JList<>(listInstrucoesCondicao1)
        JScrollPane scrollInstrucoes = new JScrollPane()
        scrollInstrucoes.setViewportView(jListInstrucoesCondicao1)

        fieldRepeticoesCondicao1 = new JTextField()
        fieldTempoCondicao1 = new JTextField()

        painel.add(labelInstrucoes, gb); ++gb.gridy
        painel.add(espaco, gb); ++gb.gridy
        painel.add(labelRepeticoes, gb); ++gb.gridy
        painel.add(labelTempo, gb); ++gb.gridy
        gb.gridy = 0; ++gb.gridx
        painel.add(scrollInstrucoes, gb); ++gb.gridy; gb.fill = GridBagConstraints.HORIZONTAL
        painel.add(botao, gb); ++gb.gridy
        painel.add(fieldRepeticoesCondicao1, gb); ++gb.gridy
        painel.add(fieldTempoCondicao1, gb)

        return painel
    }

    private JPanel criaPainelCondicao2() {
        JPanel painel = new JPanel()
        painel.setBorder(BorderFactory.createTitledBorder('Condição 2:'))
        painel.setLayout(new GridBagLayout())

        GridBagConstraints gb = ViewUtils.getGb()

        JLabel labelInstrucaoImagen = new JLabel('Instrução Imagem:')
        JLabel labelInstrucaoPalavra = new JLabel('Instrução Palavra:')
        JLabel labelModoExibicao = new JLabel('Modo Exibição:')
        JLabel labelParadaAcerto = new JLabel('Condição Parada Acerto:')
        JLabel labelParadaErro = new JLabel('Condição Parada Erro:')
        JLabel labelRepeticoes = new JLabel('Repeticoes:')
        JLabel labelTempo = new JLabel('Tempo Limite:')

        instrucaoImagem = new JComboBox<>(instrucoesExistentes.texto as String[])
        instrucaoImagem.setPrototypeDisplayValue(StringTamanhoMax)
        instrucaoPalavra = new JComboBox<>(instrucoesExistentes.texto as String[])
        instrucaoPalavra.setPrototypeDisplayValue(StringTamanhoMax)
        modoExibicao = new JComboBox<>(ModoCondicao2.values().collect { it.nomeModo } as String[])
        modoExibicao.setPrototypeDisplayValue(StringTamanhoMax)
        fieldCondicaoParadaAcerto = new JTextField()
        fieldCondicaoParadaErro = new JTextField()
        fieldRepeticoesCondicao2 = new JTextField()
        fieldTempoCondicao2 = new JTextField()

        listInstrucoesCondicao1 = new DefaultListModel<>()
        jListInstrucoesCondicao1 = new JList<>(listInstrucoesCondicao1)
        JScrollPane scrollInstrucoes = new JScrollPane()
        scrollInstrucoes.setViewportView(jListInstrucoesCondicao1)

        fieldRepeticoesCondicao1 = new JTextField()
        fieldTempoCondicao1 = new JTextField()

        painel.add(labelInstrucaoImagen, gb); ++gb.gridy
        painel.add(labelInstrucaoPalavra, gb); ++gb.gridy
        painel.add(labelModoExibicao, gb); ++gb.gridy
        painel.add(labelParadaAcerto, gb); ++gb.gridy
        painel.add(labelParadaErro, gb); ++gb.gridy
        painel.add(labelRepeticoes, gb); ++gb.gridy
        painel.add(labelTempo, gb); ++gb.gridy
        gb.gridy = 0; ++gb.gridx; gb.fill = GridBagConstraints.HORIZONTAL
        painel.add(instrucaoImagem, gb); ++gb.gridy
        painel.add(instrucaoPalavra, gb); ++gb.gridy
        painel.add(modoExibicao, gb); ++gb.gridy
        painel.add(fieldCondicaoParadaAcerto, gb); ++gb.gridy
        painel.add(fieldCondicaoParadaErro, gb); ++gb.gridy
        painel.add(fieldRepeticoesCondicao2, gb); ++gb.gridy
        painel.add(fieldTempoCondicao2, gb); ++gb.gridy

        return painel
    }

    private JPanel criarPainelLinhaDeBase() {
        JPanel painel = new JPanel()
        painel.setBorder(BorderFactory.createTitledBorder('Linha de Base:'))
        painel.setLayout(new GridBagLayout())

        GridBagConstraints gb = ViewUtils.getGb()

        JLabel labelIntrucaoInicial = new JLabel('Intrução Inicial:')
        JLabel labelInstrucoes = new JLabel('Intruções:')
        JLabel labelRepeticoes = new JLabel('Repeticoes:')
        JLabel labelTempo = new JLabel('Tempo Limite:')
        JLabel espaco = new JLabel('')

        instrucaoInicialLinhaDeBase = new JComboBox<String>(instrucoesExistentes.texto as String[])
        instrucaoInicialLinhaDeBase.setPrototypeDisplayValue(StringTamanhoMax)
        listInstrucoesLinhaDeBase = new DefaultListModel<String>()
        jListInstrucoesLinhaDeBase = new JList<>(listInstrucoesLinhaDeBase)
        JScrollPane scrollInstrucoes = new JScrollPane()
        scrollInstrucoes.setViewportView(jListInstrucoesCondicao1)

        JButton botao = new JButton('Adicionar Instrucao')
        botao.addActionListener(new ActionListener() {
            @Override
            void actionPerformed(ActionEvent actionEvent) {
                String instrucaoSelecionada = jListInstrucoesDisponiveis.getSelectedValue()
                listInstrucoesLinhaDeBase.addElement(instrucaoSelecionada)
                revalidate()
                repaint()
            }
        })

        painel.add(labelIntrucaoInicial, gb); ++gb.gridy
        painel.add(labelInstrucoes, gb); ++gb.gridy
        painel.add(espaco, gb); ++gb.gridy
        painel.add(labelRepeticoes, gb); ++gb.gridy
        painel.add(labelTempo, gb); ++gb.gridy
        gb.gridy = 0; ++gb.gridx
        painel.add(instrucaoInicialLinhaDeBase, gb); ++gb.gridy; gb.fill = GridBagConstraints.HORIZONTAL
        painel.add(scrollInstrucoes, gb); ++gb.gridy
        painel.add(botao, gb); ++gb.gridy
        painel.add(fieldRepeticoesCondicao1, gb); ++gb.gridy
        painel.add(fieldTempoCondicao1, gb)

        return painel
    }

    private JPanel criarPainelTeste1() {
        JPanel painel = new JPanel()
        painel.setBorder(BorderFactory.createTitledBorder('Condição 1:'))
        painel.setLayout(new GridBagLayout())

        GridBagConstraints gb = ViewUtils.getGb()

        JLabel labelInstrucoes = new JLabel('Intruções:')
        JLabel labelRepeticoes = new JLabel('Repeticoes:')
        JLabel labelTempo = new JLabel('Tempo Limite:')
        JLabel espaco = new JLabel('')

        JButton botao = new JButton('Adicionar Instrucao')
        botao.addActionListener(new ActionListener() {
            @Override
            void actionPerformed(ActionEvent actionEvent) {
                String instrucaoSelecionada = jListInstrucoesDisponiveis.getSelectedValue()
                listInstrucoesCondicao1.addElement(instrucaoSelecionada)
                revalidate()
                repaint()
            }
        })

        listInstrucoesCondicao1 = new DefaultListModel<>()
        jListInstrucoesCondicao1 = new JList<>(listInstrucoesCondicao1)
        JScrollPane scrollInstrucoes = new JScrollPane()
        scrollInstrucoes.setViewportView(jListInstrucoesCondicao1)

        fieldRepeticoesCondicao1 = new JTextField()
        fieldTempoCondicao1 = new JTextField()

        painel.add(labelInstrucoes, gb); ++gb.gridy
        painel.add(espaco, gb); ++gb.gridy
        painel.add(labelRepeticoes, gb); ++gb.gridy
        painel.add(labelTempo, gb); ++gb.gridy
        gb.gridy = 0; ++gb.gridx
        painel.add(scrollInstrucoes, gb); ++gb.gridy; gb.fill = GridBagConstraints.HORIZONTAL
        painel.add(botao, gb); ++gb.gridy
        painel.add(fieldRepeticoesCondicao1, gb); ++gb.gridy
        painel.add(fieldTempoCondicao1, gb)

        return painel
    }

    private JPanel criarPainelTeste2() {
        JPanel painel = new JPanel()
        painel.setBorder(BorderFactory.createTitledBorder('Condição 1:'))
        painel.setLayout(new GridBagLayout())

        GridBagConstraints gb = ViewUtils.getGb()

        JLabel labelInstrucoes = new JLabel('Intruções:')
        JLabel labelRepeticoes = new JLabel('Repeticoes:')
        JLabel labelTempo = new JLabel('Tempo Limite:')
        JLabel espaco = new JLabel('')

        JButton botao = new JButton('Adicionar Instrucao')
        botao.addActionListener(new ActionListener() {
            @Override
            void actionPerformed(ActionEvent actionEvent) {
                String instrucaoSelecionada = jListInstrucoesDisponiveis.getSelectedValue()
                listInstrucoesCondicao1.addElement(instrucaoSelecionada)
                revalidate()
                repaint()
            }
        })

        listInstrucoesCondicao1 = new DefaultListModel<>()
        jListInstrucoesCondicao1 = new JList<>(listInstrucoesCondicao1)
        JScrollPane scrollInstrucoes = new JScrollPane()
        scrollInstrucoes.setViewportView(jListInstrucoesCondicao1)

        fieldRepeticoesCondicao1 = new JTextField()
        fieldTempoCondicao1 = new JTextField()

        painel.add(labelInstrucoes, gb); ++gb.gridy
        painel.add(espaco, gb); ++gb.gridy
        painel.add(labelRepeticoes, gb); ++gb.gridy
        painel.add(labelTempo, gb); ++gb.gridy
        gb.gridy = 0; ++gb.gridx
        painel.add(scrollInstrucoes, gb); ++gb.gridy; gb.fill = GridBagConstraints.HORIZONTAL
        painel.add(botao, gb); ++gb.gridy
        painel.add(fieldRepeticoesCondicao1, gb); ++gb.gridy
        painel.add(fieldTempoCondicao1, gb)

        return painel
    }

    void criarInstrucao() {

    }

    @Override
    void atualizar() {

    }

    @Override
    void actionPerformed(ActionEvent e) {
        Object origem = e.getSource()
        
        if (origem == botaoCancelar) {
            dispose()
        }
        else if (origem == botaoCriarInstrucao) {
            criarInstrucao()
        }
        else {
            List<Instrucao> instrucoesConficao1 = instrucoesExistentes.findAll { it.texto in jListInstrucoesCondicao1.getSelectedValuesList() }
            try {
//                int repeticoesCondicao1 = Integer.
            } catch (Exception ignored) {
                throw new EntradaInvalidaException('Tempo e Repetiçeõs devem ser números!')
            }

            possuidorListaAtualizavel.atualizar()
        }
    }
}
