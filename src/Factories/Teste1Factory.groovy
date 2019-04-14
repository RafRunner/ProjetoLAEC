package Factories

import Dominio.Classe
import Dominio.Fases.Teste1
import Dominio.Instrucao
import groovy.transform.CompileStatic

@CompileStatic
class Teste1Factory {

    static Teste1 fromStringMap(Map<String, String> map, List<Classe> classes) {
        List<Instrucao> instrucoes = (map.instrucoes as List<Map<String, String>>).collect { InstrucaoFactory.fromStringMap(it) }
        int numeroRepeticoes = Integer.parseInt(map.numeroRepeticoes)
        int tempoLimite = Integer.parseInt(map.tempoLimite)

        return new Teste1(classes, instrucoes, numeroRepeticoes, tempoLimite)
    }
}
