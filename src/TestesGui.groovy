import Controllers.JanelaPrincipalController
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

        Instrucao instrucaoInicialLinhaDeBase = new Instrucao("Aparecerão algumas palavras na tela, você irá lê-las e em seguida aparecerão instruções sobre o que deverá fazer. Se for pedido para escrever, escreva, se for pedido para desenhar, desenhe. Você pode fazer da forma que achar melhor, não tem resposta certa ou errada. Caso tenha alguma dúvida, chame à experimentadora.")

        Instrucao instrucaoLinhaDeBase1 = new Instrucao("Escreva o que voce viu na folha 1@")
        Instrucao instrucaoLinhaDeBase2 = new Instrucao("Escreva o que voce leu na folha 2@")
        Instrucao instrucaoLinhaDeBase3 = new Instrucao("Escreva o que voce pensou na folha 3@")

        Instrucao instrucinstrucaoCondicao2Palavra = new Instrucao("Veja essas palavras")
        Instrucao instrucinstrucaoCondicao2Imagem = new Instrucao("Veja essas imagens")

        Instrucao instrucinstrucaoTeste11 = new Instrucao("Escreva o que voce leu na folha 4@")
        Instrucao instrucinstrucaoTeste12 = new Instrucao("Escreva o que voce pensou na folha 5@")

        Instrucao instrucaoCondicao1 = new Instrucao("Aparecerão três palavras na tela. Você deverá escolher uma entre as três palavras e tocá-la. Caso tenha alguma dúvida chame à experimentadora")

        int tempoLimite = 50

        Condicao1 condicao1 = new Condicao1([classe1, classe2, classe3], [instrucaoCondicao1], 3, tempoLimite)
        Teste2 teste2 = new Teste2([classe1, classe2, classe3], [instrucaoLinhaDeBase1, instrucaoLinhaDeBase2, instrucaoLinhaDeBase3], 3, tempoLimite)
        LinhaDeBase linhaDeBase = new LinhaDeBase([classe1, classe2, classe3], instrucaoInicialLinhaDeBase, [instrucaoLinhaDeBase1, instrucaoLinhaDeBase2, instrucaoLinhaDeBase3], 3, tempoLimite)

        classe1.imagem.resize(300, 300)

        ConfiguracaoGeral configuracaoGeral = new ConfiguracaoGeral(
                'Configuracao Teste',
                [classe1, classe2, classe3],
                Ordens.ORDEM1,
                condicao1,
                linhaDeBase,
                new Condicao2([classe1, classe2, classe3], instrucinstrucaoCondicao2Imagem, instrucinstrucaoCondicao2Palavra, ModoCondicao2.PRIMEIRO_IMAGEM.nomeModo, 3, 3, 3, tempoLimite),
                new Teste1([classe1, classe2, classe3], instrucaoInicialLinhaDeBase, [instrucinstrucaoTeste11, instrucinstrucaoTeste12], 3, tempoLimite),
                teste2)

        Logger logger = new Logger('Luisa Fernandes', 'Rafael Santana', 'm', 21, configuracaoGeral)
        LoggerService.instancia.criarArquivoResultado(logger)

        Condicao2View condicao2View = new Condicao2View([classe1, classe2, classe3].collect { it.palavraSemSentido }, classe2.cor.color, classe2.imagem)

        JanelaPrincipalController janelaPrincipalController = new JanelaPrincipalController(configuracaoGeral, logger, new JPanel())
        janelaPrincipalController.passarParaProximaFase()
    }
}
