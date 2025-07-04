#!/bin/sh
set -e
ollama serve &
# Wait for Ollama server to be ready
until curl -sf http://localhost:11434/; do sleep 1; done
echo '🚚  pulling model …'
ollama pull llama3.2:1b
echo '✅  model ready'
# Keep Ollama running in the foreground
wait
