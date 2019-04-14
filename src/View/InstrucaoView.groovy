package View

import groovy.transform.CompileStatic

import javax.swing.JLabel
import javax.swing.JPanel
import java.awt.Color
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.event.MouseEvent
import java.awt.event.MouseListener

@CompileStatic
class InstrucaoView extends JPanel implements MouseListener {

    private static final Color FUNDO = Color.WHITE
    private static final int TAMANHO_FONTE = 70

    private final Object lock

    InstrucaoView(String instrucao, Object lock) {
        this.lock = lock

        JLabel labelInstrucao = new JLabel(instrucao)
        ViewUtils.modificaLabel(labelInstrucao, null, null, TAMANHO_FONTE)

        JLabel labelInformacao = new JLabel('Toque na tela para continuar')
        ViewUtils.modificaLabel(labelInformacao, null, null, 40)

        this.setLayout(new GridBagLayout())

        GridBagConstraints gb = ViewUtils.getGb()

        this.add(labelInstrucao, gb); gb.gridy = ++gb.gridy; gb.weighty = 0.2
        this.add(labelInformacao, gb)

        this.setBackground(FUNDO)
        this.validate()
        this.setVisible(true)
        this.repaint()
        this.addMouseListener(this)
    }

    @Override
    void mousePressed(MouseEvent mouseEvent) {
        synchronized (lock) {
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
