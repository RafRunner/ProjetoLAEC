package View

import groovy.transform.CompileStatic

import javax.swing.JLabel
import javax.swing.JPanel
import java.awt.Color
import java.awt.Font
import java.awt.GridBagConstraints
import java.awt.GridBagLayout

@CompileStatic
class LinhaDeBaseView extends JPanel {

    JLabel palvra
    Color cor

    private static final Color FUNDO_PALAVRA = Color.WHITE
    private static final int TAMANHO_FONTE = 50

    LinhaDeBaseView(String palavra, Color cor) {

        this.palvra = new JLabel(palavra)
        this.palvra.setBackground(FUNDO_PALAVRA)
        this.palvra.setOpaque(true)
        Font fonte = this.palvra.font
        this.palvra.setFont(new Font(fonte.getName(), Font.PLAIN, TAMANHO_FONTE))

        this.cor = cor
        this.setBackground(cor)
        this.setLayout(new GridBagLayout())

        GridBagConstraints gb = new GridBagConstraints()

        gb.fill = GridBagConstraints.HORIZONTAL
        gb.weightx = 1
        gb.weighty = 1

        JLabel[] espacos = new JLabel[4]
        for (int i = 0; i < espacos.length; i++) {
            espacos[i] = new JLabel('')
        }

        int i = 0, j = 0, k = 0

        this.add(espacos[i], gb)
        gb.gridx = ++j
        i++

        this.add(espacos[i], gb)
        gb.gridx = ++j
        i++

        this.add(espacos[i], gb)
        gb.gridx = --j
        gb.gridy = ++k
        i++

        gb.fill = GridBagConstraints.NONE
        this.add(this.palvra, gb)
        gb.gridy = ++k
        gb.fill = GridBagConstraints.HORIZONTAL

        this.add(espacos[i], gb)
        this.setVisible(true)
        this.revalidate()
        this.repaint()
    }
}
