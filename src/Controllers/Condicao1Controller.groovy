package Controllers

import Dominio.Classe
import Dominio.ConfiguracaoGeral
import Dominio.Fases.Condicao1
import Dominio.Instrucao
import Files.Logger
import Services.LoggerService
import View.Condicao1View
import View.InstrucaoView
import groovy.transform.CompileStatic

@CompileStatic
class Condicao1Controller extends ControllerFase {

    Condicao1 condicao1
    ArrayList<Instrucao> instrucoes

    Condicao1View condicao1ViewAtual
    int indiceClasseAtual = 0
    int repeticaoAtual = 0

    LoggerService loggerService = LoggerService.instancia

    Condicao1Controller(JanelaPrincipalController janalePrincipalController1, ConfiguracaoGeral configuracaoGeral, Logger logger) {
        super(janalePrincipalController1, configuracaoGeral, logger)
        this.condicao1 = configuracaoGeral.condicao1
        this.instrucoes = (ArrayList) condicao1.instrucoes
        indiceClasseAtual = -1
    }

    @Override
    void iniciar() {
        logger.log("Inicio da Condicao 1!\n", '\n')

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

        logger.log("Iniciando a primeira repetição", '\n\t')
        loggerService.registraLog(logger)

        passarParaProximaTela()
    }

    void toqueEstimulo(String palavraTocada) {
        if (palavraTocada == null) {
            logger.log("Toque fora de qualquer estímulo", '\t')
            loggerService.registraLog(logger)
            return
        }

        Classe classeAtual = classes[indiceClasseAtual]

        String mensagem = "O participante clicou no estímulo $palavraTocada"
        if (classeAtual.palavraSemSentido == palavraTocada ) {
            mensagem += ", que era o estilo associado a tela"
        } else {
            mensagem += ", que não era o estilo associado a tela"
        }

        logger.log(mensagem, '\t')
        loggerService.registraLog(logger)

        passarParaProximaTela()
    }

    void passarParaProximaTela() {
        indiceClasseAtual++

        if (indiceClasseAtual >= classes.size()) {
            repeticaoAtual++

            if (repeticaoAtual >= condicao1.numeroRepeticoes) {
                logger.log("Fim da Condição 1!\n\n", '\n')
                loggerService.registraLog(logger)
                janelePrincipalController.passarParaProximaFase()
                return
            }

            logger.log("Iniciando a repitição de número ${repeticaoAtual + 1}", '\n\t')
            loggerService.registraLog(logger)
            indiceClasseAtual = 0
        }

        Classe classeAtual = classes[indiceClasseAtual]

        condicao1ViewAtual = new Condicao1View(classes.palavraSemSentido, classeAtual.cor.color, this)
        logger.log("Passando para tela associada a classe $classeAtual.palavraComSentido, Cor da tela: $classeAtual.cor.nomeCor\n", '\n\t')
        loggerService.registraLog(logger)

        janelePrincipalController.mudarPainel(condicao1ViewAtual)
    }
}
