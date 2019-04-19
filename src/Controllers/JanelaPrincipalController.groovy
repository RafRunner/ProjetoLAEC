package Controllers

import Dominio.ConfiguracaoGeral
import Dominio.Enums.Ordens
import Files.Logger
import groovy.transform.CompileStatic

import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.JRootPane
import java.awt.Dimension
import java.awt.Toolkit

@CompileStatic
class JanelaPrincipalController {

    JFrame janela
    JPanel painelAtual

    ConfiguracaoGeral configuracaoGeral
    Logger logger
    Ordens ordem
    int indiceFaseAtual

    private static Dimension tamanhoTela = Toolkit.defaultToolkit.screenSize

    JanelaPrincipalController(ConfiguracaoGeral configuracaoGeral, Logger logger, JPanel painelInical) {
        this.configuracaoGeral = configuracaoGeral
        this.ordem = configuracaoGeral.ordem
        this.logger = logger
        this.indiceFaseAtual = -1

        janela = new JFrame()
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
        janela.setLocationRelativeTo(null)
        janela.setSize(tamanhoTela)
        janela.setUndecorated(true)
        janela.getRootPane().setWindowDecorationStyle(JRootPane.NONE)
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
    }
    
    void passarParaProximaFase() {
        JanelaPrincipalController self = this
        new Thread() {
            void run() {
                indiceFaseAtual++
                Class<? extends ControllerFase> classeProximoControler = ordem.ordemControllers[indiceFaseAtual]
                ControllerFase proximoControler = classeProximoControler.newInstance(self, configuracaoGeral, logger)
                proximoControler.iniciar()
            }
        }.start()
    }
}
