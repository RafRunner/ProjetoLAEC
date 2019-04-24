package View

import Controllers.PossuidorListaAtualizavel
import Dominio.Classe
import Dominio.ConfiguracaoGeral
import Dominio.Enums.ModoCondicao2
import Dominio.Exceptions.EntradaInvalidaException
import Dominio.Fases.Condicao1
import Dominio.Fases.Condicao2
import Dominio.Fases.LinhaDeBase
import Dominio.Fases.Teste1
import Dominio.Fases.Teste2
import Dominio.Instrucao
import Services.ConfiguracaoGeralService
import Services.InstrucaoService

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

    private JComboBox<String> instrucaoInicialTeste1
    private DefaultListModel<String> listInstrucoesTeste1
    private JList<String> jListInstrucoesTeste1
    private JTextField fieldRepeticoesTeste1
    private JTextField fieldTempoTeste1

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
    private ConfiguracaoGeralService configuracaoGeralService = ConfiguracaoGeralService.instancia

    ConfiguracaoFases(ConfiguracaoGeral configuracaoGeral, PossuidorListaAtualizavel possuidorListaAtualizavel) {
        this.configuracaoGeral = configuracaoGeral
        this.possuidorListaAtualizavel = possuidorListaAtualizavel

        instrucoesExistentes = instrucaoService.obtenhaTodasAsInstrucoes()
        instrucoesExistentes.sort { it.texto }
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

        tamanhoTela = new Dimension((int) (tamanhoTela.width * 2), (int) (tamanhoTela.height))
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

        listInstrucoesCondicao1 = new DefaultListModel<>()
        jListInstrucoesCondicao1 = new JList<>(listInstrucoesCondicao1)
        jListInstrucoesCondicao1.setPrototypeCellValue(StringTamanhoMax)
        JScrollPane scrollInstrucoes = new JScrollPane()
        scrollInstrucoes.setViewportView(jListInstrucoesCondicao1)

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

        fieldRepeticoesCondicao2 = new JTextField()
        fieldTempoCondicao2 = new JTextField()

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
        jListInstrucoesLinhaDeBase.setPrototypeCellValue(StringTamanhoMax)
        JScrollPane scrollInstrucoes = new JScrollPane()
        scrollInstrucoes.setViewportView(jListInstrucoesLinhaDeBase)

        fieldTempoLinhaDeBase = new JTextField()
        fieldRepeticoesLinhaDeBase = new JTextField()

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
        painel.add(fieldRepeticoesLinhaDeBase, gb); ++gb.gridy
        painel.add(fieldTempoLinhaDeBase, gb)

        return painel
    }

    private JPanel criarPainelTeste1() {
        JPanel painel = new JPanel()
        painel.setBorder(BorderFactory.createTitledBorder('Teste 1'))
        painel.setLayout(new GridBagLayout())

        GridBagConstraints gb = ViewUtils.getGb()

        JLabel labelIntrucaoInicial = new JLabel('Intrução Inicial:')
        JLabel labelInstrucoes = new JLabel('Intruções:')
        JLabel labelRepeticoes = new JLabel('Repeticoes:')
        JLabel labelTempo = new JLabel('Tempo Limite:')
        JLabel espaco = new JLabel('')

        instrucaoInicialTeste1 = new JComboBox<String>(instrucoesExistentes.texto as String[])
        instrucaoInicialTeste1.setPrototypeDisplayValue(StringTamanhoMax)
        listInstrucoesTeste1 = new DefaultListModel<String>()
        jListInstrucoesTeste1 = new JList<>(listInstrucoesTeste1)
        jListInstrucoesTeste1.setPrototypeCellValue(StringTamanhoMax)
        JScrollPane scrollInstrucoes = new JScrollPane()
        scrollInstrucoes.setViewportView(jListInstrucoesTeste1)

        fieldRepeticoesTeste1 = new JTextField()
        fieldTempoTeste1 = new JTextField()

        JButton botao = new JButton('Adicionar Instrucao')
        botao.addActionListener(new ActionListener() {
            @Override
            void actionPerformed(ActionEvent actionEvent) {
                String instrucaoSelecionada = jListInstrucoesDisponiveis.getSelectedValue()
                listInstrucoesTeste1.addElement(instrucaoSelecionada)
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
        painel.add(instrucaoInicialTeste1, gb); ++gb.gridy; gb.fill = GridBagConstraints.HORIZONTAL
        painel.add(scrollInstrucoes, gb); ++gb.gridy
        painel.add(botao, gb); ++gb.gridy
        painel.add(fieldRepeticoesTeste1, gb); ++gb.gridy
        painel.add(fieldTempoTeste1, gb)

        return painel
    }

    private JPanel criarPainelTeste2() {
        JPanel painel = new JPanel()
        painel.setBorder(BorderFactory.createTitledBorder('Teste 2:'))
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
                listInstrucoesTeste2.addElement(instrucaoSelecionada)
                revalidate()
                repaint()
            }
        })

        listInstrucoesTeste2 = new DefaultListModel<>()
        jListInstrucoesTeste2 = new JList<>(listInstrucoesTeste2)
        jListInstrucoesLinhaDeBase.setPrototypeCellValue(StringTamanhoMax)
        JScrollPane scrollInstrucoes = new JScrollPane()
        scrollInstrucoes.setViewportView(jListInstrucoesTeste2)

        fieldRepeticoesTeste2 = new JTextField()
        fieldTempoTeste2 = new JTextField()

        painel.add(labelInstrucoes, gb); ++gb.gridy
        painel.add(espaco, gb); ++gb.gridy
        painel.add(labelRepeticoes, gb); ++gb.gridy
        painel.add(labelTempo, gb); ++gb.gridy
        gb.gridy = 0; ++gb.gridx
        painel.add(scrollInstrucoes, gb); ++gb.gridy; gb.fill = GridBagConstraints.HORIZONTAL
        painel.add(botao, gb); ++gb.gridy
        painel.add(fieldRepeticoesTeste2, gb); ++gb.gridy
        painel.add(fieldTempoTeste2, gb)

        return painel
    }

    void criarInstrucao() {
        String texto = JOptionPane.showInputDialog(null, 'Texto da instrução:', 'Criar Instrução')
        Instrucao instrucao = new Instrucao(texto)
        instrucaoService.salvarInstrucoes([instrucao])
        atualizar()
    }

    @Override
    void atualizar() {
        List<Instrucao> novasIntrucoes = instrucaoService.obtenhaTodasAsInstrucoes().findAll { !(it in instrucoesExistentes) }
        novasIntrucoes.each {
            instrucaoImagem.addItem(it.texto)
        }
        novasIntrucoes.each {
            instrucaoPalavra.addItem(it.texto)
        }
        novasIntrucoes.each {
            instrucoesDisponiveis.addElement(it.texto)
        }
        instrucoesExistentes.addAll(novasIntrucoes)
        instrucoesExistentes.sort { it.texto }
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
            List<Classe> classes = configuracaoGeral.classes

            List<Instrucao> instrucoesConficao1 = instrucoesExistentes.findAll { it.texto in listInstrucoesCondicao1.elements().this$0 }
            try {
                int repeticoesCondicao1 = Integer.parseInt(fieldRepeticoesCondicao1.getText())
                int tempoCondicao1 = Integer.parseInt(fieldTempoCondicao1.getText())

                Condicao1 condicao1 = new Condicao1(classes, instrucoesConficao1, repeticoesCondicao1, tempoCondicao1)
                configuracaoGeral.condicao1 = condicao1
            } catch (NumberFormatException ignored) {
                throw new EntradaInvalidaException('Tempo e Repetiçeõs devem ser números!')
            }

            Instrucao instrucaoImagem = new Instrucao(instrucaoImagem.getSelectedItem().toString())
            Instrucao instrucaoPalavra = new Instrucao(instrucaoPalavra.getSelectedItem().toString())
            String modoExibicao = modoExibicao.getSelectedItem().toString()
            try {
                int condicaoParadaAcerto = Integer.parseInt(fieldCondicaoParadaAcerto.getText().trim())
                int condicaoParadaErro = Integer.parseInt(fieldCondicaoParadaErro.getText().trim())
                int repeticoesCondicao2 = Integer.parseInt(fieldRepeticoesCondicao2.getText().trim())
                int tempoCondicao2 = Integer.parseInt(fieldTempoCondicao2.getText().trim())

                Condicao2 condicao2 = new Condicao2(classes, instrucaoImagem, instrucaoPalavra, modoExibicao, condicaoParadaAcerto, condicaoParadaErro, repeticoesCondicao2, tempoCondicao2)
                configuracaoGeral.condicao2 = condicao2
            } catch (NumberFormatException ignored) {
                throw new EntradaInvalidaException('Tempo, Repetiçeõs e condições de parada devem ser números!')
            }

            Instrucao instrucaoInicialLinhaDeBase = new Instrucao(instrucaoInicialLinhaDeBase.getSelectedItem().toString())
            List<Instrucao> listInstrucoesLinhaDeBase = instrucoesExistentes.findAll { it.texto in listInstrucoesLinhaDeBase.elements().this$0 }

            try {
                int repeticoesLinhaDeBase = Integer.parseInt(fieldRepeticoesLinhaDeBase.getText().trim())
                int tempoLinhaDeBase = Integer.parseInt(fieldRepeticoesLinhaDeBase.getText().trim())

                LinhaDeBase linhaDeBase = new LinhaDeBase(classes, instrucaoInicialLinhaDeBase, listInstrucoesLinhaDeBase, repeticoesLinhaDeBase, tempoLinhaDeBase)
                configuracaoGeral.linhaDeBase = linhaDeBase
            } catch(NumberFormatException ignored) {
                throw new EntradaInvalidaException('Tempo e Repetiçeõs devem ser números!')
            }

            Instrucao instrucaoInicialTeste1 = new Instrucao(instrucaoInicialTeste1.getSelectedItem().toString())
            List<Instrucao> listInstrucoesTeste1 = instrucoesExistentes.findAll { it.texto in listInstrucoesTeste1.elements().this$0 }

            try {
                int repeticoesTeste1 = Integer.parseInt(fieldRepeticoesTeste1.getText().trim())
                int tempoTeste1 = Integer.parseInt(fieldRepeticoesTeste1.getText().trim())

                Teste1 teste1 = new Teste1(classes, instrucaoInicialTeste1, listInstrucoesTeste1, repeticoesTeste1, tempoTeste1)
                configuracaoGeral.teste1 = teste1
            } catch(NumberFormatException ignored) {
                throw new EntradaInvalidaException('Tempo e Repetiçeõs devem ser números!')
            }

            List<Instrucao> instrucoesTeste2 = instrucoesExistentes.findAll { it.texto in listInstrucoesTeste2.elements().this$0 }
            try {
                int repeticoesTeste2 = Integer.parseInt(fieldRepeticoesTeste2.getText().trim())
                int tempoTeste2 = Integer.parseInt(fieldTempoTeste2.getText().trim())

                Teste2 teste2 = new Teste2(classes, instrucoesTeste2, repeticoesTeste2, tempoTeste2)
                configuracaoGeral.teste2 = teste2
            } catch (NumberFormatException ignored) {
                throw new EntradaInvalidaException('Tempo e Repetiçeõs devem ser números!')
            }

            configuracaoGeralService.salvaConfiguracao(configuracaoGeral)
            possuidorListaAtualizavel.atualizar()
            dispose()
        }
    }
}
