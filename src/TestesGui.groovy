import Dominio.Classe
import Dominio.ConfiguracaoGeral
import Dominio.Enums.ModoLinhaDeBase
import Dominio.Enums.Ordens
import Dominio.Fases.Condicao1
import Dominio.Fases.LinhaDeBase
import Dominio.Fases.Teste1
import Dominio.Fases.Teste2
import Dominio.Fases.Treino
import Dominio.Instrucao
import View.TreinoView
import View.LinhaDeBaseView
import groovy.transform.CompileStatic

import javax.swing.JFrame

@CompileStatic
class TestesGui {

    static void main(String[] args) {

        Classe classe1 = new Classe('Gandalf', 'uaehug', 'vermelho', 'WhatsApp Image 2019-02-13 at 15.48.50.jpeg')
        Classe classe2 = new Classe('Álbum', 'mefshwa', 'azul', 'recomendacao.png')

        Instrucao instrucaoCondicao11 = new Instrucao("Escreva o que voce viu na folha 1@", 20, true)
        Instrucao instrucaoCondicao12 = new Instrucao("Escreva o que voce leu na folha 2@", 20, true)
        Instrucao instrucaoCondicao13 = new Instrucao("Escreva o que voce pensou na folha 3@", 20, true)

        Instrucao instrucinstrucaoLinhaDeBasePalavra = new Instrucao("Veja essas palavras", 20, true)
        Instrucao instrucinstrucaoLinhaDeBaseImagem = new Instrucao("Veja essas imagens", 20, true)

        Instrucao instrucinstrucaoTeste11 = new Instrucao("Escreva o que voce leu na folha 4@", 20, true)
        Instrucao instrucinstrucaoTeste12 = new Instrucao("Escreva o que voce pensou na folha 5@", 20, true)

        Instrucao instrucinstrucaoTeste2Palavra = new Instrucao("Veja essas palavras, com novo significado", 20, true)
        Instrucao instrucinstrucaoTeste2Imagem = new Instrucao("Veja essas imagens, com novo significado", 20, true)


        Condicao1 condicao1 = new Condicao1([instrucaoCondicao11, instrucaoCondicao12, instrucaoCondicao13], [classe1, classe2], 3)
        Teste1 teste1 = new Teste1([instrucinstrucaoTeste11, instrucinstrucaoTeste12], [classe1, classe2], 3)
        LinhaDeBase linhaDeBase = new LinhaDeBase(instrucinstrucaoLinhaDeBaseImagem, instrucinstrucaoLinhaDeBasePalavra, [classe1, classe2], 3, ModoLinhaDeBase.MODO_AMBOS.nomeModo)

        classe1.imagem.resize(300, 300)
        classe2.imagem.resize(300, 300)

        println(classe1.palavraComSentido)

        ConfiguracaoGeral configuracaoGeral = new ConfiguracaoGeral(
                'Configuracao Teste',
                50,
                [classe1, classe2],
                Ordens.ORDEM1,
                condicao1,
                linhaDeBase,
                new Treino([classe1, classe2], 100, 10, 10, 30, 3),
                teste1,
                new Teste2(instrucinstrucaoTeste2Imagem, null, [classe1, classe2], 3, 'modo imagem'))

        JFrame janela = new JFrame()
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
        janela.setLocationRelativeTo(null)
        janela.setExtendedState(JFrame.MAXIMIZED_BOTH)

        LinhaDeBaseView linhaDeBaseView = new LinhaDeBaseView(classe1.palavraSemSentido, classe1.cor.color)
        TreinoView condicao1View = new TreinoView([classe1, classe2].collect { it.palavraSemSentido }, classe2.cor.color, classe2.imagem)

        janela.add(linhaDeBaseView)
        janela.setVisible(true)
        janela.revalidate()
        janela.repaint()
    }
}
