package View

import Files.MyImage
import groovy.transform.CompileStatic

import javax.swing.BorderFactory
import javax.swing.BoxLayout
import javax.swing.ImageIcon
import javax.swing.JLabel
import javax.swing.JPanel
import java.awt.Color
import java.awt.GridBagConstraints
import java.awt.GridBagLayout

@CompileStatic
class TreinoView extends JPanel {

    List<JLabel> palavras
    Color cor
    MyImage imagem

    int pontuacao = 0
    private int acertos = 0
    private int erros = 0

    private static final Color FUNDO_PALAVRA = Color.WHITE
    private static final int TAMANHO_FONTE = 50

    TreinoView(List<String> palavras, Color cor, MyImage imagem) {
        this.imagem = imagem
        this.cor = cor

        this.palavras = palavras.collect { new JLabel(it) }
        this.palavras.sort { Math.random() }

        JPanel painelPontos = new JPanel()
        painelPontos.setBorder(BorderFactory.createTitledBorder('Pontuação:'))
        painelPontos.setBackground(Color.WHITE)
        painelPontos.setLayout(new BoxLayout(painelPontos, BoxLayout.Y_AXIS))

        JLabel acertos = new JLabel('Acertos: ' + acertos)
        ViewUtils.modificaLabel(acertos, null, Color.BLACK, 30)
        JLabel erros = new JLabel('Erros: ' + erros)
        ViewUtils.modificaLabel(erros, null, Color.BLACK, 30)

        painelPontos.add(acertos)
        painelPontos.add(erros)

        GridBagConstraints gb = ViewUtils.getGb()

        JLabel[] espacos = new JLabel[5 + palavras.size()]
        for (int i = 0; i < espacos.length; i++) {
            espacos[i] = new JLabel('')
        }
        int i = 0, j = 0

        JPanel painelImagem = new JPanel()
        painelImagem.setLayout(new GridBagLayout())

        JLabel pontos = new JLabel('Pontos: ' + pontuacao)
        ViewUtils.modificaLabel(pontos, null, Color.BLACK, 30)

        ImageIcon icon = new ImageIcon(imagem.bufferedImage)
        JLabel labelImagem = new JLabel(icon)

        painelImagem.add(pontos, gb); gb.gridx = ++j
        painelImagem.add(espacos[i], gb); i++; gb.gridx = ++j
        painelImagem.add(labelImagem, gb); gb.gridx = ++j
        painelImagem.add(espacos[i], gb); i++; gb.gridx = ++j
        painelImagem.add(painelPontos, gb); gb.gridx = ++j
        painelImagem.setBackground(cor)
        painelImagem.validate()
        painelImagem.repaint()

        JPanel painelPalavras = new JPanel()
        painelPalavras.setLayout(new GridBagLayout())

        painelPalavras.add(espacos[i], gb); i++
        gb.gridx = ++j

        for (JLabel palavra : this.palavras) {
            ViewUtils.modificaLabel(palavra, FUNDO_PALAVRA, Color.BLACK, TAMANHO_FONTE)
            painelPalavras.add(palavra, gb)

            gb.gridx = ++j
            painelPalavras.add(espacos[i], gb); i++
            gb.gridx = ++j
        }

        painelPalavras.setBackground(cor)
        painelPalavras.validate()
        painelPalavras.repaint()

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS))
        this.add(painelImagem)
        this.add(painelPalavras)

        this.validate()
        this.setVisible(true)
        this.repaint()
    }
}
