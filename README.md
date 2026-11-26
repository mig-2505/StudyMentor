# StudyMentor — Gerador de Questionários com Inteligência Artificial

> API REST desenvolvida com Java, Spring Boot e Spring AI para gerar questionários de múltipla escolha, registrar gabaritos temporariamente e corrigir respostas de forma automática.

## Visão geral

O **StudyMentor** é um projeto de portfólio voltado ao desenvolvimento backend e à integração de inteligência artificial em aplicações Java. O estudante informa um assunto, um nível de dificuldade e a quantidade desejada de questões. O sistema utiliza um modelo de IA para gerar o questionário estruturado e devolve apenas as perguntas e alternativas. Quando o estudante envia suas respostas, o backend recupera o gabarito armazenado e calcula o desempenho sem realizar outra chamada ao modelo.

## Funcionalidades

- Verificação do status da API.
- Geração de um exercício simples em texto com IA.
- Geração de questionários com **1 a 10 questões**, nos níveis **FACIL**, **MEDIO** e **DIFICIL**.
- Quatro alternativas por questão (A, B, C e D).
- Conversão da saída do modelo em objetos Java com Spring AI.
- Validação da estrutura das questões geradas.
- Gabarito e explicações mantidos fora da resposta pública de geração.
- Identificação de cada questionário por **UUID**.
- Armazenamento temporário dos questionários em memória.
- Correção automática com quantidade de acertos, erros, percentual e explicações.
- Validação de respostas ausentes, repetidas ou inválidas.
- Tratamento centralizado de erros HTTP.
- Testes automatizados de Service e Controller com JUnit, Mockito e MockMvc.
- Documentação interativa da API com OpenAPI e Swagger UI.

## Tecnologias

- **Java 21**
- **Spring Boot 4.1.2**
- **Spring Web MVC** e **Jakarta Bean Validation**
- **Spring AI**, integrado à API da OpenAI
- **Maven** e Maven Wrapper
- **JUnit 5**, **Mockito** e **MockMvc**
- **Springdoc OpenAPI** e **Swagger UI**
- **Git** e **GitHub**

## Arquitetura

```text
Cliente HTTP (Postman ou Swagger UI)
          |
          v
       Controller     -> recebe e valida requisições
          |
          v
        Service       -> aplica regras de negócio
         /    \
        v      v
   Spring AI  Repository
   (geração)  (quiz + gabarito em memória)
          |
          v
      DTO de resposta -> retorna dados ao cliente
```

Estrutura principal:

```text
src/
├── main/
│   ├── java/com/studymentor/
│   │   ├── config/          # OpenApiConfig
│   │   ├── controller/      # StatusController, ExercicioController,
│   │   │                    # QuizController, CorrecaoController
│   │   ├── dto/             # Objetos de entrada e saída
│   │   ├── model/           # Questões geradas e quizzes internos
│   │   ├── repository/      # QuizMemoryRepository
│   │   ├── service/         # ExercicioService, QuizService, CorrecaoService
│   │   ├── exception/       # Tratamento global e exceções próprias
│   │   └── StudymentorApplication.java
│   └── resources/
│       └── application.properties
└── test/
    └── java/com/studymentor/
        ├── service/         # CorrecaoServiceTest
        └── controller/      # CorrecaoControllerTest
```

### Fluxo de geração

1. O cliente envia assunto, dificuldade e quantidade para `POST /api/quizzes/gerar`.
2. O Controller valida o DTO de entrada e chama o Service.
3. O Service usa o Spring AI para solicitar questões estruturadas.
4. A estrutura gerada é validada e recebe um UUID.
5. O Repository guarda o quiz com o gabarito.
6. O cliente recebe somente as perguntas e alternativas, sem respostas corretas.

### Fluxo de correção

1. O cliente envia o UUID e uma resposta para cada questão a `POST /api/quizzes/corrigir`.
2. O Service recupera o questionário pelo UUID.
3. São verificadas a quantidade de respostas, as alternativas e eventuais duplicações.
4. O Java compara as respostas com o gabarito armazenado.
5. O cliente recebe acertos, erros, percentual e a explicação de cada questão.

## Pré-requisitos

- JDK 21 instalado e configurado.
- Acesso à internet para baixar as dependências Maven e chamar o modelo da OpenAI.
- Uma chave de API da OpenAI válida, com acesso e limites suficientes para as chamadas utilizadas.
- PowerShell no Windows, ou um terminal compatível em outro sistema.

## Configuração

Clone o repositório:

```bash
git clone https://github.com/SEU_USUARIO/StudyMentor.git
cd StudyMentor
```

> Substitua `SEU_USUARIO` pelo nome da conta GitHub que hospedar o projeto.

Confirme a configuração em `src/main/resources/application.properties`:

```properties
spring.application.name=studymentor
server.port=8080
spring.ai.openai.api-key=${OPENAI_API_KEY}
spring.ai.openai.chat.model=gpt-4o-mini
```

**Nunca coloque a chave real no `application.properties` versionado.** No PowerShell, configure-a apenas na sessão do terminal em que executará a aplicação:

```powershell
$env:OPENAI_API_KEY = "SUA_CHAVE_AQUI"
```

Para iniciar a aplicação no Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

Para Linux/macOS, com o Maven Wrapper executável:

```bash
./mvnw spring-boot:run
```

O servidor local utiliza, por padrão, a porta `8080`.

## Endpoints

| Método | Rota | Descrição |
|---|---|---|
| GET | `/api/status` | Confirma o funcionamento da API |
| POST | `/api/exercicios/gerar` | Gera um exercício textual usando IA |
| POST | `/api/quizzes/gerar` | Gera um quiz estruturado |
| POST | `/api/quizzes/corrigir` | Corrige as respostas do quiz |

### 1. Verificar status

**GET** `http://localhost:8080/api/status`

Exemplo de resposta:

```json
{
  "aplicacao": "StudyMentor",
  "status": "ONLINE",
  "mensagem": "API funcionando corretamente!"
}
```

### 2. Gerar exercício simples

**POST** `http://localhost:8080/api/exercicios/gerar`

```json
{
  "assunto": "Programacao Orientada a Objetos"
}
```

O resultado contém o assunto e um exercício em texto produzido pela IA.

### 3. Gerar quiz

**POST** `http://localhost:8080/api/quizzes/gerar`

```json
{
  "assunto": "Java",
  "dificuldade": "MEDIO",
  "quantidade": 3
}
```

Exemplo ilustrativo de resposta (o conteúdo real varia conforme a geração):

```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "assunto": "Java",
  "dificuldade": "MEDIO",
  "questoes": [
    {
      "id": 1,
      "enunciado": "Qual palavra-chave declara uma classe em Java?",
      "alternativas": {
        "A": "new",
        "B": "class",
        "C": "void",
        "D": "return"
      }
    }
  ]
}
```

*O exemplo acima mostra apenas uma questão para manter a documentação concisa. Uma requisição válida com `quantidade: 3` deve retornar três questões.*

**Importante:** o endpoint de geração não devolve o gabarito.

### 4. Corrigir quiz

**POST** `http://localhost:8080/api/quizzes/corrigir`

Utilize o **UUID real** retornado na geração do quiz e envie uma resposta para cada questão:

```json
{
  "quizId": "550e8400-e29b-41d4-a716-446655440000",
  "respostas": [
    { "questaoId": 1, "alternativa": "B" },
    { "questaoId": 2, "alternativa": "C" },
    { "questaoId": 3, "alternativa": "A" }
  ]
}
```

Exemplo ilustrativo de resposta:

```json
{
  "quizId": "550e8400-e29b-41d4-a716-446655440000",
  "totalQuestoes": 3,
  "acertos": 2,
  "erros": 1,
  "percentualAcertos": 66.67,
  "resultados": [
    {
      "questaoId": 1,
      "respostaAluno": "B",
      "respostaCorreta": "B",
      "acertou": true,
      "explicacao": "A palavra-chave class declara uma classe."
    }
  ]
}
```

*O exemplo da correção apresenta apenas um resultado individual de modo abreviado. A resposta real contém os resultados das três questões.*

## Documentação interativa

Com a aplicação iniciada:

- **Swagger UI:** http://localhost:8080/swagger-ui.html
- **OpenAPI JSON:** http://localhost:8080/v3/api-docs

A documentação apresenta as operações REST e permite executar requisições diretamente pelo navegador.

## Executando testes

No Windows, a partir da raiz do projeto:

```powershell
.\mvnw.cmd clean test
```

Os testes existentes cobrem cenários como:

- Correção com todas as respostas corretas.
- Correção com respostas incorretas e cálculo percentual.
- Rejeição de quiz inexistente.
- Rejeição de respostas ausentes e duplicadas.
- Respostas e códigos HTTP do endpoint de correção.

Os testes automatizados da correção utilizam mocks, evitando chamadas reais à OpenAI. Os relatórios do Maven ficam em `target/surefire-reports/`.

## Tratamento de erros

A API utiliza um tratamento centralizado de exceções. Entre os comportamentos implementados:

| HTTP | Exemplo |
|---|---|
| 400 | Requisição inválida, respostas inconsistentes ou JSON ilegível |
| 404 | Quiz não encontrado |
| 502 | Estrutura de questionário rejeitada pela validação de geração |

Erros técnicos inesperados e falhas específicas do provedor de IA ainda podem necessitar de tratamento adicional.

## Limitações conhecidas

- **Persistência temporária:** os quizzes ficam em memória e são perdidos quando a aplicação reinicia.
- **Sem autenticação:** o projeto ainda não identifica usuários ou controla acesso por estudante.
- **Qualidade do conteúdo:** a IA pode gerar erros conceituais mesmo quando a estrutura é válida.
- **Dependência externa:** a geração de questões exige acesso ao modelo e pode envolver custos e limites de uso.
- **Escalabilidade:** ainda não há expiração automática dos quizzes nem armazenamento compartilhado entre instâncias.
- **Ambiente local:** os exemplos utilizam `localhost`; o projeto não está apresentado aqui como serviço implantado na nuvem.

## Possíveis evoluções

- Persistência com PostgreSQL e Spring Data JPA.
- Autenticação e histórico individual de estudantes.
- Expiração ou limpeza automática de quizzes antigos.
- Tratamento específico de indisponibilidade, quota e erros do provedor de IA.
- Melhorias na validação do conteúdo pedagógico gerado.
- Ampliação da cobertura de testes, especialmente no fluxo de geração com IA.
- Deploy em infraestrutura de nuvem e integração contínua.

## Objetivo de aprendizado

O StudyMentor foi desenvolvido para praticar arquitetura em camadas, Java moderno, criação de APIs REST, integração de IA ao backend, validação de dados, testes automatizados e documentação de software.