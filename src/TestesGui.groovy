import Controllers.Condicao1Controller
import Controllers.JanelaPrincipalController
import Dominio.Classe
import Dominio.ConfiguracaoGeral
import Dominio.Enums.ModoLinhaCondicao2
import Dominio.Enums.Ordens
import Dominio.Fases.Condicao1
import Dominio.Fases.LinhaDeBase
import Dominio.Fases.Teste2
import Dominio.Fases.Teste1
import Dominio.Fases.Condicao2
import Dominio.Instrucao
import Files.Logger
import Services.LoggerService
import View.Condicao2View
import groovy.transform.CompileStatic

import javax.swing.JPanel

@CompileStatic
class TestesGui {

    static void main(String[] args) {
        Classe classe1 = new Classe('Gandalf', 'uaehug', 'vermelho', 'WhatsApp Image 2019-02-13 at 15.48.50.jpeg')
        Classe classe2 = new Classe('Álbum', 'mefshwa', 'azul', 'recomendacao.png')
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

        Condicao1 condicao1 = new Condicao1([classe1, classe2, classe3], [instrucaoCondicao11, instrucaoCondicao12, instrucaoCondicao13], 3, tempoLimite)
        Teste2 teste2 = new Teste2([classe1, classe2, classe3], [instrucaoCondicao11, instrucaoCondicao12, instrucaoCondicao13], 3, tempoLimite)
        LinhaDeBase linhaDeBase = new LinhaDeBase([classe1, classe2, classe3], [instrucaoCondicao11, instrucaoCondicao12, instrucaoCondicao13], 3, tempoLimite)

        classe1.imagem.resize(300, 300)

        ConfiguracaoGeral configuracaoGeral = new ConfiguracaoGeral(
                'Configuracao Teste',
                [classe1, classe2, classe3],
                Ordens.ORDEM1,
                condicao1,
                linhaDeBase,
                new Condicao2([classe1, classe2, classe3], instrucinstrucaoLinhaDeBaseImagem, instrucinstrucaoLinhaDeBasePalavra, ModoLinhaCondicao2.PRIMEIRO_IMAGEM.nomeModo, 3, 3, 3, tempoLimite),
                new Teste1([classe1, classe2, classe3], [instrucinstrucaoTeste11, instrucinstrucaoTeste12], 3, tempoLimite),
                teste2)

        Logger logger = new Logger('Luisa Fernandes', 'Rafael Santana', 'm', 21, configuracaoGeral)
        LoggerService.instancia.criarArquivoResultado(logger)

        Condicao2View condicao2View = new Condicao2View([classe1, classe2, classe3].collect { it.palavraSemSentido }, classe2.cor.color, classe2.imagem)

        JanelaPrincipalController janelaPrincipalController = new JanelaPrincipalController(configuracaoGeral, logger, new JPanel())
        Condicao1Controller condicao1Controller = new Condicao1Controller(janelaPrincipalController, configuracaoGeral, logger)
        janelaPrincipalController.passarParaProximaFase()
    }
}
