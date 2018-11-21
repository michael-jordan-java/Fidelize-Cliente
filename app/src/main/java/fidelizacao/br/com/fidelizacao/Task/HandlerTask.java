package fidelizacao.br.com.fidelizacao.Task;

/**
 * Created by Stefanini on 25/04/2017.
 */

public interface HandlerTask {
    void onPreHandle();

    void onSuccess(String valueRead) throws Exception;

    void onError(Exception erro);
}
