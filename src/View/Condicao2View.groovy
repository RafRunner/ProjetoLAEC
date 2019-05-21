package View

import groovy.transform.CompileStatic

import javax.swing.BoxLayout
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.SwingConstants
import java.awt.Color
import java.awt.Component
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.event.MouseEvent
import java.awt.event.MouseListener

@CompileStatic
class Condicao2View extends JPanel implements MouseListener {

    private List<JLabel> labelPalavras
    private List<String> palavras
    private Color cor

    private JLabel labelImagemOuPalavra

    private String estimuloClicado

    private int acertos = 0
    private int erros = 0

    private JPanel painelErros
    private JLabel labelErros
    private JLabel tituloAcertos
    private JPanel painelAcertos
    private JLabel labelAcertos

    private Object imagemOuPalavra

    private static final int TAMANHO_IMAGEM = 500
    private static final Color FUNDO_PALAVRA = Color.WHITE
    private static final int TAMANHO_FONTE_CLASSES = 70
    private static final int TAMANHO_FONTE_PONTUACAO = 30

    private final Object lock

    Condicao2View(List<String> palavras, Color cor, Object imagemOuPalavra, Object lock) {
        this.lock = lock
        this.imagemOuPalavra = imagemOuPalavra
        this.palavras = palavras
        this.cor = cor

        palavras.sort { Math.random() }
        labelPalavras = palavras.collect { new JLabel(it, SwingConstants.CENTER) }

        GridBagConstraints gb = ViewUtils.getGb()

        painelAcertos = new JPanel()
        painelAcertos.setBackground(Color.WHITE)
        painelAcertos.setLayout(new GridBagLayout())
        painelAcertos.addMouseListener(this)

        tituloAcertos = new JLabel('ACERTOS:')
        ViewUtils.modificaLabel(tituloAcertos, FUNDO_PALAVRA, null, TAMANHO_FONTE_PONTUACAO)
        labelAcertos = new JLabel(acertos.toString())
        ViewUtils.modificaLabel(labelAcertos, FUNDO_PALAVRA, null, TAMANHO_FONTE_PONTUACAO)

        painelAcertos.add(tituloAcertos, gb); gb.gridy = ++gb.gridy
        painelAcertos.add(labelAcertos, gb)

        painelErros = new JPanel()
        painelErros.setBackground(Color.WHITE)
        painelErros.setLayout(new GridBagLayout())
        painelErros.addMouseListener(this)

        JLabel tituloErros = new JLabel('ERROS:')
        ViewUtils.modificaLabel(tituloErros, FUNDO_PALAVRA, null, TAMANHO_FONTE_PONTUACAO)
        labelErros = new JLabel(erros.toString())
        ViewUtils.modificaLabel(labelErros, FUNDO_PALAVRA, null, TAMANHO_FONTE_PONTUACAO)

        painelErros.add(tituloErros, gb); gb.gridy = ++gb.gridy
        painelErros.add(labelErros, gb)

        gb = ViewUtils.getGb()

        JLabel[] espacos = ViewUtils.criaEspacos(5 + palavras.size())
        int i = 0, j = 0

        JPanel painelImagem = new JPanel()
        painelImagem.setLayout(new GridBagLayout())

        labelImagemOuPalavra = ViewUtils.criaLabelImagemOuPalavra(imagemOuPalavra, TAMANHO_IMAGEM, TAMANHO_FONTE_CLASSES)
        labelImagemOuPalavra.addMouseListener(this)

        gb.fill = GridBagConstraints.HORIZONTAL
        painelImagem.add(espacos[i], gb); i++; gb.gridx = ++j
        painelImagem.add(painelAcertos, gb); gb.gridx = ++j
        painelImagem.add(espacos[i], gb); i++; gb.gridx = ++j
        painelImagem.add(labelImagemOuPalavra, gb); gb.gridx = ++j
        painelImagem.add(espacos[i], gb); i++; gb.gridx = ++j
        painelImagem.add(painelErros, gb); gb.gridx = ++j
        painelImagem.add(espacos[i], gb); i++; gb.gridx = ++j
        painelImagem.setBackground(cor)
        painelImagem.validate()
        painelImagem.repaint()

        JPanel painelPalavras = new JPanel()
        painelPalavras.setLayout(new GridBagLayout())

        painelPalavras.add(espacos[i], gb); i++
        gb.gridx = ++j

        for (JLabel palavra : labelPalavras) {
            ViewUtils.modificaLabel(palavra, FUNDO_PALAVRA, Color.BLACK, TAMANHO_FONTE_CLASSES)
            painelPalavras.add(palavra, gb)
            palavra.addMouseListener(this)

            gb.gridx = ++j
            painelPalavras.add(espacos[i], gb); i++
            gb.gridx = ++j
        }

        painelPalavras.setBackground(cor)
        painelPalavras.validate()
        painelPalavras.repaint()

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS))
        add(painelImagem)
        add(painelPalavras)

        validate()
        repaint()
        addMouseListener(this)
    }

    private void piscaPainelAcertos() {
        painelAcertos.background = Color.BLACK
        labelAcertos.background = Color.BLACK
        tituloAcertos.background = Color.BLACK
        repaint()
        Thread.sleep(100)
        painelAcertos.background = Color.WHITE
        labelAcertos.background = Color.WHITE
        tituloAcertos.background = Color.WHITE
        repaint()
    }

    private void embaralhaPainelPalavras() {
        palavras.sort { Math.random() }
        labelPalavras.eachWithIndex { JLabel entry, int i ->
            entry.setText(palavras[i])
        }
    }

    void acerto() {
        acertos++
        labelAcertos.setText(acertos.toString())
        embaralhaPainelPalavras()
        piscaPainelAcertos()
        repaint()
    }

    void erro() {
        erros++
        labelErros.setText(erros.toString())
        embaralhaPainelPalavras()
        repaint()
    }

    @Override
    void mousePressed(MouseEvent mouseEvent) {
        synchronized (lock) {
            Component componenteClicado = (Component) mouseEvent.getSource()

            if (componenteClicado instanceof JLabel) {
                if (componenteClicado == labelImagemOuPalavra) {
                    estimuloClicado = 'imagem ou palavra'
                    lock.notifyAll()
                    return
                }

                estimuloClicado = componenteClicado.getText()
            }
            else if (componenteClicado == painelAcertos) {
                estimuloClicado = 'acertos'
            }
            else if (componenteClicado == painelErros) {
                estimuloClicado = 'erros'
            }
            else {
                estimuloClicado = null
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
