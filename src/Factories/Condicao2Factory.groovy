package Factories

import Dominio.Classe
import Dominio.Fases.Condicao2
import groovy.transform.CompileStatic

@CompileStatic
class Condicao2Factory {

    static Condicao2 fromStringMap(Map<String, String> map, List<Classe> classes) {
        Integer pontuacaoInicial = Integer.parseInt(map.pontuacaoInicial)
        Integer pontosPorAcerto = Integer.parseInt(map.pontosPorAcerto)
        Integer pontosPorErro = Integer.parseInt(map.pontosPorErro)
        Integer condicaoParadaAcerto = Integer.parseInt(map.condicaoParadaAcerto)
        Integer condicaoParadaErro = Integer.parseInt(map.condicaoParadaErro)

        return new Condicao2(classes, pontuacaoInicial, pontosPorAcerto, pontosPorErro, condicaoParadaAcerto, condicaoParadaErro)
    }
}
