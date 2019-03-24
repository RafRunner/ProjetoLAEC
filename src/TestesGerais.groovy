import Dominio.Classe
import Dominio.Configuracoes.ConfiguracaoGeral
import Dominio.Enums.Ordens
import Dominio.Enums.Sexo
import Dominio.Fases.Condicao1
import Dominio.Fases.LinhaDeBase
import Dominio.Fases.Teste1
import Dominio.Fases.Teste2
import Dominio.Fases.Treino
import Dominio.Instrucao
import FIles.Ambiente
import Services.ConfiguracaoGeralService

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
        ConfiguracaoGeral configuracaoGeralObtidaArquivo = configuracaoGeralService.obtemConfiguracaoDoArquivo(configuracaoGeral.tituloConfiguracao)
        print(configuracaoGeral.toJson())
    }
}
