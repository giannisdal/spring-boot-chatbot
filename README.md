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
  "response": "Quantum mechanics is a branch of physics that describes the behavior of matter and energy at an atomic 
  and subatomic level, where the classical laws of physics no longer apply. At these scales, particles can exist in 
  multiple states simultaneously and be connected in ways that defy classical notions of space and time. The study of 
  quantum mechanics has led to numerous breakthroughs in technology, including transistors, lasers, and computer chips, 
  and has also been applied to fields such as medicine, finance, and materials science."
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

## Docker Support

### Running with Docker Compose

The application comes with Docker support, including a full setup with both the Spring Boot application and an Ollama LLM backend.

#### Prerequisites
- Docker and Docker Compose installed on your system

#### Quick Start
1. Clone this repository
2. From the root directory (containing the docker-compose.yml file), run:
   ```
   docker-compose up -d
   ```
3. This will:
   - Build and start the Ollama container with the specified LLM model
   - Build and start the Spring Boot chatbot application
   - Connect them together automatically

#### Accessing the Application
- The chatbot API will be available at: `http://localhost:8080/api/chatbots`
- Ollama service will be available at: `http://localhost:11434`

#### Environment Variables
You can configure the connection to Ollama using environment variables:
- `SPRING_OLLAMA_URL`: The URL of the Ollama API (default: http://ollama:11434/api/generate)

#### Docker Volumes
- `ollama-data`: Persists the Ollama models between container restarts

#### Maven Dependencies Caching
The Dockerfile is configured to cache Maven dependencies separately from the application build, which:
- Significantly speeds up subsequent builds
- Avoids re-downloading dependencies when only application code changes
- Ensures consistent builds across different environments

#### Changing the LLM Model
To change the LLM model used by Ollama:

1. Update the model name in `application.yml`:
   ```yaml
   ollama:
     model: llama3.2:1b  # Change this to your desired model
   ```

2. When you start the containers with `docker-compose up -d`, you'll see the following process:
   ```
   chatbot  | ========================================
   chatbot  |    🚀 Spring Boot application started!   
   chatbot  | ========================================
   ollama   | pulling manifest 
   ollama   | pulling manifest 
   ollama   | pulling 74701a8c35f6: 100% ▕██████████████████▏ 1.3 GB                         
   ollama   | verifying sha256 digest 
   ollama   | writing manifest 
   ollama   | success 
   ollama   | ✅  model ready
   ```

3. The first time you use a new model, Ollama will automatically download it, which might take a few minutes depending on your internet connection and the model size.

#### Custom Configuration
To modify configurations:
1. Edit the `application.yml` file before building
2. Or override specific parameters in the docker-compose.yml environment section

#### Rebuilding After Changes
```
docker-compose down
docker-compose --build
```
