package View

import Controllers.ControllerFase
import groovy.transform.CompileStatic

import javax.swing.BoxLayout
import javax.swing.JLabel
import javax.swing.JPanel
import java.awt.Color
import java.awt.Component
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.event.MouseEvent
import java.awt.event.MouseListener

@CompileStatic
class Condicao1View extends JPanel implements MouseListener {

    List<JLabel> palavras = []

    String palavraTocada

    private static final Color FUNDO_PALAVRA = Color.WHITE
    private static final int TAMANHO_FONTE = 90

    private final Object lock

    Condicao1View(List<String> palavras, Color cor, final Object lock) {
        this.lock = lock

        palavras.sort { Math.random() }
        for (String palavra : palavras) {
            JLabel labelPalavra = new JLabel(palavra)
            ViewUtils.modificaLabel(labelPalavra, FUNDO_PALAVRA, Color.BLACK, TAMANHO_FONTE)
            labelPalavra.addMouseListener(this)

            this.palavras.add(labelPalavra)
        }

        int quantidadePalavras = palavras.size()

        JLabel[] espacos = ViewUtils.criaEspacos(1 + quantidadePalavras)
        int i = 0, j = 0

        JPanel painelEsquerdo = new JPanel()
        JPanel painelDireito = new JPanel()

        List<JLabel> palavrasEsquerda = this.palavras.subList(0, (int) (quantidadePalavras / 2))
        List<JLabel> palavrasDireita = this.palavras.findAll { !(it in palavrasEsquerda) }

        painelEsquerdo.setLayout(new GridBagLayout())
        painelEsquerdo.setBackground(cor)
        painelDireito.setLayout(new GridBagLayout())
        painelDireito.setBackground(cor)

        GridBagConstraints gb = ViewUtils.getGb()

        for (JLabel palavra : palavrasEsquerda) {
            gb.weighty = Math.random() * Math.random()
            painelEsquerdo.add(espacos[i], gb); i++; gb.gridy = ++j
            gb.weighty = Math.random() * Math.random()
            painelEsquerdo.add(palavra, gb); gb.gridy = ++j
        }
        painelEsquerdo.add(espacos[i], gb); i++

        j = 0

        for (JLabel palavra : palavrasDireita) {
            gb.weighty = Math.random() * Math.random()
            painelDireito.add(espacos[i], gb); i++; gb.gridy = ++j
            gb.weighty = Math.random() * Math.random()
            painelDireito.add(palavra, gb); gb.gridy = ++j
        }

        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS))
        if (Math.random() > 0.5) {
            this.add(painelEsquerdo)
            this.add(painelDireito)
        } else {
            this.add(painelDireito)
            this.add(painelEsquerdo)
        }

        this.validate()
        this.setVisible(true)
        this.repaint()
        this.addMouseListener(this)
    }

    @Override
    void mousePressed(MouseEvent mouseEvent) {
        synchronized (lock) {
            Component componeteTocado = (Component) mouseEvent.getSource()

            if (componeteTocado instanceof JLabel) {
                palavraTocada = componeteTocado.getText()
            } else {
                palavraTocada = null
            }
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
