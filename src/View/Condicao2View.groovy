package View

import Controllers.Condicao2Controller
import Files.MyImage
import groovy.transform.CompileStatic

import javax.swing.BoxLayout
import javax.swing.ImageIcon
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

    List<JLabel> palavras
    Color cor

    JLabel labelImagemOuPalavra
    JPanel painelPontos

    String estimuloClicado

    private int acertos = 0
    private int erros = 0

    JPanel painelErros
    JLabel labelErros
    JPanel painelAcertos
    JLabel labelAcertos

    Object imagemOuPalavra

    private static final int TAMANHO_IMAGEM = 500
    private static final Color FUNDO_PALAVRA = Color.WHITE
    private static final int TAMANHO_FONTE_CLASSES = 70
    private static final int TAMANHO_FONTE_PONTUACAO = 30

    private final Object lock

    Condicao2View(List<String> palavras, Color cor, Object imagemOuPalavra, Object lock) {
        this.lock = lock
        this.imagemOuPalavra = imagemOuPalavra
        this.cor = cor

        this.palavras = palavras.collect { new JLabel(it, SwingConstants.CENTER) }
        this.palavras.sort { Math.random() }

        GridBagConstraints gb = ViewUtils.getGb()

        painelAcertos = new JPanel()
        this.painelPontos = painelAcertos
        painelAcertos.setBackground(Color.WHITE)
        painelAcertos.setLayout(new GridBagLayout())
        painelAcertos.addMouseListener(this)

        JLabel tituloAcertos = new JLabel('ACERTOS:')
        ViewUtils.modificaLabel(tituloAcertos, FUNDO_PALAVRA, null, TAMANHO_FONTE_PONTUACAO)
        labelAcertos = new JLabel(acertos.toString())
        ViewUtils.modificaLabel(labelAcertos, FUNDO_PALAVRA, null, TAMANHO_FONTE_PONTUACAO)

        painelAcertos.add(tituloAcertos, gb); gb.gridy = ++gb.gridy
        painelAcertos.add(labelAcertos, gb)

        painelErros = new JPanel()
        this.painelPontos = painelErros
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


        JLabel labelImagemOuPalavra

        if (imagemOuPalavra instanceof MyImage) {
            imagemOuPalavra.resize(TAMANHO_IMAGEM, TAMANHO_IMAGEM)
            ImageIcon icon = new ImageIcon(imagemOuPalavra.bufferedImage)
            labelImagemOuPalavra = new JLabel(icon)
        }
        else {
            labelImagemOuPalavra = new JLabel(imagemOuPalavra.toString())
            ViewUtils.modificaLabel(labelImagemOuPalavra, FUNDO_PALAVRA, null, TAMANHO_FONTE_CLASSES + 30)
        }

        this.labelImagemOuPalavra = labelImagemOuPalavra
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

        for (JLabel palavra : this.palavras) {
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

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS))
        this.add(painelImagem)
        this.add(painelPalavras)

        this.validate()
        this.repaint()
        this.addMouseListener(this)
    }

    int getAcertos() {
        return acertos
    }

    int getErros() {
        return erros
    }

    void acerto() {
        acertos++
        labelAcertos.setText(acertos.toString())
        repaint()
    }

    void erro() {
        erros++
        labelErros.setText(erros.toString())
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
