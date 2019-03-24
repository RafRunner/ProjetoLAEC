import Dominio.Classe
import Dominio.Configuracoes.ConfiguracaoGeral
import Dominio.Enums.Ordens
import Dominio.Fases.Condicao1
import Dominio.Fases.LinhaDeBase
import Dominio.Fases.Teste1
import Dominio.Fases.Teste2
import Dominio.Fases.Treino
import Dominio.Instrucao
import Files.Ambiente
import Files.Logger
import Services.ConfiguracaoGeralService
import Services.LoggerService

import javax.swing.ImageIcon
import javax.swing.JLabel
import javax.swing.JOptionPane
import javax.swing.JPanel

class TestesGerais {

    static void main(String[] args) {
        String caminhoImagem = Ambiente.instancia.getFullPath('Imagens', 'WhatsApp Image 2019-02-13 at 15.48.50.jpeg')

        Classe classe = new Classe('Gandalf', 'uaehug', 'vermelho',caminhoImagem)
        Instrucao instrucao = new Instrucao("Clique na tela", 20, true)

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
                tituloConfiguracao: 'Configuracao Teste',
                tempoLimite: 50,
                classes: [classe],
                repeticoes: 3,
                ordem: Ordens.ORDEM1,
                condicao1: new Condicao1([instrucao], [classe], 3),
                linhaDeBase: new LinhaDeBase(instrucao, instrucao, [classe]),
                treino: new Treino(),
                teste1: new Teste1(),
                teste2: new Teste2(),)

        ConfiguracaoGeralService configuracaoGeralService = ConfiguracaoGeralService.instancia
        configuracaoGeralService.salvaConfiguracao(configuracaoGeral)
//        ConfiguracaoGeral configuracaoGeralObtidaArquivo = configuracaoGeralService.obtemConfiguracaoDoArquivo(configuracaoGeral.tituloConfiguracao)
        print(configuracaoGeral.toJson())

        Logger logger = new Logger('Luisa Fernandes', 'Rafael Santana', 'm', 21, configuracaoGeral)
        logger.log = '''James Strachan falou sobre o desenvolvimento do Groovy pela primeira vez em seu blog em Agosto de 2003.
 Em Março de 2004, Groovy foi enviado ao Java Community Process(JCP) como JSR 241 e aceito. 
 Diversas versões foram lançadas entre 2004 e 2006. 
 Depois que o processo de padronização atráves do JCP começou, a numeração de versão mudou, e uma versão chamada "1.0" foi lançada em 2 de Janeiro de 2007. 
 Depois de vários betas numerados como 1.1, em 7 de Dezembro de 2007, Groovy 1.1 Final foi lançado e imediatamente renumerado como Groovy 1.5 para refletir as várias mudanças que foram feitas.'''
        LoggerService loggerService = LoggerService.instancia
        loggerService.salvaResultado(logger)
    }
}
