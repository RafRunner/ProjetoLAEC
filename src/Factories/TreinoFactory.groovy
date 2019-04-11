package Factories

import Dominio.Classe
import Dominio.Fases.Treino
import groovy.transform.CompileStatic

@CompileStatic
class TreinoFactory {

    static Treino fromStringMap(Map<String, String> map, List<Classe> classes) {
        Integer pontuacaoInicial = Integer.parseInt(map.pontuacaoInicial)
        Integer pontosPorAcerto = Integer.parseInt(map.pontosPorAcerto)
        Integer pontosPorErro = Integer.parseInt(map.pontosPorErro)
        Integer condicaoParadaAcerto = Integer.parseInt(map.condicaoParadaAcerto)
        Integer condicaoParadaErro = Integer.parseInt(map.condicaoParadaErro)

        return new Treino(classes, pontuacaoInicial, pontosPorAcerto, pontosPorErro, condicaoParadaAcerto, condicaoParadaErro)
    }
}
