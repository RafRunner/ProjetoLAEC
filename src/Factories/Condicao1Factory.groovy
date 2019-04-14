package Factories

import Dominio.Classe
import Dominio.Fases.Condicao1
import Dominio.Instrucao
import groovy.transform.CompileStatic

@CompileStatic
class Condicao1Factory {

    static Condicao1 fromStringMap(Map<String, String> map, List<Classe> classes) {
        List<Instrucao> instrucoes = (map.instrucoes as List<Map<String, String>>).collect { InstrucaoFactory.fromStringMap(it) }
        int numeroRepeticoes = Integer.parseInt(map.numeroRepeticoes)
        int tempoLimite = Integer.parseInt(map.tempoLimite)

        return new Condicao1(instrucoes, classes, numeroRepeticoes, tempoLimite)
    }
}
