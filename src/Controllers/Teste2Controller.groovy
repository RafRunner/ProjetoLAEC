package Controllers

import Dominio.Classe
import Dominio.ConfiguracaoGeral
import Dominio.Fases.Teste2
import Dominio.Instrucao
import Files.Logger
import Services.LoggerService
import View.Condicao1View
import View.InstrucaoView
import groovy.transform.CompileStatic

@CompileStatic
class Teste2Controller extends ControllerFase {

    private Teste2 teste2
    private int repeticoes

    LoggerService loggerService = LoggerService.instancia

    Teste2Controller(JanelaPrincipalController janalePrincipalController1, ConfiguracaoGeral configuracaoGeral, Logger logger) {
        super(janalePrincipalController1, configuracaoGeral, logger)
        teste2 = configuracaoGeral.teste2
        instrucoes = (ArrayList) teste2.instrucoes
        repeticoes = teste2.numeroRepeticoes
        tempoLimite = teste2.tempoLimite
        verificarTempo()
    }

    @Override
    void iniciar() {
        logger.log("Inicio do Teste 2!\n", '\n')

        final Object lock = new Object()
        for (Instrucao instrucao : instrucoes) {

            InstrucaoView instrucaoView = new InstrucaoView(instrucao.texto, lock)
            janelePrincipalController.mudarPainel(instrucaoView)

            logger.log("Mostrando a instrução: $instrucao.texto", '\t')
            loggerService.registraLog(logger)

            synchronized (lock) {
                lock.wait()
            }
        }

        apresentarPalavras()

        logger.log("Fim do Teste 2!\n", '\n')
        loggerService.registraLog(logger)
        acabou = true
        janelePrincipalController.passarParaProximaFase()
    }

    void apresentarPalavras () {
        final Object lock = new Object()

        for (int i = 0; i < repeticoes; i ++) {
            logger.log("Iniciando a repitição de número ${i + 1}\n", '\n\t')

            for (Classe classeAtual : classes) {
                Condicao1View condicao1ViewAtual = new Condicao1View(classes.palavraSemSentido, classeAtual.cor.color, lock)
                logger.log("Passando para tela associada a classe $classeAtual.palavraComSentido, Cor da tela: $classeAtual.cor.nomeCor\n", '\t\t')
                loggerService.registraLog(logger)

                janelePrincipalController.mudarPainel(condicao1ViewAtual)
                String palavraTocada

                while (!palavraTocada) {
                    synchronized (lock) {
                        lock.wait()
                    }

                    palavraTocada = condicao1ViewAtual.palavraTocada

                    String mensagem = "O participante clicou no estímulo $palavraTocada"

                    switch (palavraTocada) {
                        case null: mensagem = 'Toque fora de qualquer estímulo'; break

                        case classeAtual.palavraSemSentido: mensagem += ', que era o estilo associado a tela'; break

                        default: mensagem += ", que não era o estilo associado a tela"
                    }

                    logger.log(mensagem, '\t\t')
                    loggerService.registraLog(logger)
                }
            }
        }
    }
}
