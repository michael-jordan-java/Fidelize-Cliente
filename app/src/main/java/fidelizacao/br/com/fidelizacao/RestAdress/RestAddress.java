package fidelizacao.br.com.fidelizacao.RestAdress;

/**
 * Created by Stefanini on 25/04/2017.
 */

public class RestAddress {
    //Endereco base da API
    public static final String URL = "http://172.18.36.57:8080/Fidelizacao";

    //Endereços de recursos da API
    public static final String LOGIN = URL + "/rest/cliente/logar";
    public static final String CADASTRAR_CLIENTE = URL + "/rest/cliente";
    public static final String BUSCAR_PROGRAMA_FIDELIZACAO = URL + "/rest/programaFidelizacao/buscarProgramaFidelizacao";
    public static final String CADASTRAR_FIDELIZACAO = URL + "/rest/fidelizacao";
    public static final String BUSCAR_QTD_FIDELIZACAO = URL + "/rest/fidelizacao/qtdCompra/cliente/";
}
