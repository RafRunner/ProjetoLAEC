package Factories

import Dominio.Classe
import Dominio.Fases.Condicao2
import Dominio.Instrucao
import groovy.transform.CompileStatic

@CompileStatic
class Condicao2Factory {

    static Condicao2 fromStringMap(Map<String, String> map, List<Classe> classes) {
        int condicaoParadaAcerto = Integer.parseInt(map.condicaoParadaAcerto)
        int condicaoParadaErro = Integer.parseInt(map.condicaoParadaErro)
        String modoCondicao2 = map.get('modoCondicao2')
        Instrucao instrucaoImagem = InstrucaoFactory.fromStringMap((map.instrucaoImagem as Map<String, String>))
        Instrucao instrucaoPalavra = InstrucaoFactory.fromStringMap((map.instrucaoPalavra as Map<String, String>))
        int repeticoes = Integer.parseInt(map.numeroRepeticoes)
        int tempoLimite = Integer.parseInt(map.tempoLimite)

        return new Condicao2(classes, modoCondicao2, instrucaoImagem, instrucaoPalavra, condicaoParadaAcerto, condicaoParadaErro, repeticoes, tempoLimite)
    }
}
