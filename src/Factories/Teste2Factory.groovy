package Factories

import Dominio.Classe
import Dominio.Fases.Condicao1
import Dominio.Fases.Teste2
import groovy.transform.CompileStatic

@CompileStatic
class Teste2Factory {

    static Teste2 fromStringMap(Map<String, String> map, List<Classe> classes) {
        Condicao1 condicao1 = Condicao1Factory.fromStringMap(map, classes)
        return new Teste2(classes, condicao1.instrucoes, condicao1.numeroRepeticoes, condicao1.tempoLimite)
    }
}
