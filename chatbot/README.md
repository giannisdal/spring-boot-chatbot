# Spring Boot Chatbot

A simple chatbot application built with Spring Boot, demonstrating integration with an LLM (Large Language Model) backend (e.g., Ollama) and a RESTful API for chat interactions.

## Features
- REST API endpoint for chatbot conversations
- Configurable LLM backend (model, temperature, etc.)
- Message localization support
- Clean logging configuration
- Custom startup message

## Getting Started

### Prerequisites
- Java 21+
- Maven 3.8+
- An LLM backend (e.g., Ollama) running and accessible

### Configuration
Edit `src/main/resources/application.yml` to set your LLM backend URL, model, and other parameters:

```yaml
ollama:
  url: http://localhost:11434/api/generate
  model: llama3.2:1b
  stream: false
  temperature: 0.3
  num_predict: 100
  presence_penalty: 1.5

logging:
  level:
    root: INFO
    com.giannisdal.chatbot: DEBUG
```

### Build & Run

```
mvn clean package
java -jar target/chatbot-*.jar
```

You will see a custom startup message in the logs when the application is ready.

### API Usage

- **POST** `/api/chatbots`
  - Request body: `{ "request": "Your question here" }`
  - Response: `{ "response": "LLM's answer" }`

Example using `curl`:

```
curl -X POST http://localhost:8080/api/chatbots \
  -H 'Content-Type: application/json' \
  -d '{"request": "What is Spring Boot?"}'
```

#### Example Request/Response

POST http://localhost:8080/api/chatbots

Request body:
```
{
  "request": "what is quantum mechenics?"
}
```

Response body:
```
{
  "request": "what is quantum mechenics?",
  "response": "Quantum mechanics is a branch of physics that describes the behavior of matter and energy at an atomic and subatomic level, where the classical laws of physics no longer apply. At these scales, particles can exist in multiple states simultaneously and be connected in ways that defy classical notions of space and time. The study of quantum mechanics has led to numerous breakthroughs in technology, including transistors, lasers, and computer chips, and has also been applied to fields such as medicine, finance, and materials science."
}
```

### Customization
- Edit `messages.properties` for prompt templates and localization.
- Adjust logging and LLM parameters in `application.yml`.

## Project Structure
- `ChatbotApplication.java`: Main Spring Boot entry point
- `config/AiConfiguration.java`: LLM integration and configuration
- `rest/AiResource.java`: REST API controller
- `service/`: Service layer for chatbot logic
- `resources/messages.properties`: Message keys and prompt templates
- `resources/application.yml`: Main configuration

## License
MIT

