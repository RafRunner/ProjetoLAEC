package Dominio.Fases

import Dominio.Jsonable
import groovy.transform.CompileStatic

@CompileStatic
class Teste1 implements Jsonable {

    Condicao1 condicao1

    Teste1(Condicao1 condicao1) {
        this.condicao1 = condicao1
    }

    @Override
    String toJson() {
        return condicao1.toJson()
    }

    @Override
    String montaNomeArquivo() {
        return condicao1.montaNomeArquivo()
    }
}
