package Controllers

import Dominio.Classe
import Dominio.ConfiguracaoGeral
import Dominio.Fases.Teste1
import Dominio.Instrucao
import Files.Logger
import Services.LoggerService
import View.InstrucaoView
import View.LinhaDeBaseView
import groovy.transform.CompileStatic

@CompileStatic
class Teste1Controller extends ControllerFase {

    private Teste1 teste1

    private LoggerService loggerService = LoggerService.instancia

    Teste1Controller(JanelaPrincipalController janalePrincipalController1, ConfiguracaoGeral configuracaoGeral, Logger logger) {
        super(janalePrincipalController1, configuracaoGeral, logger)
        teste1 = configuracaoGeral.teste1
        instrucoes = (ArrayList) teste1.instrucoes
        tempoLimite = teste1.tempoLimite
    }

    @Override
    void iniciar() {
        logger.log("Inicio do Teste 1!\n", '\n')

        final Object lock = new Object()

        Map<Classe, List<Instrucao>> instrucoesParaClasses = teste1.getInstrucoesParaClasses()
        Instrucao instrucaoInicial = teste1.instrucaoInicial

        InstrucaoView instrucaoInicialView = new InstrucaoView(instrucaoInicial.texto, lock)
        janelePrincipalController.mudarPainel(instrucaoInicialView)

        logger.log("Mostrando a instrução inicial: $instrucaoInicial.texto", '\t')
        loggerService.registraLog(logger)

        synchronized (lock) {
            lock.wait()
        }

        for (Classe classe : classes) {

            synchronized (lock) {
                apresentarPalavraERegistrarToques(classe, lock)
                lock.wait()
            }

            logger.log("fim do tempo limite de $teste1.tempoLimite! Mostrando as instruções\n", '\n')
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
        logger.log("Fim do Teste 1!\n", '\n')
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
                            logger.log("Participante tocou no estímulo $classe.palavraSemSentido!", '\t')
                        } else {
                            logger.log("Participante tocou no fundo (fora do estímulo)!", '\t')
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
