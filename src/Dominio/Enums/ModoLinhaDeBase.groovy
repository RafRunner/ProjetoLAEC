package Dominio.Enums

enum ModoLinhaDeBase {

    PRIMEIRO_IMAGEM('primeiro imagem'),
    PRIMEIRO_PALAVRA('primeiro palavra')

    String nomeModo

    ModoLinhaDeBase(String nomeModo) {
        this.nomeModo = nomeModo
    }
}