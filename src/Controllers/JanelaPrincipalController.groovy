package Controllers

import Dominio.ConfiguracaoGeral
import Dominio.Enums.Ordens
import Files.Logger
import Services.LoggerService
import View.InstrucaoView
import groovy.transform.CompileStatic

import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.JRootPane
import java.awt.Dimension
import java.awt.Toolkit

@CompileStatic
class JanelaPrincipalController {

    private JFrame janela
    private JPanel painelAtual
    private Ordens ordem
    private int indiceFaseAtual

    private ConfiguracaoGeral configuracaoGeral
    private Logger logger
    private LoggerService loggerService = LoggerService.instancia

    private static Dimension tamanhoTela = Toolkit.defaultToolkit.screenSize
    private static InstrucaoView instrucaoFinal = new InstrucaoView("Fim do esperimento! Por favor, chame o(a) experimentador(a)", this, false)

    JanelaPrincipalController(ConfiguracaoGeral configuracaoGeral, Logger logger, JPanel painelInical) {
        this.configuracaoGeral = configuracaoGeral
        this.ordem = configuracaoGeral.ordem
        this.logger = logger
        this.indiceFaseAtual = -1

        janela = new JFrame()
        janela.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE)
        janela.setSize(tamanhoTela)
        janela.setLocation((int) (tamanhoTela.width/2 - janela.getSize().width/2), (int) (tamanhoTela.height/2 - janela.getSize().height/2))
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

                if (classeProximoControler) {
                    ControllerFase proximoControler = classeProximoControler.newInstance(self, configuracaoGeral, logger)
                    proximoControler.iniciar()
                } else {
                    mudarPainel(instrucaoFinal)
                    logger.log("Fim do experimento!")
                    loggerService.registraLog(logger)
                }
            }
        }.start()
    }
}
