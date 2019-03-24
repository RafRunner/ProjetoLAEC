package Dominio.Fases

import Dominio.Jsonable
import groovy.transform.CompileStatic

@CompileStatic
class Teste2 implements Jsonable {

    @Override
    String toJson() {
        return ''
    }

    @Override
    Teste2 fromJson(String json) {
        return null
    }
}
