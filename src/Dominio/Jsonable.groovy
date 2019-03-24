package Dominio

interface Jsonable {

    String toJson()
    Jsonable fromJson(String json)
}