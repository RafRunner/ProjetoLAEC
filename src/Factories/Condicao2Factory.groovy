package Factories

import Dominio.Classe
import Dominio.Fases.Condicao2
import Dominio.Instrucao
import groovy.transform.CompileStatic

@CompileStatic
class Condicao2Factory {

    static Condicao2 fromStringMap(Map<String, String> map, List<Classe> classes) {
        int condicaoParadaAcerto = Integer.parseInt(map.condicaoParadaAcerto)
        int condicaoParadaTentativas = Integer.parseInt(map.condicaoParadaTentativas)
        String modoCondicao2 = map.get('modoCondicao2')
        List<Instrucao> instrucaoImagem
        if (map.instrucaoImagem) {
            instrucaoImagem = (map.instrucaoImagem as List<Map<String, String>>).collect { InstrucaoFactory.fromStringMap(it) }
        }
        List<Instrucao> instrucaoPalavra
        if (map.instrucaoPalavra) {
            instrucaoPalavra = (map.instrucaoPalavra as List<Map<String, String>>).collect { InstrucaoFactory.fromStringMap(it) }
        }
        int repeticoes = Integer.parseInt(map.numeroRepeticoes)
        int tempoLimite = Integer.parseInt(map.tempoLimite)
        String instrucaoApresetacao = map.instrucaoApresetacao
        String instrucaoEscolha =  map.instrucaoEscolha

        return new Condicao2(classes, modoCondicao2, instrucaoImagem, instrucaoPalavra, condicaoParadaAcerto, condicaoParadaTentativas, repeticoes, tempoLimite, instrucaoApresetacao, instrucaoEscolha)
    }
}
