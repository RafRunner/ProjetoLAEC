package View

import groovy.transform.CompileStatic

import javax.swing.JLabel
import javax.swing.JPanel
import java.awt.Color
import java.awt.GridBagConstraints
import java.awt.GridBagLayout

@CompileStatic
class LinhaDeBaseView extends JPanel {

    JLabel palavra
    Color cor

    private static final Color FUNDO_PALAVRA = Color.WHITE
    private static final int TAMANHO_FONTE = 50

    LinhaDeBaseView(String palavra, Color cor) {

        this.palavra = new JLabel(palavra)
        ViewUtils.modificaLabel(this.palavra, FUNDO_PALAVRA, Color.BLACK, TAMANHO_FONTE)

        this.cor = cor
        this.setBackground(cor)
        this.setLayout(new GridBagLayout())

        GridBagConstraints gb = ViewUtils.getGb()

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
        this.add(this.palavra, gb)
        gb.gridy = ++k
        gb.fill = GridBagConstraints.HORIZONTAL

        this.add(espacos[i], gb)
        this.setVisible(true)
        this.revalidate()
        this.repaint()
    }
}
