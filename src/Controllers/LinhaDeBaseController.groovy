package Controllers

import Dominio.Classe
import Dominio.ConfiguracaoGeral
import Dominio.Fases.LinhaDeBase
import Dominio.Instrucao
import Files.Logger
import Services.LoggerService
import View.InstrucaoView
import View.LinhaDeBaseView
import groovy.transform.CompileStatic

@CompileStatic
class LinhaDeBaseController extends ControllerFase {

    private ConfiguracaoGeral configuracaoGeral
    private  LinhaDeBase linhaDeBase

    private LoggerService loggerService = LoggerService.instancia

    LinhaDeBaseController(JanelaPrincipalController janalePrincipalController1, ConfiguracaoGeral configuracaoGeral, Logger logger) {
        super(janalePrincipalController1, configuracaoGeral, logger)
        linhaDeBase = configuracaoGeral.linhaDeBase
        instrucoes = (ArrayList) linhaDeBase.instrucoes
        tempoLimite = linhaDeBase.tempoLimite
        verificarTempo()
    }

    @Override
    void iniciar() {
        logger.log("Inicio da Linha de base!", '\n')

        final Object lock = new Object()

        Map<Classe, List<Instrucao>> instrucoesParaClasses = linhaDeBase.getInstrucoesParaClasses()
        Instrucao instrucaoInicial = linhaDeBase.instrucaoInicial

        InstrucaoView instrucaoInicialView = new InstrucaoView(instrucaoInicial.texto, lock)
        janelePrincipalController.mudarPainel(instrucaoInicialView)

        logger.log("Mostrando a instrução inicial: $instrucaoInicial.texto", '\t')
        loggerService.registraLog(logger)

        synchronized (lock) {
            lock.wait()
        }

        for (Classe classe : classes) {

            LinhaDeBaseView linhaDeBaseView = new LinhaDeBaseView(classe.palavraSemSentido, classe.cor.color, this)
            janelePrincipalController.mudarPainel(linhaDeBaseView)

            boolean tocouNaPalavra

            synchronized (this) {
                this.wait()
                tocouNaPalavra = linhaDeBaseView.tocouNaPalavra
            }

            if (tocouNaPalavra) {
                logger.log("Participante tocou no estímulo!\n", '\n\t')
            } else {
                logger.log("Participante tocou fora do estímulo!\n", '\n\t')
            }

            loggerService.registraLog(logger)

            List<Instrucao> instrucoesClasseAtual = instrucoesParaClasses[classe]

            for (Instrucao instrucao : instrucoesClasseAtual) {

                InstrucaoView instrucaoView = new InstrucaoView(instrucao.texto, lock)
                janelePrincipalController.mudarPainel(instrucaoView)

                logger.log("Mostrando a instrução: $instrucao.texto", '\t')
                loggerService.registraLog(logger)

                synchronized (lock) {
                    lock.wait()
                }
            }
        }
        logger.log("Fim da Linha de Base!\n", '\n')
        loggerService.registraLog(logger)
        acabou = true
        janelePrincipalController.passarParaProximaFase()
    }
}
