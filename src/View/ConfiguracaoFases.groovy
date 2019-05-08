package View

import Controllers.PossuidorListaAtualizavel
import Dominio.Classe
import Dominio.ConfiguracaoGeral
import Dominio.Enums.ModoCondicao2
import Dominio.Exceptions.EntradaInvalidaException
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

    // TODO tudo aí
    //Corrigir adicionar novas instruções -> texto e cópias
    //Ao criar nova configuração ela aparece bugada no menu, arrumar

    private DefaultListModel<String> listInstrucoesCondicao1
    private JList<String> jListInstrucoesCondicao1
    private JTextField fieldRepeticoesCondicao1
    private JTextField fieldTempoCondicao1

    private JComboBox<String> modoCondicao2
    private JComboBox<String> instrucaoImagem
    private JComboBox<String> instrucaoPalavra
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

    private static Dimension screenSize = Toolkit.defaultToolkit.screenSize
    private static Dimension tamanhoTela = new Dimension((int) (screenSize.width * 2), (int) (screenSize.height))
    private static String StringTamanhoMax = 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa'

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

//        JPanel painelCondicao1 = criaPainelCondicao1()
        JPanel painelLinhaDeBase = criarPainelLinhaDeBase()
        JPanel painelCondicao2 = criaPainelCondicao2()
        JPanel painelTeste1 = criarPainelTeste1()
        JPanel painelTeste2 = criarPainelTeste2()

        painelFases.add(painelLinhaDeBase)
//        painelFases.add(painelCondicao1)
        painelFases.add(painelCondicao2)
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

        ViewUtils.configuraJFrame(this, tamanhoTela, 'Configurar Fases')
    }

    private JPanel criaPainelCondicao1() {
        JPanel painel = new JPanel()
        painel.setBorder(BorderFactory.createTitledBorder('Condicao 1'))
        painel.setLayout(new GridBagLayout())

        GridBagConstraints gb = ViewUtils.getGb()

        JLabel labelInstrucoes = new JLabel('Intruções:')
        JLabel labelRepeticoes = new JLabel('Repeticoes:')
        JLabel labelTempo = new JLabel('Tempo Limite:')
        JLabel espaco1 = new JLabel('')
        JLabel espaco2 = new JLabel('')

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

        JButton botaoRemover = new JButton('Remover Instrucao')
        botaoRemover.addActionListener(new ActionListener() {
            @Override
            void actionPerformed(ActionEvent actionEvent) {
                String instrucaoSelecionada = jListInstrucoesCondicao1.getSelectedValue()
                listInstrucoesCondicao1.removeElement(instrucaoSelecionada)
                repaint()
                revalidate()
            }
        })

        fieldRepeticoesCondicao1 = new JTextField()
        fieldTempoCondicao1 = new JTextField()

        painel.add(labelInstrucoes, gb); ++gb.gridy
        painel.add(espaco1, gb); ++gb.gridy
        painel.add(espaco2, gb); ++gb.gridy
        painel.add(labelRepeticoes, gb); ++gb.gridy
        painel.add(labelTempo, gb); ++gb.gridy
        gb.gridy = 0; ++gb.gridx; gb.fill = GridBagConstraints.HORIZONTAL
        painel.add(scrollInstrucoes, gb); ++gb.gridy
        painel.add(botao, gb); ++gb.gridy
        painel.add(botaoRemover, gb); ++gb.gridy
        painel.add(fieldRepeticoesCondicao1, gb); ++gb.gridy
        painel.add(fieldTempoCondicao1, gb)

        return painel
    }

    private JPanel criaPainelCondicao2() {
        JPanel painel = new JPanel()
        painel.setBorder(BorderFactory.createTitledBorder('Treino'))
        painel.setLayout(new GridBagLayout())

        GridBagConstraints gb = ViewUtils.getGb()

        JLabel labelModoCodicao2 = new JLabel('Modo Condição 2: ')
        JLabel labelInstrucaoImagen = new JLabel('Instrução Imagem:')
        JLabel labelInstrucaoPalavra = new JLabel('Instrução Palavra:')
        JLabel labelParadaAcerto = new JLabel('Condição Parada Acerto:')
        JLabel labelParadaErro = new JLabel('Condição Parada Erro:')
        JLabel labelRepeticoes = new JLabel('Repeticoes:')
        JLabel labelTempo = new JLabel('Tempo Limite:')

        modoCondicao2 = new JComboBox<>(ModoCondicao2.values().collect { it.nomeModo } as String[])
        modoCondicao2.setPrototypeDisplayValue(StringTamanhoMax)
        instrucaoImagem = new JComboBox<>(instrucoesExistentes.texto as String[])
        instrucaoImagem.setPrototypeDisplayValue(StringTamanhoMax)
        instrucaoPalavra = new JComboBox<>(instrucoesExistentes.texto as String[])
        instrucaoPalavra.setPrototypeDisplayValue(StringTamanhoMax)
        fieldCondicaoParadaAcerto = new JTextField()
        fieldCondicaoParadaErro = new JTextField()
        fieldRepeticoesCondicao2 = new JTextField()
        fieldTempoCondicao2 = new JTextField()

        fieldRepeticoesCondicao2 = new JTextField()
        fieldTempoCondicao2 = new JTextField()

        painel.add(labelModoCodicao2, gb); ++gb.gridy
        painel.add(labelInstrucaoImagen, gb); ++gb.gridy
        painel.add(labelInstrucaoPalavra, gb); ++gb.gridy
        painel.add(labelParadaAcerto, gb); ++gb.gridy
        painel.add(labelParadaErro, gb); ++gb.gridy
        painel.add(labelRepeticoes, gb); ++gb.gridy
        painel.add(labelTempo, gb); ++gb.gridy
        gb.gridy = 0; ++gb.gridx; gb.fill = GridBagConstraints.HORIZONTAL
        painel.add(modoCondicao2, gb); ++gb.gridy
        painel.add(instrucaoImagem, gb); ++gb.gridy
        painel.add(instrucaoPalavra, gb); ++gb.gridy
        painel.add(fieldCondicaoParadaAcerto, gb); ++gb.gridy
        painel.add(fieldCondicaoParadaErro, gb); ++gb.gridy
        painel.add(fieldRepeticoesCondicao2, gb); ++gb.gridy
        painel.add(fieldTempoCondicao2, gb); ++gb.gridy

        return painel
    }

    private JPanel criarPainelLinhaDeBase() {
        JPanel painel = new JPanel()
        painel.setBorder(BorderFactory.createTitledBorder('Linha de Base'))
        painel.setLayout(new GridBagLayout())

        GridBagConstraints gb = ViewUtils.getGb()

        JLabel labelIntrucaoInicial = new JLabel('Intrução Inicial:')
        JLabel labelInstrucoes = new JLabel('Intruções:')
        JLabel labelRepeticoes = new JLabel('Repeticoes:')
        JLabel labelTempo = new JLabel('Tempo Limite:')
        JLabel espaco1 = new JLabel('')
        JLabel espaco2 = new JLabel('')

        instrucaoInicialLinhaDeBase = new JComboBox<String>(instrucoesExistentes.texto as String[])
        instrucaoInicialLinhaDeBase.setPrototypeDisplayValue(StringTamanhoMax)
        instrucaoInicialLinhaDeBase.setSelectedIndex(-1)
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

        JButton botaoRemover = new JButton('Remover Instrucao')
        botaoRemover.addActionListener(new ActionListener() {
            @Override
            void actionPerformed(ActionEvent actionEvent) {
                String instrucaoSelecionada = jListInstrucoesLinhaDeBase.getSelectedValue()
                listInstrucoesLinhaDeBase.removeElement(instrucaoSelecionada)
                repaint()
                revalidate()
            }
        })

        painel.add(labelIntrucaoInicial, gb); ++gb.gridy
        painel.add(labelInstrucoes, gb); ++gb.gridy
        painel.add(espaco1, gb); ++gb.gridy
        painel.add(espaco2, gb); ++gb.gridy
        painel.add(labelRepeticoes, gb); ++gb.gridy
        painel.add(labelTempo, gb); ++gb.gridy
        gb.gridy = 0; ++gb.gridx; gb.fill = GridBagConstraints.HORIZONTAL
        painel.add(instrucaoInicialLinhaDeBase, gb); ++gb.gridy
        painel.add(scrollInstrucoes, gb); ++gb.gridy
        painel.add(botao, gb); ++gb.gridy
        painel.add(botaoRemover, gb); ++gb.gridy
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
        JLabel espaco1 = new JLabel('')
        JLabel espaco2 = new JLabel('')

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

        JButton botaoRemover = new JButton('Remover Instrucao')
        botaoRemover.addActionListener(new ActionListener() {
            @Override
            void actionPerformed(ActionEvent actionEvent) {
                String instrucaoSelecionada = jListInstrucoesTeste1.getSelectedValue()
                listInstrucoesTeste1.removeElement(instrucaoSelecionada)
                repaint()
                revalidate()
            }
        })

        painel.add(labelIntrucaoInicial, gb); ++gb.gridy
        painel.add(labelInstrucoes, gb); ++gb.gridy
        painel.add(espaco1, gb); ++gb.gridy
        painel.add(espaco2, gb); ++gb.gridy
        painel.add(labelRepeticoes, gb); ++gb.gridy
        painel.add(labelTempo, gb); ++gb.gridy
        gb.gridy = 0; ++gb.gridx; gb.fill = GridBagConstraints.HORIZONTAL
        painel.add(instrucaoInicialTeste1, gb); ++gb.gridy
        painel.add(scrollInstrucoes, gb); ++gb.gridy
        painel.add(botao, gb); ++gb.gridy
        painel.add(botaoRemover, gb); ++gb.gridy
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
        JLabel espaco1 = new JLabel('')
        JLabel espaco2 = new JLabel('')

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

        JButton botaoRemover = new JButton('Remover Instrucao')
        botaoRemover.addActionListener(new ActionListener() {
            @Override
            void actionPerformed(ActionEvent actionEvent) {
                String instrucaoSelecionada = jListInstrucoesTeste2.getSelectedValue()
                listInstrucoesTeste2.removeElement(instrucaoSelecionada)
                repaint()
                revalidate()
            }
        })

        listInstrucoesTeste2 = new DefaultListModel<>()
        jListInstrucoesTeste2 = new JList<>(listInstrucoesTeste2)
        jListInstrucoesTeste2.setPrototypeCellValue(StringTamanhoMax)
        JScrollPane scrollInstrucoes = new JScrollPane()
        scrollInstrucoes.setViewportView(jListInstrucoesTeste2)

        fieldRepeticoesTeste2 = new JTextField()
        fieldTempoTeste2 = new JTextField()

        painel.add(labelInstrucoes, gb); ++gb.gridy
        painel.add(espaco1, gb); ++gb.gridy
        painel.add(espaco2, gb); ++gb.gridy
        painel.add(labelRepeticoes, gb); ++gb.gridy
        painel.add(labelTempo, gb); ++gb.gridy
        gb.gridy = 0; ++gb.gridx; gb.fill = GridBagConstraints.HORIZONTAL
        painel.add(scrollInstrucoes, gb); ++gb.gridy
        painel.add(botao, gb); ++gb.gridy
        painel.add(botaoRemover, gb); ++gb.gridy
        painel.add(fieldRepeticoesTeste2, gb); ++gb.gridy
        painel.add(fieldTempoTeste2, gb)

        return painel
    }

    void criarInstrucao() {
        String texto = JOptionPane.showInputDialog(null, 'Texto da instrução:', '')
        Instrucao instrucao = new Instrucao(texto)
        instrucaoService.salvarInstrucoes([instrucao])

        instrucaoImagem.addItem(texto)
        instrucaoPalavra.addItem(texto)
        instrucoesDisponiveis.addElement(texto)
        instrucoesExistentes.add(instrucao)
        atualizar()
    }

    @Override
    void atualizar() {
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

//            List<Instrucao> instrucoesConficao1 = listInstrucoesCondicao1.delegate.collect { new Instrucao(it) }
//            try {
//                int repeticoesCondicao1 = Integer.parseInt(fieldRepeticoesCondicao1.getText())
//                int tempoCondicao1 = Integer.parseInt(fieldTempoCondicao1.getText())
//
//                Condicao1 condicao1 = new Condicao1(classes, instrucoesConficao1, repeticoesCondicao1, tempoCondicao1)
//                configuracaoGeral.condicao1 = condicao1
//            } catch (NumberFormatException ignored) {
//                throw new EntradaInvalidaException('Tempo e Repetiçeõs devem ser números!')
//            }

            String nomeModoCondicao2 = modoCondicao2.getSelectedItem()
            Instrucao instrucaoImagem = new Instrucao(instrucaoImagem.getSelectedItem().toString())
            Instrucao instrucaoPalavra = new Instrucao(instrucaoPalavra.getSelectedItem().toString())
            try {
                int condicaoParadaAcerto = Integer.parseInt(fieldCondicaoParadaAcerto.getText().trim())
                int condicaoParadaErro = Integer.parseInt(fieldCondicaoParadaErro.getText().trim())
                int repeticoesCondicao2 = Integer.parseInt(fieldRepeticoesCondicao2.getText().trim())
                int tempoCondicao2 = Integer.parseInt(fieldTempoCondicao2.getText().trim())

                Condicao2 condicao2 = new Condicao2(classes, nomeModoCondicao2, instrucaoImagem, instrucaoPalavra, condicaoParadaAcerto, condicaoParadaErro, repeticoesCondicao2, tempoCondicao2)
                configuracaoGeral.condicao2 = condicao2
            } catch (NumberFormatException ignored) {
                throw new EntradaInvalidaException('Tempo, Repetiçeõs e condições de parada devem ser números!')
            }

            String textoInstrucaoInicialLinhaDeBase = instrucaoInicialTeste1.getSelectedItem() ?: ''
            Instrucao instrucaoInicialLinhaDeBase
            if (textoInstrucaoInicialLinhaDeBase) {
                instrucaoInicialLinhaDeBase = new Instrucao(textoInstrucaoInicialLinhaDeBase)
            }
            List<Instrucao> listInstrucoesLinhaDeBase = listInstrucoesLinhaDeBase.delegate.collect { new Instrucao(it) }

            try {
                int repeticoesLinhaDeBase = Integer.parseInt(fieldRepeticoesLinhaDeBase.getText().trim())
                int tempoLinhaDeBase = Integer.parseInt(fieldTempoLinhaDeBase.getText().trim())

                LinhaDeBase linhaDeBase = new LinhaDeBase(classes, instrucaoInicialLinhaDeBase, listInstrucoesLinhaDeBase, repeticoesLinhaDeBase, tempoLinhaDeBase)
                configuracaoGeral.linhaDeBase = linhaDeBase
            } catch(NumberFormatException ignored) {
                throw new EntradaInvalidaException('Tempo e Repetiçeõs devem ser números!')
            }

            Instrucao instrucaoInicialTeste1 = new Instrucao(instrucaoInicialTeste1.getSelectedItem().toString())
            List<Instrucao> listInstrucoesTeste1 = listInstrucoesTeste1.delegate.collect { new Instrucao(it) }

            try {
                int repeticoesTeste1 = Integer.parseInt(fieldRepeticoesTeste1.getText().trim())
                int tempoTeste1 = Integer.parseInt(fieldTempoTeste1.getText().trim())

                Teste1 teste1 = new Teste1(classes, instrucaoInicialTeste1, listInstrucoesTeste1, repeticoesTeste1, tempoTeste1)
                configuracaoGeral.teste1 = teste1
            } catch(NumberFormatException ignored) {
                throw new EntradaInvalidaException('Tempo e Repetiçeõs devem ser números!')
            }

            List<Instrucao> instrucoesTeste2 = listInstrucoesTeste2.delegate.collect { new Instrucao(it) }
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
