#!/bin/sh
set -e
ollama serve &
# Wait for Ollama server to be ready
until curl -sf http://localhost:11434/; do sleep 1; done
echo '🚚  pulling model …'
ollama pull qwen3:0.6b
echo '✅  model ready'
# Keep Ollama running in the foreground
wait
