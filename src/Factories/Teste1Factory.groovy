package Factories

import Dominio.Classe
import Dominio.Fases.Teste1
import Dominio.Instrucao
import groovy.transform.CompileStatic

@CompileStatic
class Teste1Factory {

    static Teste1 fromStringMap(Map<String, String> map, List<Classe> classes) {
        Instrucao instrucaoImagem = null
        Instrucao instrucaoPalavra = null

        if (map.instrucaoImagem) {
            instrucaoImagem = InstrucaoFactory.fromStringMap((map.instrucaoImagem as Map<String, String>))
        }
        if (map.instrucaoPalavra) {
            instrucaoPalavra = InstrucaoFactory.fromStringMap((map.instrucaoPalavra as Map<String, String>))
        }

        Integer repeticoes = Integer.parseInt(map.repeticoes)
        String modoExibicao = map.modoExibicao


        return new Teste1(instrucaoImagem, instrucaoPalavra, classes, repeticoes, modoExibicao)
    }
}
