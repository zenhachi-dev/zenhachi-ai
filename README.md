# zenhachi-ai

A few common configuration, tools, and patterns around spring-ai.

## Multiple chat client loader

One thing we needed on top of spring-ai, is to disable its autoconfiguration and load multiple ChatClients.
Helpful if you want to connect to multiple ollama servers or multiple OpenAI-compatible APIs.

### Usage

To add the maven dependencies, we are using the same versions as spring-ai:

```xml

<dependencies>
    <dependency>
        <groupId>com.zenhachi</groupId>
        <artifactId>zenhachi-ai-ollama-spring-boot-starter</artifactId>
        <version>1.0.0-SNAPSHOT</version>
    </dependency>
    <dependency>
        <groupId>com.zenhachi</groupId>
        <artifactId>zenhachi-ai-openai-spring-boot-starter</artifactId>
        <version>1.0.0-SNAPSHOT</version>
    </dependency>
    <dependency>
        <groupId>com.zenhachi</groupId>
        <artifactId>zenhachi-ai-gemini-spring-boot-starter</artifactId>
        <version>1.0.0-SNAPSHOT</version>
    </dependency>
</dependencies>
```

Example properties for multiple clients and models:

In the following example we are setting:

- 2 ollama servers (primary and secondary)
- primary ollama server has a "reasoner" model only
- secondary ollama server has two models
- an openai-compatible localai server with stable diffusion image generator and a longer timeout
- a gemini 2.0 flash model

```properties
zenhachi.ai.ollama.primary.base-url=http://localhost:11434
zenhachi.ai.ollama.primary.chat.reasoner.model=deepseek-r1:14b

zenhachi.ai.ollama.secondary.base-url=http://10.1.5.7:11434
zenhachi.ai.ollama.secondary.chat.llama.model=llama3.1:8b-instruct-q8_0
zenhachi.ai.ollama.secondary.chat.phi.model=phi4:14b-q8_0

zenhachi.ai.openai.localai.api-key=Mandatory
zenhachi.ai.openai.localai.readTimeout=PT5M
zenhachi.ai.openai.localai.base-url=http://10.1.5.8:8080
zenhachi.ai.openai.localai.image.stableDiffusion.model=stablediffusion
zenhachi.ai.openai.localai.image.stableDiffusion.options.size=512x512

zenhachi.ai.gemini.google.api-key=Mandatory
zenhachi.ai.gemini.google.chat.gemini.model=gemini-2.0-flash
```

Then, to access the ChatModel you just need to autowire them with the name given via the property key:

```java
@Autowired
@Qualifier("reasoner")
private ChatModel deepSeekModel;

@Autowired
@Qualifier("llama")
private ChatModel llamaModel;

@Autowired
@Qualifier("phi")
private ChatModel phiModel;

@Autowired
@Qualifier("gemini")
private ChatModel geminiModel;

@Autowired
@Qualifier("stableDiffusion")
private ImageModel stableDiffusionModel;
```

Note that for now, we are copying spring-ai autoconfiguration properties classes, so many of the
configuration properties from spring-ai documentation will work, only with prefixes.

### Current support

- Multiple Ollama clients and chat models
- Multiple OpenAI clients with chat models and image models
- Configuration loaded from spring boot .properties files
- Clients and models added as beans to the context by property key as bean name
- Very basic support for gemini, while waiting for spring-ai to provide proper support

### Planned features

_(To be added as we need them in other projects)_

- Retrieving ChatClient instances by criteria rather than by name
- Multiple VectorStore support
- Client and model prioritisation and fallback
- Programmatic management (to allow users to configure clients and models from a UI)
- Observability support
- ToolCallingManager support
- Ollama model pulling support
- Embedding models support