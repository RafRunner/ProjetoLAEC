package Services

import Dominio.Classe
import Factories.ClasseFactory
import Files.Ambiente
import groovy.json.JsonSlurper
import groovy.transform.CompileStatic

@CompileStatic
class ClasseService {

    private static String pastaClasses = 'Classes'

    private Ambiente ambiente = Ambiente.instancia

    static ClasseService instancia = new ClasseService()
    private ClasseService() {}

    List<Classe> obtemTodasAsClasses() {
        List<File> arquivosConfiguracao = ambiente.getFiles(pastaClasses)

        return arquivosConfiguracao?.collect { obtemClasseDoArquivo(it) }?.findAll { it }
    }

    private Classe obtemClasseDoArquivo(File arquivo) {
        String json = arquivo.getText()

        try {
            JsonSlurper jsonSlurper = new JsonSlurper()
            Map classe = jsonSlurper.parseText(json) as Map

            return ClasseFactory.fromStringMap(classe)

        } catch (Exception ignored) {
            return null
        }
    }

    void salvarClasses(List<Classe> classes) {
        List<File> classesExistentes = ambiente.getFiles(pastaClasses)

        for (Classe classe : classes) {
            String nomeArquivo = classe.montaNomeArquivo()

            if (!(nomeArquivo in classesExistentes.name)) {
                String caminhoArquivo = ambiente.getFullPath(pastaClasses, nomeArquivo)
                File arquivo = new File(caminhoArquivo)
                arquivo.write(classe.toJson())
            }
        }
    }
}
