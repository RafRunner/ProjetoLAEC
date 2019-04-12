package View

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
    MyImage imagem

    JLabel labelImagem
    JPanel painelPontos

    int pontuacao = 0
    private int acertos = 0
    private int erros = 0

    private static final int TAMANHO_IMAGEM = 500
    private static final Color FUNDO_PALAVRA = Color.WHITE
    private static final int TAMANHO_FONTE_CLASSES = 70
    private static final int TAMANHO_FONTE_PONTUACAO = 30

    Condicao2View(List<String> palavras, Color cor, MyImage imagem) {
        this.imagem = imagem
        this.cor = cor

        this.palavras = palavras.collect { new JLabel(it, SwingConstants.CENTER) }
        this.palavras.sort { Math.random() }

        GridBagConstraints gb = ViewUtils.getGb()

        JPanel painelAcertos = new JPanel()
        this.painelPontos = painelAcertos
        painelAcertos.setBackground(Color.WHITE)
        painelAcertos.setLayout(new GridBagLayout())
        painelAcertos.addMouseListener(this)

        JLabel tituloAcertos = new JLabel('ACERTOS:')
        ViewUtils.modificaLabel(tituloAcertos, FUNDO_PALAVRA, null, TAMANHO_FONTE_PONTUACAO)
        JLabel acertos = new JLabel(acertos.toString())
        ViewUtils.modificaLabel(acertos, FUNDO_PALAVRA, null, TAMANHO_FONTE_PONTUACAO)

        painelAcertos.add(tituloAcertos, gb); gb.gridy = ++gb.gridy
        painelAcertos.add(acertos, gb)

        JPanel painelErros = new JPanel()
        this.painelPontos = painelErros
        painelErros.setBackground(Color.WHITE)
        painelErros.setLayout(new GridBagLayout())
        painelErros.addMouseListener(this)

        JLabel tituloErros = new JLabel('ERROS:')
        ViewUtils.modificaLabel(tituloErros, FUNDO_PALAVRA, null, TAMANHO_FONTE_PONTUACAO)
        JLabel erros = new JLabel(erros.toString())
        ViewUtils.modificaLabel(erros, FUNDO_PALAVRA, null, TAMANHO_FONTE_PONTUACAO)

        painelErros.add(tituloErros, gb); gb.gridy = ++gb.gridy
        painelErros.add(erros, gb)

        gb = ViewUtils.getGb()

        JLabel[] espacos = ViewUtils.criaEspacos(5 + palavras.size())
        int i = 0, j = 0

        JPanel painelImagem = new JPanel()
        painelImagem.setLayout(new GridBagLayout())

        imagem.resize(TAMANHO_IMAGEM, TAMANHO_IMAGEM)
        ImageIcon icon = new ImageIcon(imagem.bufferedImage)
        JLabel labelImagem = new JLabel(icon)
        this.labelImagem = labelImagem
        labelImagem.addMouseListener(this)

        gb.fill = GridBagConstraints.HORIZONTAL
        painelImagem.add(espacos[i], gb); i++; gb.gridx = ++j
        painelImagem.add(painelAcertos, gb); gb.gridx = ++j
        painelImagem.add(espacos[i], gb); i++; gb.gridx = ++j
        painelImagem.add(labelImagem, gb); gb.gridx = ++j
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
        this.setVisible(true)
        this.repaint()
        this.addMouseListener(this)
    }

    @Override
    void mousePressed(MouseEvent mouseEvent) {
        Component componenteCLicado = (Component) mouseEvent.getSource()
        if (componenteCLicado instanceof JLabel)
            println(componenteCLicado.getText())
        else
            println(componenteCLicado)
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
