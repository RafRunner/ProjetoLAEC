package View

import javax.swing.JLabel
import javax.swing.JPanel
import java.awt.Color
import java.awt.Font

class LinhaDeBaseView extends JPanel {

    JLabel palvra
    Color cor

    private static final Color FUNDO_PALAVRA = Color.WHITE
    private static final int TAMANHO_FONTE = 50

    LinhaDeBaseView(String palavra, Color cor) {
        this.setVisible(true)

        this.palvra = new JLabel(palavra)
        this.palvra.setBackground(FUNDO_PALAVRA)
        this.palvra.setOpaque(true)
        Font fonte = this.palvra.font
        this.palvra.setFont(new Font(fonte.getName(), Font.PLAIN, TAMANHO_FONTE))

        this.cor = cor
        this.setBackground(cor)

        this.add(this.palvra)
    }
}
