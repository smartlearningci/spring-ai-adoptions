#!/usr/bin/env bash
set -euo pipefail

MODEL_NAME="lfm2-350m"
MODELS_DIR="./models"
HUB_REPO="LiquidAI/LFM2-350M-GGUF"

CANDIDATES=(
  "LFM2-350M-Q4_0.gguf"
)

mkdir -p "$MODELS_DIR"

GGUF_FILE=""
for f in "${CANDIDATES[@]}"; do
  if [ -f "$MODELS_DIR/$f" ]; then
    GGUF_FILE="$f"
    break
  fi
done

if [ -z "$GGUF_FILE" ]; then
  for f in "${CANDIDATES[@]}"; do
    URL="https://huggingface.co/${HUB_REPO}/resolve/main/${f}"
    echo "⬇️  A tentar transferir ${f} ..."
    if curl -L --fail -o "${MODELS_DIR}/${f}.part" "$URL"; then
      mv "${MODELS_DIR}/${f}.part" "${MODELS_DIR}/${f}"
      GGUF_FILE="$f"
      echo "✅ Transferido: ${f}"
      break
    else
      echo "⚠️  Não encontrado: ${f}"
      rm -f "${MODELS_DIR}/${f}.part" || true
    fi
  done
fi

if [ -z "$GGUF_FILE" ]; then
  echo "❌ Não foi possível obter o ficheiro .gguf. Coloque manualmente o .gguf em ./models e volte a correr."
  exit 1
fi

cat > "${MODELS_DIR}/Modelfile" <<'EOF'
FROM __GGUF_FILE__
TEMPLATE "{{ .Prompt }}"
PARAMETER temperature 0.3
EOF

sed -i "s#__GGUF_FILE__#${GGUF_FILE}#g" "${MODELS_DIR}/Modelfile"

echo "📦 A criar o modelo ${MODEL_NAME} com base em ${GGUF_FILE} ..."
docker exec -i ollama ollama create "${MODEL_NAME}" -f "/models/Modelfile"

echo "✅ Modelo criado!"
echo "Teste com: docker exec -it ollama ollama run ${MODEL_NAME} 'Olá'"
