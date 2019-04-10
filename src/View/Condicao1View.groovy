package View

import Files.MyImage
import groovy.transform.CompileStatic

import javax.swing.BoxLayout
import javax.swing.ImageIcon
import javax.swing.JLabel
import javax.swing.JPanel
import java.awt.Color
import java.awt.Font
import java.awt.GridBagConstraints
import java.awt.GridBagLayout

@CompileStatic
class Condicao1View extends JPanel {

    List<JLabel> palavras
    Color cor
    MyImage imagem

    private static final Color FUNDO_PALAVRA = Color.WHITE
    private static final int TAMANHO_FONTE = 50

    Condicao1View(List<String> palavras, Color cor, MyImage imagem) {
        this.imagem = imagem
        this.cor = cor

        this.palavras = palavras.collect { new JLabel(it) }
        this.palavras.sort { Math.random() }

        ImageIcon icon = new ImageIcon(imagem.bufferedImage)
        JLabel labelImagem = new JLabel(icon)

        GridBagConstraints gb = new GridBagConstraints()

        gb.anchor = GridBagConstraints.CENTER
        gb.fill = GridBagConstraints.NONE
        gb.weightx = 0.1
        gb.weighty = 0.1

        JLabel[] espacos = new JLabel[5 + palavras.size()]
        for (int i = 0; i < espacos.length; i++) {
            espacos[i] = new JLabel('')
        }
        int i = 0, j = 0

        JPanel painelImagem = new JPanel()
        painelImagem.setLayout(new GridBagLayout())

        painelImagem.add(labelImagem, gb)
        painelImagem.setBackground(cor)
        painelImagem.validate()
        painelImagem.repaint()

        JPanel painelPalavras = new JPanel()
        painelPalavras.setLayout(new GridBagLayout())

        painelPalavras.add(espacos[i], gb); i++
        gb.gridx = ++j

        for (JLabel palavra : this.palavras) {
            palavra.setBackground(FUNDO_PALAVRA)
            palavra.setOpaque(true)
            Font fonte = palavra.font
            palavra.setFont(new Font(fonte.getName(), Font.PLAIN, TAMANHO_FONTE))
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
