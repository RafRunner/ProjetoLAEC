package Controllers

import Dominio.Classe
import Dominio.ConfiguracaoGeral
import Dominio.Fases.Condicao1
import Dominio.Instrucao
import Files.Logger
import Services.LoggerService
import View.Condicao1View
import groovy.transform.CompileStatic

@CompileStatic
class Condicao1Controller extends ControllerFase {

    Condicao1 condicao1

    Condicao1View condicao1ViewAtual
    int indiceClasseAtual

    LoggerService loggerService = LoggerService.instancia

    Condicao1Controller(JanelaPrincipalController janalePrincipalController1, ConfiguracaoGeral configuracaoGeral, Logger logger) {
        super(janalePrincipalController1, configuracaoGeral, logger)
        this.condicao1 = configuracaoGeral.condicao1
    }

    @Override
    void iniciar() {
        Classe primeiraClasse = classes[0]
        ArrayList<Instrucao> instrucoes = (ArrayList) condicao1.instrucoes

        condicao1ViewAtual = new Condicao1View(classes.palavraSemSentido, primeiraClasse.cor.color, this)
        indiceClasseAtual = 0
        logger.log("Inicio da Condicao 1!\n", '\n')
        logger.log("Classe associada a primeira tela: $primeiraClasse.palavraComSentido", '\t')
        logger.log("Cor da primeira tela: $primeiraClasse.cor.nomeCor\n", '\t')
        loggerService.registraLog(logger)

        janelePrincipalController.mudarPainel(condicao1ViewAtual)
    }

    void tocou(String palavraTocada) {
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
        Classe classeAtual = classes[indiceClasseAtual]

        condicao1ViewAtual = new Condicao1View(classes.palavraSemSentido, classeAtual.cor.color, this)
        logger.log("Passando para tela associada a classe $classeAtual.palavraComSentido", '\n\t')
        logger.log("Cor da tela: $classeAtual.cor.nomeCor\n", '\t')
        loggerService.registraLog(logger)

        janelePrincipalController.mudarPainel(condicao1ViewAtual)
    }
}
