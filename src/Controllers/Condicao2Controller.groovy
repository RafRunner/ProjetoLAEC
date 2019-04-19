package Controllers

import Dominio.Classe
import Dominio.ConfiguracaoGeral
import Dominio.Enums.ModoCondicao2
import Dominio.Fases.Condicao2
import Files.Logger
import View.Condicao2View
import groovy.transform.CompileStatic

@CompileStatic
class Condicao2Controller extends ControllerFase {

    private Condicao2 condicao2
    private ModoCondicao2 modoCondicao2

    private int indiceAtual = 0

    Condicao2Controller(JanelaPrincipalController janalePrincipalController1, ConfiguracaoGeral configuracaoGeral, Logger logger) {
        super(janalePrincipalController1, configuracaoGeral, logger)
        this.condicao2 = configuracaoGeral.condicao2
        this.modoCondicao2 = condicao2.modoExibicao
    }

    @Override
    void iniciar() {
        logger.log('Inicio da Condição2!\n')
        loggerService.registraLog(logger)

        for (int i = 0; i < condicao2.numeroRepeticoes; i++) {

            Classe classeAtual = classes[0]

            if (modoCondicao2 == ModoCondicao2.PRIMEIRO_IMAGEM) {
                apresentarImagem(classeAtual)
                apresentarPalavra(classeAtual)
            }
            else {
                apresentarPalavra(classeAtual)
                apresentarImagem(classeAtual)
            }
        }
        janelePrincipalController.passarParaProximaFase()
    }

    private void apresentarImagem(Classe classeAtual) {
        logger.log("Apresentando a imagem associada a classe $classeAtual.palavraComSentido")
        loggerService.registraLog(logger)
        jogar(classeAtual, classeAtual.imagem)

    }

    private void apresentarPalavra(Classe classeAtual) {
        logger.log("Apresentando a palavra sem sentido associada a classe $classeAtual.palavraComSentido")
        loggerService.registraLog(logger)
        jogar(classeAtual, classeAtual.palavraSemSentido)
    }

    private Classe obtemProximaClasse() {
        indiceAtual++
        return classes[indiceAtual]
    }

    private void jogar(Classe classe, Object imagemOuPalavra) {
        new Thread() {
            void run() {
                final Object lock = new Object()
                synchronized (lock) {
                    Condicao2View condicao2View = new Condicao2View(classes.palavraComSentido, classe.cor.color, imagemOuPalavra, lock)
                    janelePrincipalController.mudarPainel(condicao2View)

                    String estimuloAssociado = classe.palavraComSentido
                    boolean acabou

                    while (!acabou) {
                        lock.wait()

                        String estimuloClicado = condicao2View.estimuloClicado
                        String message

                        switch (estimuloClicado) {
                            case null               : message = "Participante clicou no fundo!"; break
                            case 'acertos'          : message = "Participante tocou no painel de acertos!"; break
                            case 'erros'            : message = "Participante tocou no painel de erros!"; break
                            case 'imagem ou palavra': message = "Participante tocou na imagem ou palavra estímulo!"; break
                            default:
                                message = "Participante tocou na palavra $estimuloClicado!"
                                if (estimuloClicado == estimuloAssociado) {
                                    message += "Que era o estímulo associado a tela!"
                                    condicao2View.acerto()
                                } else {
                                    message += "Que não era o estímulo associado a tela!"
                                    condicao2View.erro()
                                }
                        }

                        logger.log(message, '\t')
                        loggerService.registraLog(logger)
                    }

                    Classe proximaClasse = obtemProximaClasse()
                    if (imagemOuPalavra instanceof String) {
                        apresentarPalavra(proximaClasse)
                    } else {
                        apresentarImagem(proximaClasse)
                    }
                }
                }
        }.start()
    }
}
