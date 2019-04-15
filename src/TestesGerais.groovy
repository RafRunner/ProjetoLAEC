import Dominio.Classe
import Dominio.ConfiguracaoGeral
import Dominio.Enums.ModoCondicao2
import Dominio.Enums.Ordens
import Dominio.Fases.Condicao1
import Dominio.Fases.LinhaDeBase
import Dominio.Fases.Teste2
import Dominio.Fases.Teste1
import Dominio.Fases.Condicao2
import Dominio.Instrucao
import Files.Logger
import Services.ConfiguracaoGeralService
import Services.LoggerService
import Utils.TextUtils

class TestesGerais {

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
                new Condicao2([classe1, classe2, classe3], instrucinstrucaoLinhaDeBaseImagem, instrucinstrucaoLinhaDeBasePalavra, ModoCondicao2.PRIMEIRO_IMAGEM.nomeModo, 3, 3, 3, tempoLimite),
                new Teste1([classe1, classe2, classe3], [instrucinstrucaoTeste11, instrucinstrucaoTeste12], 3, tempoLimite),
                teste2)

        ConfiguracaoGeralService configuracaoGeralService = ConfiguracaoGeralService.instancia
        configuracaoGeralService.salvaConfiguracao(configuracaoGeral)
        ConfiguracaoGeral configuracaoGeralObtidaArquivo = configuracaoGeralService.obtemConfiguracaoDoArquivo(configuracaoGeral.montaNomeArquivo())
        print(configuracaoGeral.toJson())

        Logger logger = new Logger('Luisa Fernandes', 'Rafael Santana', 'm', 21, configuracaoGeral)
        logger.log 'James Strachan falou sobre o desenvolvimento do Groovy pela primeira vez em seu blog em Agosto de 2003.'
        logger.log 'Em Março de 2004, Groovy foi enviado ao Java Community Process(JCP) como JSR 241 e aceito. '
        logger.log 'Diversas versões foram lançadas entre 2004 e 2006. '
        logger.log 'Depois que o processo de padronização atráves do JCP começou, a numeração de versão mudou, e uma versão chamada "1.0" foi lançada em 2 de Janeiro de 2007. '
        logger.log 'Depois de vários betas numerados como 1.1, em 7 de Dezembro de 2007, Groovy 1.1 Final foi lançado e imediatamente renumerado como Groovy 1.5 para refletir as várias mudanças que foram feitas.'
        logger.log TextUtils.mapToString(condicao1.instrucoesParaClasses.collectEntries { Map.Entry<Classe, List<Instrucao>> entry -> [entry.key.palavraComSentido, TextUtils.listToJsonString(entry.value.texto as List<String>)] } as Map<String, String>)

        LoggerService loggerService = LoggerService.instancia
        loggerService.criarArquivoResultado(logger)
        loggerService.registraLog(logger)
    }
}
