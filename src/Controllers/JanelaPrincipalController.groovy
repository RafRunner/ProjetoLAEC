package Controllers

import Dominio.ConfiguracaoGeral
import Dominio.Enums.Ordens
import Files.Logger
import groovy.transform.CompileStatic

import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JPanel
import java.awt.Component
import java.awt.Toolkit

@CompileStatic
class JanelaPrincipalController {

    JFrame janela
    JPanel painelAtual

    ConfiguracaoGeral configuracaoGeral
    Logger logger
    Ordens ordem
    int indiceFaseAtual

    private final static String html = "<html><body style='width: %1spx'>%1s"
    private final static int offSet = 500

    private static int larguraTela = (int) Toolkit.defaultToolkit.screenSize.width

    JanelaPrincipalController(ConfiguracaoGeral configuracaoGeral, Logger logger, JPanel painelInical) {
        this.configuracaoGeral = configuracaoGeral
        this.ordem = configuracaoGeral.ordem
        this.logger = logger
        this.indiceFaseAtual = -1

        janela = new JFrame()
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
        janela.setLocationRelativeTo(null)
        janela.setExtendedState(JFrame.MAXIMIZED_BOTH)
        janela.setResizable(false)

        painelAtual = painelInical
        janela.add(painelInical)
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

        for (Component component : painel.components) {
            if (component instanceof JLabel && component.width > larguraTela - offSet) {
                String textoAntigo = component.text
                component.setText(String.format(html, larguraTela - offSet, textoAntigo))
            }
        }

        janela.repaint()
    }
    
    void passarParaProximaFase() {
        indiceFaseAtual++
        Class<? extends ControllerFase> classeProximoControler = ordem.ordemControllers[indiceFaseAtual]
        ControllerFase proximoControler = classeProximoControler.newInstance(this, configuracaoGeral, logger)
        proximoControler.iniciar()
    }
}
