package Dominio.Fases

import Dominio.Jsonable
import groovy.transform.CompileStatic

@CompileStatic
class Treino implements Jsonable {

    @Override
    String toJson() {
        return ''
    }

    @Override
    Treino fromJson(String json) {
        return null
    }
}
