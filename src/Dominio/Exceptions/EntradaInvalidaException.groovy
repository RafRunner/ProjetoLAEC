package Dominio.Exceptions

import groovy.transform.CompileStatic

@CompileStatic
class EntradaInvalidaException extends Exception {

    EntradaInvalidaException(String mensagem) {
        super(mensagem)
    }
}
