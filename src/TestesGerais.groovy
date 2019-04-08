import Dominio.Classe
import Dominio.ConfiguracaoGeral
import Dominio.Enums.CoresDisponiveis
import Dominio.Enums.ModoLinhaDeBase
import Dominio.Enums.Ordens
import Dominio.Fases.Condicao1
import Dominio.Fases.LinhaDeBase
import Dominio.Fases.Teste1
import Dominio.Fases.Teste2
import Dominio.Fases.Treino
import Dominio.Instrucao
import Files.Logger
import Services.ConfiguracaoGeralService
import Services.LoggerService
import Utils.TextUtils
import View.LinhaDeBaseView

import javax.swing.ImageIcon
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JOptionPane
import javax.swing.JPanel

class TestesGerais {

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

        ImageIcon icon = new ImageIcon(classe2.imagem.bufferedImage)

        JPanel panel = new JPanel()
        panel.setSize(1000, 1000)
        panel.setBackground(classe2.cor.color)
        JLabel imagem = new JLabel(icon)
        panel.add(imagem)
        panel.setVisible(true)

        JOptionPane.showMessageDialog(null, panel, 'Imagem', JOptionPane.PLAIN_MESSAGE)

        println(classe1.palavraComSentido)

        ConfiguracaoGeral configuracaoGeral = new ConfiguracaoGeral(
                'Configuracao Teste',
                50,
                [classe1, classe2],
                Ordens.ORDEM1,
                condicao1,
                linhaDeBase,
                new Treino([classe1, classe2], 100, 10, 10),
                teste1,
                new Teste2(instrucinstrucaoTeste2Imagem, null, [classe1, classe2], 3, 'modo imagem'))

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

        JFrame janela = new JFrame()
        janela.setVisible(true)
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
        janela.setLocationRelativeTo(null)
        janela.setExtendedState(JFrame.MAXIMIZED_BOTH)

        janela.add(new LinhaDeBaseView(classe1.palavraSemSentido, classe1.cor.color))
    }
}
