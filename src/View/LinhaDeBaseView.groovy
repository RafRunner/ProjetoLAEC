package View

import Controllers.ControllerFase
import Controllers.LinhaDeBaseController
import groovy.transform.CompileStatic

import javax.swing.JLabel
import javax.swing.JPanel
import java.awt.Color
import java.awt.Component
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.event.MouseEvent
import java.awt.event.MouseListener

@CompileStatic
class LinhaDeBaseView extends JPanel implements MouseListener {

    JLabel palavra
    Color cor

    private final ControllerFase controller
    boolean tocouNaPalavra

    private static final Color FUNDO_PALAVRA = Color.WHITE
    private static final int TAMANHO_FONTE = 200

    LinhaDeBaseView(String palavra, Color cor, ControllerFase controller) {
        this.controller = controller

        this.palavra = new JLabel(palavra)
        ViewUtils.modificaLabel(this.palavra, FUNDO_PALAVRA, Color.BLACK, TAMANHO_FONTE)
        this.palavra.addMouseListener(this)

        this.cor = cor
        this.setBackground(cor)
        this.setLayout(new GridBagLayout())

        GridBagConstraints gb = ViewUtils.getGb()

        JLabel[] espacos = ViewUtils.criaEspacos(4)

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
        this.addMouseListener(this)
    }

    @Override
    void mousePressed(MouseEvent mouseEvent) {
        synchronized (controller) {
            Component componeteTocado = (Component) mouseEvent.getSource()
            tocouNaPalavra = componeteTocado instanceof JLabel
            controller.notify()
        }
    }

    @Override
    void mouseClicked(MouseEvent mouseEvent) {}

    @Override
    void mouseReleased(MouseEvent mouseEvent) {}

    @Override
    void mouseEntered(MouseEvent mouseEvent) {}

    @Override
    void mouseExited(MouseEvent mouseEvent) {}
}
