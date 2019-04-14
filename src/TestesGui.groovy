import Controllers.Condicao1Controller
import Controllers.JanelaPrincipalController
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
import Files.Logger
import Services.LoggerService
import View.InstrucaoView
import View.Condicao2View
import View.LinhaDeBaseView
import groovy.transform.CompileStatic

import javax.swing.JPanel

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

        int tempoLimite = 50

        Condicao1 condicao1 = new Condicao1([instrucaoCondicao11, instrucaoCondicao12, instrucaoCondicao13], [classe1, classe2, classe3], 3, tempoLimite)
        Teste2 teste2 = new Teste2([instrucinstrucaoTeste11, instrucinstrucaoTeste12], [classe1, classe2, classe3], 3, tempoLimite)
        LinhaDeBase linhaDeBase = new LinhaDeBase(instrucinstrucaoLinhaDeBaseImagem, instrucinstrucaoLinhaDeBasePalavra, [classe1, classe2, classe3], 3, ModoLinhaDeBase.PRIMEIRO_IMAGEM.nomeModo, tempoLimite)

        classe1.imagem.resize(500, 500)
        classe2.imagem.resize(500, 500)

        println(classe1.palavraComSentido)

        ConfiguracaoGeral configuracaoGeral = new ConfiguracaoGeral(
                'Configuracao Teste',
                [classe1, classe2, classe3],
                Ordens.ORDEM1,
                condicao1,
                linhaDeBase,
                new Condicao2([classe1, classe2, classe3], 3, 3, tempoLimite),
                new Teste1(instrucinstrucaoTeste1Imagem, instrucinstrucaoTeste1Palavra, [classe1, classe2, classe3], 3, 'primeiro imagem', tempoLimite),
                teste2)

        Logger logger = new Logger('Luisa Fernandes', 'Rafael Santana', 'm', 21, configuracaoGeral)
        LoggerService.instancia.criarArquivoResultado(logger)

        LinhaDeBaseView linhaDeBaseView = new LinhaDeBaseView(classe1.palavraSemSentido, classe1.cor.color)
        Condicao2View condicao2View = new Condicao2View([classe1, classe2, classe3].collect { it.palavraSemSentido }, classe2.cor.color, classe2.imagem)
        InstrucaoView instrucaoView = new InstrucaoView(instrucinstrucaoLinhaDeBasePalavra.texto)

        JanelaPrincipalController janelaPrincipalController = new JanelaPrincipalController(configuracaoGeral, logger, new JPanel())
        Condicao1Controller condicao1Controller = new Condicao1Controller(janelaPrincipalController, configuracaoGeral, logger)
        condicao1Controller.iniciar()
    }
}
