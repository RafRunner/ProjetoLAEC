package View

import Files.MyImage
import groovy.transform.CompileStatic

import javax.swing.ImageIcon
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.SwingConstants
import java.awt.Color
import java.awt.Dimension
import java.awt.Font
import java.awt.GridBagConstraints

@CompileStatic
class ViewUtils {

    private static final Color FUNDO_PALAVRA = Color.WHITE

    static GridBagConstraints getGb() {
        GridBagConstraints gb = new GridBagConstraints()

        gb.anchor = GridBagConstraints.CENTER
        gb.fill = GridBagConstraints.NONE
        gb.gridx = 0
        gb.gridy = 0
        gb.weightx = 1
        gb.weighty = 1

        return gb
    }

    static void modificaLabel(JLabel label, Color corBackground, Color corFonte, int tamanhoFonte) {
        if (corBackground) {
            label.setBackground(corBackground)
            label.setOpaque(true)
        }
        if (corFonte) {
            label.setForeground(corFonte)
        }
        Font fonte = label.font
        label.setFont(new Font(fonte.getName(), Font.PLAIN, tamanhoFonte))
    }

    static JLabel[] criaEspacos(int quantidade) {
        JLabel[] espacos = new JLabel[quantidade]
        for (int i = 0; i < quantidade; i++) {
            espacos[i] = new JLabel('')
        }
        return espacos
    }

    static void configuraJFrame(JFrame jFrame, Dimension tamanhoTela, String titulo) {
        jFrame.setTitle(titulo)
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
        jFrame.setSize(new Dimension((int) (tamanhoTela.width / 2),(int) (tamanhoTela.height / 1.5)))
        jFrame.setLocationRelativeTo(null)

        jFrame.setResizable(false)
        jFrame.setVisible(true)
    }

    static JLabel criaLabelImagemOuPalavra(Object imagemOuPalavra, int tamanhoImagem, int tamanhoPalavra) {
        JLabel labelImagemOuPalavra

        if (imagemOuPalavra instanceof MyImage) {
            imagemOuPalavra.resize(tamanhoImagem, tamanhoImagem)
            ImageIcon icon = new ImageIcon(imagemOuPalavra.bufferedImage)
            labelImagemOuPalavra = new JLabel(icon)
        }
        else {
            labelImagemOuPalavra = new JLabel(imagemOuPalavra.toString(), SwingConstants.CENTER)
            modificaLabel(labelImagemOuPalavra, FUNDO_PALAVRA, null, tamanhoPalavra + 30)
        }

        return labelImagemOuPalavra
    }
}
