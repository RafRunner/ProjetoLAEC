package View

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

    JLabel labelImagemOuPalavra
    Color cor

    private final Object lock
    boolean tocouNaPalavra

    private static final int TAMANHO_IMAGEM = 500
    private static final int TAMANHO_FONTE_CLASSES = 200

    LinhaDeBaseView(Object imagemOuPalavra, Color cor, final Object lock) {
        this.lock = lock

        this.labelImagemOuPalavra = ViewUtils.criaLabelImagemOuPalavra(imagemOuPalavra, TAMANHO_IMAGEM, TAMANHO_FONTE_CLASSES)
        this.labelImagemOuPalavra.addMouseListener(this)

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
        this.add(this.labelImagemOuPalavra, gb)
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
        synchronized (lock) {
            Component componeteTocado = (Component) mouseEvent.getSource()
            tocouNaPalavra = componeteTocado instanceof JLabel
            lock.notifyAll()
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
