package Factories

import Dominio.Classe
import Dominio.Fases.LinhaDeBase
import Dominio.Instrucao
import groovy.transform.CompileStatic

@CompileStatic
class LinhaDeBaseFactory {

    static LinhaDeBase fromStringMap(Map<String, String> map, List<Classe> classes) {
        Instrucao instrucaoImagem = InstrucaoFactory.fromStringMap((map.instrucaoImagem as Map<String, String>))
        Instrucao instrucaoPalavra = InstrucaoFactory.fromStringMap((map.instrucaoPalavra as Map<String, String>))
        int repeticoes = Integer.parseInt(map.repeticoes)
        int tempoLimite = Integer.parseInt(map.tempoLimite)
        String modoExibicao = map.modoExibicao

        return new LinhaDeBase(instrucaoImagem, instrucaoPalavra, classes, repeticoes, modoExibicao, tempoLimite)
    }
}
