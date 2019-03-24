package Dominio.Fases

import Dominio.Jsonable
import groovy.transform.CompileStatic

@CompileStatic
class Teste1 implements Jsonable {

    @Override
    String toJson() {
        return ''
    }

    @Override
    Teste1 fromJson(String json) {
        return null
    }
}
