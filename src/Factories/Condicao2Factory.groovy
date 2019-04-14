package Factories

import Dominio.Classe
import Dominio.Fases.Condicao2
import groovy.transform.CompileStatic

@CompileStatic
class Condicao2Factory {

    static Condicao2 fromStringMap(Map<String, String> map, List<Classe> classes) {
        int condicaoParadaAcerto = Integer.parseInt(map.condicaoParadaAcerto)
        int condicaoParadaErro = Integer.parseInt(map.condicaoParadaErro)
        int tempoLimite = Integer.parseInt(map.tempoLimite)

        return new Condicao2(classes, condicaoParadaAcerto, condicaoParadaErro, tempoLimite)
    }
}
