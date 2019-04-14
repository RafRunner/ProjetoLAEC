package Factories

import Dominio.Classe
import Dominio.Fases.Teste1
import Dominio.Instrucao
import groovy.transform.CompileStatic

@CompileStatic
class Teste1Factory {

    static Teste1 fromStringMap(Map<String, String> map, List<Classe> classes) {
        Instrucao instrucaoImagem = InstrucaoFactory.fromStringMap((map.instrucaoImagem as Map<String, String>))
        Instrucao instrucaoPalavra = InstrucaoFactory.fromStringMap((map.instrucaoPalavra as Map<String, String>))
        int repeticoes = Integer.parseInt(map.repeticoes)
        int tempoLimite = Integer.parseInt(map.tempoLimite)
        String modoExibicao = map.modoExibicao


        return new Teste1(instrucaoImagem, instrucaoPalavra, classes, repeticoes, modoExibicao, tempoLimite)
    }
}
