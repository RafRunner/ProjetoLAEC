import Dominio.Classe
import Dominio.ConfiguracaoGeral
import Dominio.Enums.ModoLinhaDeBase
import Dominio.Enums.Ordens
import Dominio.Fases.Condicao1
import Dominio.Fases.LinhaDeBase
import Dominio.Fases.Teste2
import Dominio.Fases.Teste1
import Dominio.Fases.Condicao2
import Dominio.Instrucao
import View.Condicao1View
import View.InstrucaoView
import View.Condicao2View
import View.LinhaDeBaseView
import groovy.transform.CompileStatic

import javax.swing.JFrame

@CompileStatic
class TestesGui {

    static void main(String[] args) {

        Classe classe1 = new Classe('Gandalf', 'uaehug1', 'vermelho', 'WhatsApp Image 2019-02-13 at 15.48.50.jpeg')
        Classe classe2 = new Classe('Álbum', 'mefshwa2', 'azul', 'recomendacao.png')
        Classe classe3 = new Classe('Luigi', 'huaheu3', 'verde', 'luigi.jpeg')

        Instrucao instrucaoCondicao11 = new Instrucao("Escreva o que voce viu na folha 1@")
        Instrucao instrucaoCondicao12 = new Instrucao("Escreva o que voce leu na folha 2@")
        Instrucao instrucaoCondicao13 = new Instrucao("Escreva o que voce pensou na folha 3@")

        Instrucao instrucinstrucaoLinhaDeBasePalavra = new Instrucao("Veja essas palavras")
        Instrucao instrucinstrucaoLinhaDeBaseImagem = new Instrucao("Veja essas imagens")

        Instrucao instrucinstrucaoTeste11 = new Instrucao("Escreva o que voce leu na folha 4@")
        Instrucao instrucinstrucaoTeste12 = new Instrucao("Escreva o que voce pensou na folha 5@")

        Instrucao instrucinstrucaoTeste1Palavra = new Instrucao("Veja essas palavras, com novo significado")
        Instrucao instrucinstrucaoTeste1Imagem = new Instrucao("Veja essas imagens, com novo significado")


        Condicao1 condicao1 = new Condicao1([instrucaoCondicao11, instrucaoCondicao12, instrucaoCondicao13], [classe1, classe2], 3)
        Teste2 teste2 = new Teste2([instrucinstrucaoTeste11, instrucinstrucaoTeste12], [classe1, classe2], 3)
        LinhaDeBase linhaDeBase = new LinhaDeBase(instrucinstrucaoLinhaDeBaseImagem, instrucinstrucaoLinhaDeBasePalavra, [classe1, classe2], 3, ModoLinhaDeBase.PRIMEIRO_IMAGEM.nomeModo)

        classe1.imagem.resize(500, 500)
        classe2.imagem.resize(500, 500)

        println(classe1.palavraComSentido)

        ConfiguracaoGeral configuracaoGeral = new ConfiguracaoGeral(
                'Configuracao Teste',
                [classe1, classe2],
                Ordens.ORDEM1,
                condicao1,
                linhaDeBase,
                new Condicao2([classe1, classe2], 100, 10, 10, 30, 3),
                new Teste1(instrucinstrucaoTeste1Imagem, instrucinstrucaoTeste1Palavra, [classe1, classe2], 3, 'primeiro imagem'),
                teste2)

        JFrame janela = new JFrame()
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
        janela.setLocationRelativeTo(null)
        janela.setExtendedState(JFrame.MAXIMIZED_BOTH)

        LinhaDeBaseView linhaDeBaseView = new LinhaDeBaseView(classe1.palavraSemSentido, classe1.cor.color)
        Condicao2View condicao2View = new Condicao2View([classe1, classe2, classe3].collect { it.palavraSemSentido }, classe2.cor.color, classe2.imagem)
        Condicao1View condicao1View = new Condicao1View([classe1, classe2, classe3].collect { it.palavraSemSentido }, classe3.cor.color)
        InstrucaoView instrucaoView = new InstrucaoView(instrucinstrucaoLinhaDeBasePalavra.texto)

        janela.add(instrucaoView)
        janela.setVisible(true)
        janela.revalidate()
        janela.repaint()
    }
}
