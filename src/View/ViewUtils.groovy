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
        gb.weightx = 0.1
        gb.weighty = 0.1

        return gb
    }

    static void modificaLabel(JLabel label, Color corBackground, Color corFonte, int tamanhoFonte) {
        label.setBackground(corBackground)
        label.setForeground(corFonte)
        label.setOpaque(true)
        Font fonte = label.font
        label.setFont(new Font(fonte.getName(), Font.PLAIN, tamanhoFonte))
    }
}
