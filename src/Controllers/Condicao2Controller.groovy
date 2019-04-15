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

    Condicao2Controller(JanelaPrincipalController janalePrincipalController1, ConfiguracaoGeral configuracaoGeral, Logger logger) {
        super(janalePrincipalController1, configuracaoGeral, logger)
        this.condicao2 = configuracaoGeral.condicao2
        this.modoCondicao2 = condicao2.modoExibicao
    }

    @Override
    void iniciar() {
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
        janelePrincipalController.passarParaProximaFase()
    }

    private void apresentarImagem() {
        for (Classe classeAtual : classes) {
            Condicao2View condicao2View = new Condicao2View(classes.palavraSemSentido, classeAtual.cor.color, classeAtual.imagem)
            janelePrincipalController.mudarPainel(condicao2View)
            jogar(classeAtual, condicao2View)
        }
    }

    private void apresentarPalavra() {
        for (Classe classeAtual : classes) {
            Condicao2View condicao2View = new Condicao2View(classes.palavraComSentido, classeAtual.cor.color, classeAtual.palavraSemSentido)
            janelePrincipalController.mudarPainel(condicao2View)
            jogar(classeAtual, condicao2View)
        }
    }

    private void jogar(Classe classe, Condicao2View condicao2View) {
        final Object lock = new Object()

        int acertos = condicao2View.acertos
        int erros = condicao2View.erros

        int condicaoParadaAcerto = condicao2.condicaoParadaAcerto
        int condicaoParadaErro = condicao2.condicaoParadaErro
    }
}
