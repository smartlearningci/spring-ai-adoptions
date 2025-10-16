# Instruções rápidas

1) Coloque (ou deixe o script transferir) um ficheiro GGUF em `./models/`.
   Exemplos possíveis: `LFM2-350M.Q4_0.gguf` ou `LFM2-350M-Q4_K_M.gguf`.

2) Suba os serviços:
   docker compose up -d

3) Crie o modelo:
   ./load-model.sh

4) Teste:
   docker exec -it ollama ollama run lfm2-350m "Olá, quem és tu?"
