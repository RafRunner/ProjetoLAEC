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

    final private Object lock = new Object()
    private InstrucaoView instrucaoInicial
    private InstrucaoView instrucaoFinal

    private static Dimension tamanhoTela = Toolkit.defaultToolkit.screenSize

    JanelaPrincipalController(ConfiguracaoGeral configuracaoGeral, Logger logger) {
        this.configuracaoGeral = configuracaoGeral
        this.ordem = configuracaoGeral.ordem
        this.logger = logger
        this.indiceFaseAtual = -1

        if (configuracaoGeral.instrucaoInicial) {
            instrucaoInicial = new InstrucaoView(configuracaoGeral.instrucaoInicial.texto, lock)
        }
        if (configuracaoGeral.instrucaoFinal) {
            instrucaoFinal = new InstrucaoView(configuracaoGeral.instrucaoFinal.texto, lock, false)
        }

        janela = new JFrame()
        janela.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE)
        janela.setSize(tamanhoTela)
        janela.setLocation((int) (tamanhoTela.width/2 - janela.getSize().width/2), (int) (tamanhoTela.height/2 - janela.getSize().height/2))
        janela.setUndecorated(true)
        janela.getRootPane().setWindowDecorationStyle(JRootPane.NONE)
        janela.setResizable(false)

        painelAtual = new JPanel()
        janela.setVisible(true)
        janela.revalidate()
        janela.repaint()
    }

    void apresentarInstrucaoInicial() {
        if (!instrucaoInicial) {
            return
        }
        new Thread() {
            void run() {
                mudarPainel(instrucaoInicial)
                synchronized (lock) {
                    lock.wait()
                }
            }
        }
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
                    if (instrucaoFinal) {
                        mudarPainel(instrucaoFinal)
                    } else {
                        mudarPainel(new InstrucaoView('', lock, false))
                    }
                    logger.registraFimExperimento()
                    loggerService.registraLog(logger)
                }
            }
        }.start()
    }
}
