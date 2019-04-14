package Controllers

import Dominio.ConfiguracaoGeral
import Dominio.Enums.Ordens
import Files.Logger
import groovy.transform.CompileStatic

import javax.swing.JFrame
import javax.swing.JPanel

@CompileStatic
class JanelaPrincipalController {

    JFrame janela
    JPanel painelAtual

    ConfiguracaoGeral configuracaoGeral
    Logger logger
    Ordens ordem
    int indiceFaseAtual

    JanelaPrincipalController(ConfiguracaoGeral configuracaoGeral, Logger logger, JPanel painelInical) {
        this.configuracaoGeral = configuracaoGeral
        this.ordem = configuracaoGeral.ordem
        this.logger = logger
        this.indiceFaseAtual = -1

        janela = new JFrame()
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
        janela.setLocationRelativeTo(null)
        janela.setExtendedState(JFrame.MAXIMIZED_BOTH)

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
        indiceFaseAtual++
        Class<? extends ControllerFase> classeProximoControler = ordem.ordemControllers[indiceFaseAtual]
        ControllerFase proximoControler = classeProximoControler.newInstance(this, configuracaoGeral, logger)
        proximoControler.iniciar()
    }
}
