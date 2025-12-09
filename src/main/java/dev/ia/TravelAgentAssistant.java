package dev.ia;

import io.quarkiverse.langchain4j.RegisterAiService;

//Instrui o quarkus a gerar uma implementação dessa interface que se conecta com o modelo IA
@RegisterAiService
public interface TravelAgentAssistant {

    /**
     * O metodo chat recebe a mensagem do usuario e retorna a resposta do LLM
     * @param userMessage A mensagem do usuario
     * @return A resposta do LLM
     */

    String chat(String userMessage);

}
