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

    private  LinhaDeBase linhaDeBase

    private LoggerService loggerService = LoggerService.instancia

    LinhaDeBaseController(JanelaPrincipalController janalePrincipalController1, ConfiguracaoGeral configuracaoGeral, Logger logger) {
        super(janalePrincipalController1, configuracaoGeral, logger)
        linhaDeBase = configuracaoGeral.linhaDeBase
        instrucoes = (ArrayList) linhaDeBase.instrucoes
        tempoLimite = linhaDeBase.tempoLimite
    }

    @Override
    void iniciar() {
        logger.log("Inicio da Linha de Base!\n", '\n')

        final Object lock = new Object()

        Map<Classe, List<Instrucao>> instrucoesParaClasses = linhaDeBase.getInstrucoesParaClasses()
        Instrucao instrucaoInicial = linhaDeBase.instrucaoInicial

        if (instrucaoInicial) {
            InstrucaoView instrucaoInicialView = new InstrucaoView(instrucaoInicial.texto, lock)
            janelePrincipalController.mudarPainel(instrucaoInicialView)

            logger.log("Mostrando a instrução inicial: $instrucaoInicial.texto", '\t')
            loggerService.registraLog(logger)

            synchronized (lock) {
                lock.wait()
            }
        }

        for (int i = 0; i < linhaDeBase.numeroRepeticoes; i++) {
            for (Classe classe : classes) {

                logger.log("Apresentando a palavra $classe.palavraSemSentido associada a $classe.palavraComSentido\n", '\n\t')

                synchronized (lock) {
                    apresentarPalavraERegistrarToques(classe, lock)
                    lock.wait()
                }

                logger.log("fim do tempo de apresentação de ${linhaDeBase.tempoLimite}s! Mostrando as instruções\n", '\n')
                loggerService.registraLog(logger)

                List<Instrucao> instrucoesClasseAtual = instrucoesParaClasses[classe]

                for (Instrucao instrucao : instrucoesClasseAtual) {

                    InstrucaoView instrucaoView = new InstrucaoView(instrucao.texto, lock)
                    janelePrincipalController.mudarPainel(instrucaoView)

                    logger.log("Mostrando a instrução: $instrucao.texto", '\t\n')
                    loggerService.registraLog(logger)

                    synchronized (lock) {
                        lock.wait()
                    }
                }
            }
        }

        janelePrincipalController.aguardarExperimentador()

        logger.log("Fim da Linha de Base!\n", '\n\n')
        loggerService.registraLog(logger)
        acabou = true
        janelePrincipalController.passarParaProximaFase()
    }

    void apresentarPalavraERegistrarToques(final Classe classe, final Object lock) {

        final Object lockToque = new Object()

        LinhaDeBaseView linhaDeBaseView = new LinhaDeBaseView(classe.palavraSemSentido, classe.cor.color, lockToque)
        janelePrincipalController.mudarPainel(linhaDeBaseView)

        boolean acabouTempo = false

        Thread threadToque = new Thread() {
            void run() {
                synchronized (this) {
                    while (!acabouTempo) {
                        boolean tocouNaPalavra

                        synchronized (lockToque) {
                            lockToque.wait()
                        }

                        if (acabouTempo) {
                            break
                        }

                        tocouNaPalavra = linhaDeBaseView.tocouNaPalavra

                        if (tocouNaPalavra) {
                            logger.log("Participante tocou no estímulo $classe.palavraSemSentido!", '\n\t')
                        } else {
                            logger.log("Participante tocou no fundo (fora do estímulo)!", '\n\t')
                        }

                        loggerService.registraLog(logger)
                    }
                }
            }
        }

        new Thread() {
            void run() {
                int tempo = 0

                while (tempo <= tempoLimite) {
                    tempo += 1
                    sleep(1000)
                }

                acabouTempo = true
                synchronized (lockToque) {
                    lockToque.notifyAll()
                }
                synchronized (lock) {
                    lock.notifyAll()
                }
            }

        }.start()
        threadToque.start()
    }
}
