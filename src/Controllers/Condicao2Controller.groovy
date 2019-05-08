package Controllers

import Dominio.Classe
import Dominio.ConfiguracaoGeral
import Dominio.Enums.ModoCondicao2
import Dominio.Fases.Condicao2
import Files.EfeitosSonoros
import Files.Logger
import View.Condicao2View
import View.InstrucaoView
import View.LinhaDeBaseView

class Condicao2Controller extends ControllerFase {

    private Condicao2 condicao2
    private ModoCondicao2 modoCondicao2
    private String motivoFim

    private static final String condicaoFimExperimento = 'tentativas'

    Condicao2Controller(JanelaPrincipalController janalePrincipalController1, ConfiguracaoGeral configuracaoGeral, Logger logger) {
        super(janalePrincipalController1, configuracaoGeral, logger)
        condicao2 = configuracaoGeral.condicao2
        modoCondicao2 = configuracaoGeral.condicao2.modoCondicao2
        tempoLimite = condicao2.tempoLimite
        verificarTempo()
    }

    @Override
    void iniciar() {
        logger.log('Inicio do Treino!\n')
        loggerService.registraLog(logger)

        for (int i = 0; i < condicao2.numeroRepeticoes; i++) {

            if (modoCondicao2 == ModoCondicao2.PRIMEIRO_IMAGEM) {
                apresentarImagem()
                apresentarPalavra()
            }
            else if (modoCondicao2 == ModoCondicao2.PRIMEIRO_PALAVRA) {
                apresentarPalavra()
                apresentarImagem()
            }
            else if (modoCondicao2 == ModoCondicao2.SOMENTE_PALAVRA) {
                apresentarPalavra()
            }
            else if (modoCondicao2 == ModoCondicao2.SOMENTE_IMAGEM) {
                apresentarImagem()
            }
        }

        logger.log('Fim do Treino!', '\n')
        loggerService.registraLog(logger)
        acabou = true

        if (motivoFim == condicaoFimExperimento) {
            janelePrincipalController.finalizarExperimento()
        }
        else {
            janelePrincipalController.passarParaProximaFase()
        }
    }

    private void apresentarImagem() {
        apresentar(condicao2.instrucaoImagem.texto, 'Apresentando a imagem associada a classe: ', 'imagem')
    }

    private void apresentarPalavra() {
        apresentar(condicao2.instrucaoPalavra.texto, 'Apresentando a palavras associada a classe: ', 'palavraComSentido')
    }

    private void apresentar(String textoInstrucao, String mensagemLog, String imagemOuPalavra) {
        if (motivoFim == condicaoFimExperimento) {
            return
        }

        final Object lock = new Object()
        InstrucaoView instrucaoImagem = new InstrucaoView(textoInstrucao, lock)

        synchronized (lock) {
            logger.log("Mostrando a instrução: $textoInstrucao", '\t')
            janelePrincipalController.mudarPainel(instrucaoImagem)
            lock.wait()
        }

        for (Classe classeAtual : classes) {
            LinhaDeBaseView estimulo = new LinhaDeBaseView(classeAtual.palavraComSentido, classeAtual.cor.color, lock)

            logger.log('Mostrando o estímulo isoladamente!', '\n\t')
            janelePrincipalController.mudarPainel(estimulo)
            synchronized (lock) {
                lock.wait()
            }

            logger.log(mensagemLog + "$classeAtual.palavraComSentido\n", '\n\t')
            loggerService.registraLog(logger)
            jogar(classeAtual, classeAtual."$imagemOuPalavra")

            if (motivoFim == condicaoFimExperimento) {
                break
            }
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
                            message += " Acerto!"
                            EfeitosSonoros.ACERTO.play()
                            condicao2View.acerto()
                            condicao2.acerto()
                        } else {
                            message += " Erro!"
                            EfeitosSonoros.ERRO.play()
                            condicao2View.erro()
                            condicao2.erro()
                        }
                }

                logger.log(message, '\t')
                loggerService.registraLog(logger)

                List resultados = verificarFim()
                acabou = resultados[0]
                motivoFim = resultados[1]
            }
        }
    }

    private List verificarFim() {
        List fim = condicao2.acabou()
        boolean acabou = fim[0]
        String motivo = fim[1]

        if (acabou) {
            String mensagemFim
            if (motivo == 'acertos') {
                mensagemFim = 'Condição de parada por acertos atingida! Passando para o próximo bloco!'
            } else {
                mensagemFim = 'Condição de parada por tentativas atingida! Finalizando o experimento!'
            }

            logger.log(mensagemFim, '\t')
            loggerService.registraLog(logger)
        }
        return fim
    }
}
