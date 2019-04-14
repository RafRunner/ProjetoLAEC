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

    ConfiguracaoGeral configuracaoGeral
    LinhaDeBase linhaDeBase

    LoggerService loggerService = LoggerService.instancia

    LinhaDeBaseController(JanelaPrincipalController janalePrincipalController1, ConfiguracaoGeral configuracaoGeral, Logger logger) {
        super(janalePrincipalController1, configuracaoGeral, logger)
        this.configuracaoGeral = configuracaoGeral
        this.linhaDeBase = configuracaoGeral.linhaDeBase
        this.instrucoes = (ArrayList) linhaDeBase.instrucoes
    }

    @Override
    void iniciar() {
        logger.log("Inicio da Linha de base!\n", '\n')

        Map<Classe, List<Instrucao>> instrucoesParaClasses = linhaDeBase.getInstrucoesParaClasses()

        for (Classe classe : classes) {
            final Object lock = new Object()

            LinhaDeBaseView linhaDeBaseView = new LinhaDeBaseView(classe.palavraSemSentido, classe.cor.color, this)
            janelePrincipalController.mudarPainel(linhaDeBaseView)

            boolean tocouNaPalavra

            synchronized (this) {
                this.wait()
                tocouNaPalavra = linhaDeBaseView.tocouNaPalavra
            }

            if (tocouNaPalavra) {
                logger.log("Participante tocou no estímulo!\n", '\t')
            } else {
                logger.log("Participante tocou fora do estímulo!\n", '\t')
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
        logger.log("Fim da Linha de Base!")
        loggerService.registraLog(logger)
        janelePrincipalController.passarParaProximaFase()
    }
}
