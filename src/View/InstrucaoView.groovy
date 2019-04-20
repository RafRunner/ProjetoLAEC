package View

import groovy.transform.CompileStatic

import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.SwingConstants
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

    InstrucaoView(String instrucao, Object lock, boolean mostrarIntrucaoToque = true) {
        this.lock = lock

        JLabel labelInstrucao = new JLabel("<html><div style='text-align: center;'>" + instrucao + "</div></html>", SwingConstants.CENTER)
        ViewUtils.modificaLabel(labelInstrucao, null, null, TAMANHO_FONTE)

        JLabel labelInformacao = new JLabel('Toque na tela para continuar')
        ViewUtils.modificaLabel(labelInformacao, null, null, 40)

        setLayout(new GridBagLayout())

        GridBagConstraints gb = ViewUtils.getGb()

        gb.fill = GridBagConstraints.HORIZONTAL
        add(labelInstrucao, gb); gb.gridy = ++gb.gridy; gb.weighty = 0.2
        gb.fill = GridBagConstraints.NONE
        if (mostrarIntrucaoToque) {
            add(labelInformacao, gb)
        }

        setBackground(FUNDO)
        validate()
        repaint()
        addMouseListener(this)
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
