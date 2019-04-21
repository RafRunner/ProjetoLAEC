package Controllers

import Dominio.Classe
import Dominio.ConfiguracaoGeral
import Dominio.Enums.ModoCondicao2
import Dominio.Fases.Condicao2
import Files.Logger
import View.Condicao2View
import View.InstrucaoView
import groovy.transform.CompileStatic

@CompileStatic
class Condicao2Controller extends ControllerFase {

    private Condicao2 condicao2
    private ModoCondicao2 modoCondicao2

    Condicao2Controller(JanelaPrincipalController janalePrincipalController1, ConfiguracaoGeral configuracaoGeral, Logger logger) {
        super(janalePrincipalController1, configuracaoGeral, logger)
        condicao2 = configuracaoGeral.condicao2
        modoCondicao2 = condicao2.modoExibicao
        tempoLimite = condicao2.tempoLimite
        verificarTempo()
    }

    @Override
    void iniciar() {
        logger.log('Inicio da Condição 2!')
        loggerService.registraLog(logger)

        for (int i = 0; i < condicao2.numeroRepeticoes; i++) {

            if (modoCondicao2 == ModoCondicao2.PRIMEIRO_IMAGEM) {
                apresentarImagem()
                apresentarPalavra()
            }
            else {
                apresentarPalavra()
                apresentarImagem()
            }
        }

        logger.log('Fim da Condição 2!')
        loggerService.registraLog(logger)
        acabou = true
        janelePrincipalController.passarParaProximaFase()
    }

    private void apresentarImagem() {
        final Object lock = new Object()
        InstrucaoView instrucaoImagem = new InstrucaoView(condicao2.instrucaoImagem.texto, lock)

        synchronized (lock) {
            logger.log("\nMostrando a instrução: $condicao2.instrucaoImagem.texto", '\t')
            janelePrincipalController.mudarPainel(instrucaoImagem)
            lock.wait()
        }

        for (Classe classeAtual : classes) {
            logger.log("Apresentando a imagem associada a classe $classeAtual.palavraComSentido\n", '\n\t')
            loggerService.registraLog(logger)
            jogar(classeAtual, classeAtual.imagem)
        }
    }

    private void apresentarPalavra() {
        final Object lock = new Object()
        InstrucaoView instrucaoPalavra = new InstrucaoView(condicao2.instrucaoPalavra.texto, lock)

        synchronized (lock) {
            logger.log("\nMostrando a instrução: $condicao2.instrucaoImagem.texto", '\t')
            janelePrincipalController.mudarPainel(instrucaoPalavra)
            lock.wait()
        }
        for (Classe classeAtual : classes) {
            logger.log("Apresentando a palavra $classeAtual.palavraComSentido\n", '\n\t')
            loggerService.registraLog(logger)
            jogar(classeAtual, classeAtual.palavraComSentido)
        }
    }

    private void jogar(Classe classe, Object imagemOuPalavra) {
        final Object lock = new Object()
        synchronized (lock) {
            Condicao2View condicao2View = new Condicao2View(classes.palavraSemSentido, classe.cor.color, imagemOuPalavra, lock)
            janelePrincipalController.mudarPainel(condicao2View)

            String estimuloAssociado = classe.palavraSemSentido
            boolean acabou

            while (!acabou) {
                lock.wait()

                String estimuloClicado = condicao2View.estimuloClicado
                String message

                switch (estimuloClicado) {
                    case null: message = "Participante clicou no fundo!"; break
                    case 'acertos': message = "Participante tocou no painel de acertos!"; break
                    case 'erros': message = "Participante tocou no painel de erros!"; break
                    case 'imagem ou palavra': message = "Participante tocou na imagem ou palavra estímulo!"; break
                    default:
                        message = "Participante tocou na palavra $estimuloClicado!"
                        if (estimuloClicado == estimuloAssociado) {
                            message += " que era o estímulo associado a tela!"
                            condicao2View.acerto()
                            condicao2.acerto()
                        } else {
                            message += " que não era o estímulo associado a tela!"
                            condicao2View.erro()
                            condicao2.erro()
                        }
                }

                logger.log(message, '\t')
                loggerService.registraLog(logger)

                acabou = verificarFim()
            }
        }
    }

    private boolean verificarFim() {
        List fim = condicao2.acabou()
        boolean acabou = fim[0]
        String motivo = fim[1]

        if (acabou) {
            String mensagemFim
            if (motivo == 'acertos') {
                mensagemFim = 'Condição de para por acertos atingida! Passando para o próximo estímulo!'
            } else {
                mensagemFim = 'Condição de para por erros atingida! Passando para o próximo estímulo!'
            }

            logger.log(mensagemFim, '\t')
            loggerService.registraLog(logger)
        }
        return acabou
    }
}
