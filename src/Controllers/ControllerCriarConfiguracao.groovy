package Controllers

import javax.swing.JFrame
import javax.swing.JPanel
import java.awt.Dimension
import java.awt.Toolkit

class ControllerCriarConfiguracao {

    JFrame janela
    JPanel painelAtual

    private static Dimension tamanhoTela = Toolkit.defaultToolkit.screenSize

    ControllerCriarConfiguracao(JPanel painelInicial) {
        janela = new JFrame()

        janela.setTitle('Criando Configuração')
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
        janela.setSize(new Dimension((int) (tamanhoTela.width / 2),(int) (tamanhoTela.height / 1.5)))
        janela.setLocation((int) (tamanhoTela.width/2 - janela.getSize().width/2), (int) (tamanhoTela.height/2 - janela.getSize().height/2))

        janela.setResizable(false)
        janela.setVisible(true)

        painelAtual = painelInicial
        janela.add(painelInicial)
        janela.setVisible(true)
        janela.revalidate()
        janela.repaint()
    }

    void mudarPainel(JPanel painel) {
        janela.remove(painelAtual)
        painelAtual = painel
        janela.add(painel)
        janela.revalidate()
        janela.repaint()
    }
}
