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
import Files.Logger
import Services.ConfiguracaoGeralService
import Services.LoggerService
import Utils.TextUtils

import javax.swing.ImageIcon
import javax.swing.JLabel
import javax.swing.JOptionPane
import javax.swing.JPanel

class TestesGerais {

    static void main(String[] args) {
        Classe classe = new Classe([palavraComSentido:  'Gandalf', palavraSemSentido:  'uaehug', cor:  'vermelho', imagem: 'WhatsApp Image 2019-02-13 at 15.48.50.jpeg'])
        Instrucao instrucao = new Instrucao("Clique na tela @#", 20, true)
        Condicao1 condicao1 = new Condicao1([instrucao], [classe], 3)
        Teste1 teste1 = new Teste1(condicao1)
        LinhaDeBase linhaDeBase = new LinhaDeBase(instrucao, instrucao, [classe], 3, ModoLinhaDeBase.MODO_AMBOS.nomeModo)

        classe.imagem.resize(300, 300)

        ImageIcon icon = new ImageIcon(classe.imagem.bufferedImage)

        JPanel panel = new JPanel()
        panel.setSize(1000, 1000)
        panel.setBackground(classe.cor.color)
        JLabel imagem = new JLabel(icon)
        panel.add(imagem)
        panel.setVisible(true)

        JOptionPane.showMessageDialog(null, panel, 'Imagem', JOptionPane.PLAIN_MESSAGE)

        println(classe.palavraComSentido)

        ConfiguracaoGeral configuracaoGeral = new ConfiguracaoGeral(
                'Configuracao Teste',
                50,
                [classe],
                Ordens.ORDEM1,
                condicao1,
                linhaDeBase,
                new Treino(),
                teste1,
                new Teste2(linhaDeBase))

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
        logger.log TextUtils.mapToString(condicao1.instrucoesParaClasses.collectEntries { Map.Entry<Classe, List<Instrucao>> entry -> [entry.key.palavraComSentido, TextUtils.listToString(entry.value.texto as List<String>)] } as Map<String, String>)

        LoggerService loggerService = LoggerService.instancia
        loggerService.criarArquivoResultado(logger)
        loggerService.registraLog(logger)
    }
}
