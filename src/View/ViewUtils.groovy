package View

import groovy.transform.CompileStatic

import javax.swing.JLabel
import java.awt.Color
import java.awt.Font
import java.awt.GridBagConstraints

@CompileStatic
class ViewUtils {

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
}
