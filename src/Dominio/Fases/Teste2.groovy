package Dominio.Fases

import Dominio.Jsonable
import groovy.transform.CompileStatic

@CompileStatic
class Teste2 implements Jsonable {

    LinhaDeBase linhaDeBase

    Teste2(LinhaDeBase linhaDeBase) {
        this.linhaDeBase = linhaDeBase
    }

    @Override
    String toJson() {
        return linhaDeBase.toJson()
    }

    @Override
    String montaNomeArquivo() {
        return linhaDeBase.montaNomeArquivo()
    }
}
