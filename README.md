# Sistema de gerenciamento de cursos e estudantes

Este projeto é um Sistema de Gerenciamento de Cursos e Estudantes desenvolvido com Spring Boot. Ele permite realizar operações CRUD em cursos, estudantes e professores.

## Funcionalidades

- CRUD de Curso
  - Criar, ler, atualizar e deletar cursos
- CRUD de Estudante
  - Criar, ler, atualizar e deletar estudantes
- CRUD de Professor
  - Criar, ler, atualizar e deletar professores

## Regras de Negócio

- Um curso pode ter múltiplos estudantes matriculados.
- Um estudante pode estar matriculado em múltiplos cursos.
- Um curso é ministrado por um professor.
- Um professor pode ministrar múltiplos cursos.

## Tecnologias Utilizadas

- Java com Spring Boot
- Spring Data JPA para persistência
- Spring Web para criar a API REST
- Spring Test para testes unitários
- Mokito para gerar mock de dados para os testes
- MySQL como banco de dados
- SpringDoc OpenAPI para documentação da API (escolhida essa biblioteca pois ela foi compativel com a versão do Spring Boot e Java utilizados no projeto)

## Estrutura do Projeto

O projeto foi divido em camadas para manter baixo acoplamento. Essa divisão foi feita em model, repository, service e controller
- A camada de model foi responsável por abrigar as entidades (classes que representam tabelas do banco de dados)
- A camada de repository foi reresponsável pela operações no banco de dados (consulta, inserção, edição e exclusão de dados)
- A camada de service foi responsável por intermediar as requisições recebidas no controller, aplicar regras de negócios quando necessário, encaminhar ao service e devolver a resposta ao controller.
- A camada de controller foi responsável por receber as requisições feitas a API direcionar ao service e devolver a resposta, nos controllers encontramos os endpoints das funcionalidades da API.

- **Modelos:**
  - Course
  - Student
  - Teacher
  - Person (classe abstrata que fornece por herança atributos comuns a professores e estudantes)
- **Repositórios:**
  - CourseRepository
  - StudentRepository
  - TeacherRepository
- **Serviços:**
  - CourseService
  - StudentService
  - TeacherService
- **Controladores:**
  - CourseController
  - StudentController
  - TeacherController
- **Documentação e Testes:**
  - Documentação da API utilizando SpringDoc OpenAPI
  - Testes unitários para as funcionalidades

## Configuração do Projeto

### Pré-requisitos

- Java 22
- Maven
- MySQL

### Passos para Configuração

1. Clone o repositório:
    ```bash
    git clone https://github.com/seu-usuario/Course-And-Student-Management-System.git
    ```

2. Navegue até o diretório do projeto:
    ```bash
    cd Course-And-Student-Management-System
    ```

3. Configure o banco de dados MySQL:
    - Crie um banco de dados chamado `course_student_management`.
    - Atualize as credenciais de conexão no arquivo `application.properties` localizado em `src/main/resources/`.

4. Execute o projeto:
    ```bash
    mvn spring-boot:run
    ```

5. Acesse a documentação da API:
    - Abra o navegador e vá para `http://localhost:8080/swagger-ui/index.html#/`.

## Testes

Para executar os testes unitários e de integração, use o seguinte comando:
```bash
mvn test
```

## Chamadas a API via Postman

- Em `src/main/resources/static` tem um arquivo JSON que pode ser importado no Postman com algumas chamadas a API para se fazer testes.
