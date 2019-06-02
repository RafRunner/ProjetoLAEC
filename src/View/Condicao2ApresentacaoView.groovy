package View

import groovy.transform.CompileStatic

import javax.swing.BoxLayout
import javax.swing.JLabel
import javax.swing.JPanel
import java.awt.Color
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.event.MouseEvent
import java.awt.event.MouseListener

@CompileStatic
class Condicao2ApresentacaoView extends JPanel implements MouseListener {

    private Color cor

    private JLabel labelImagemOuPalavra

    private String instrucao
    private Object imagemOuPalavra

    private static final int TAMANHO_IMAGEM = 500
    private static final int TAMANHO_FONTE_CLASSES = 70

    private final Object lock

    Condicao2ApresentacaoView(Color cor, Object imagemOuPalavra, String instrucao, Object lock) {
        this.lock = lock
        this.imagemOuPalavra = imagemOuPalavra
        this.instrucao = instrucao
        this.cor = cor

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS))
        GridBagConstraints gb = ViewUtils.getGb()

        JLabel[] espacos = ViewUtils.criaEspacos(3)
        int i = 0, j = 0

        JPanel painelImagemOuPalavra = new JPanel()
        painelImagemOuPalavra.setLayout(new GridBagLayout())

        labelImagemOuPalavra = ViewUtils.criaLabelImagemOuPalavra(imagemOuPalavra, TAMANHO_IMAGEM, TAMANHO_FONTE_CLASSES)
        labelImagemOuPalavra.addMouseListener(this)

        gb.fill = GridBagConstraints.HORIZONTAL
        painelImagemOuPalavra.add(espacos[i], gb); i++; gb.gridx = ++j
        painelImagemOuPalavra.add(labelImagemOuPalavra, gb); gb.gridx = ++j
        painelImagemOuPalavra.add(espacos[i], gb); i++; gb.gridx = ++j
        painelImagemOuPalavra.setBackground(cor)
        painelImagemOuPalavra.validate()
        painelImagemOuPalavra.repaint()


        JPanel painelInstrucao = new JPanel()
        painelInstrucao.setLayout(new GridBagLayout())

        gb = ViewUtils.getGb()
        painelInstrucao.add(espacos[i], gb)

        if (instrucao) {
            JLabel labelInstrucao = new JLabel(instrucao)
            ViewUtils.modificaLabel(labelInstrucao, Color.LIGHT_GRAY, Color.BLACK, 27)

            gb.weighty = 0.0001; ++gb.gridy
            painelInstrucao.add(labelInstrucao, gb)
        }

        painelInstrucao.setBackground(cor)
        painelInstrucao.validate()
        painelInstrucao.repaint()

        add(painelImagemOuPalavra)
        add(painelInstrucao)

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
