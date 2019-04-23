package View

import Controllers.PossuidorListaAtualizavel
import Dominio.Classe
import Dominio.Enums.CoresDisponiveis
import Services.ClasseService
import Services.ImagemService
import groovy.transform.CompileStatic

import javax.swing.BorderFactory
import javax.swing.JButton
import javax.swing.JComboBox
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JTextField
import java.awt.Dimension
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.Toolkit
import java.awt.event.ActionEvent
import java.awt.event.ActionListener

@CompileStatic
class CriarClasse extends JFrame implements ActionListener {

    JTextField fieldPalavraComSentido
    JTextField fieldPalavraSemSentido
    JComboBox<String> fieldCor
    JComboBox<String> fieldImagem

    JButton botaoCriar
    JButton botaoCancelar

    ClasseService classeService = ClasseService.instancia
    ImagemService imagemService = ImagemService.instancia

    private PossuidorListaAtualizavel criadorConfiguracao

    private static Dimension tamanhoTela = Toolkit.defaultToolkit.screenSize

    CriarClasse(PossuidorListaAtualizavel criadorConfiguracao) {
        this.criadorConfiguracao = criadorConfiguracao

        JPanel painel = new JPanel()

        painel.setLayout(new GridBagLayout())
        painel.setBorder(BorderFactory.createTitledBorder('Criar classe'))

        GridBagConstraints gb = ViewUtils.getGb()
        gb.fill = GridBagConstraints.HORIZONTAL

        String[] coresDisponiveis = CoresDisponiveis.values().collect { it.nomeCor } as String[]
        String[] imagens = imagemService.nomesArquivosImagem as String[]

        JLabel labelPalavraComSentido = new JLabel('Palavra com Sentido: ')
        JLabel labelPalavraSemSentido = new JLabel('Palavra sem Sentido: ')
        JLabel labelCor = new JLabel('Cor: ')
        JLabel labelImagem = new JLabel('Imagem: ')

        fieldPalavraComSentido = new JTextField()
        fieldPalavraSemSentido = new JTextField()
        fieldCor = new JComboBox<>(coresDisponiveis)
        fieldImagem  = new JComboBox<>(imagens)

        botaoCriar = new JButton('Criar')
        botaoCriar.addActionListener(this)
        botaoCancelar = new JButton('Cancelar')
        botaoCancelar.addActionListener(this)

        painel.add(labelPalavraComSentido, gb); ++gb.gridy
        painel.add(labelPalavraSemSentido, gb); ++gb.gridy
        painel.add(labelCor, gb); ++gb.gridy
        painel.add(labelImagem, gb); ++gb.gridx; gb.gridy = 0; gb.weightx = 10

        painel.add(fieldPalavraComSentido, gb); ++gb.gridy
        painel.add(fieldPalavraSemSentido, gb); ++gb.gridy
        painel.add(fieldCor, gb); ++gb.gridy
        painel.add(fieldImagem, gb); ++gb.gridy
        gb.fill = GridBagConstraints.NONE
        gb.weighty = 3
        gb.gridx = 0
        painel.add(botaoCancelar, gb); ++gb.gridx
        painel.add(botaoCriar, gb)

        add(painel)

        ViewUtils.configuraJFrame(this, tamanhoTela, 'Criar Classe')
    }

    @Override
    void actionPerformed(ActionEvent e) {
        Object origem = e.getSource()

        if (origem == botaoCriar) {
            String palavraComSentido = fieldPalavraComSentido.getText()
            String palavraSemSentido = fieldPalavraSemSentido.getText()
            String cor = fieldCor.getSelectedItem()
            String imagem = fieldImagem.getSelectedItem()

            Classe classe = new Classe(palavraComSentido, palavraSemSentido, cor, imagem)
            classeService.salvarClasses([classe])
            criadorConfiguracao.atualizar()
            dispose()
        } else {
            dispose()
        }
    }
}
