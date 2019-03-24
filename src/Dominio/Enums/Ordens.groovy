package Dominio.Enums

import Dominio.Fases.Condicao1
import Dominio.Fases.LinhaDeBase
import Dominio.Fases.Teste1
import Dominio.Fases.Teste2
import Dominio.Fases.Treino
import groovy.transform.CompileStatic

@CompileStatic
enum Ordens {

    ORDEM1('Ordem 1', [LinhaDeBase.simpleName, Condicao1.simpleName, Treino.simpleName, Teste1.simpleName, Teste2.simpleName]),
    ORDEM2('Ordem 2', [Condicao1.simpleName, LinhaDeBase.simpleName, Treino.simpleName, Teste2.simpleName, Teste1.simpleName])

    String nomeOrdem
    List<String> ordemFases

    Ordens(String nomeOrdem, List<String> ordemFases) {
        this.nomeOrdem = nomeOrdem
        this.ordemFases = ordemFases
    }
}