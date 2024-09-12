<h1>Sistema de Gerenciamento de Tarefas</h1>

 <h2>Descrição</h2>
    <p>
        Este é um sistema de gerenciamento de listas e itens que permite aos usuários criar, gerenciar e organizar suas listas, bem como os itens associados a elas. A aplicação suporta a criação de listas e itens, a recuperação de todas as listas e itens, a busca de listas por ID, e a atualização e exclusão de itens. Itens prioritários podem ser destacados, e a aplicação inclui uma API para facilitar a interação com o sistema. Os dados são armazenados de forma persistente em um banco de dados relacional.
    </p>

 <h2>Funcionalidades</h2>
    <ul>
        <li><strong>Criação de Listas:</strong> Permite criar e gerenciar listas, com cada lista contendo uma coleção de itens.</li>
        <li><strong>Gerenciamento de Itens:</strong> Dentro de cada lista, os usuários podem adicionar, editar, remover e alterar o estado dos itens.</li>
        <li><strong>Visualização e Filtragem:</strong> Exibe listas e itens, com opções de filtragem.</li>
        <li><strong>Prioridade de Itens:</strong> Itens podem ser destacados para indicar prioridade.</li>
        <li><strong>Validação de Dados:</strong> Itens devem ter um título com comprimento mínimo e status válido.</li>
        <li><strong>Estado dos Itens:</strong> Cada item tem um estado que pode ser modificado pelo usuário.</li>
        <li><strong>Persistência de Dados:</strong> As listas e itens são armazenados de forma persistente.</li>
        <li><strong>API RESTful:</strong> A aplicação fornece endpoints para as principais operações, incluindo criação e busca de listas e itens, consulta de listas por ID, atualização e exclusão de itens, além de atualização do status e destaque dos itens.</li>
        <li><strong>Testes Automatizados:</strong> Foram implementados testes nos controladores para validar funcionalidades principais.</li>
    </ul>

  <h2>Tecnologias Utilizadas</h2>
    <ul>
        <li>Java 17</li>
        <li>Spring Boot 3</li>
        <li>H2 Database (para desenvolvimento e testes)</li>
        <li>JPA / Hibernate (para persistência de dados)</li>
        <li>Maven (para gerenciamento de dependências)</li>
        <li>JUnit 5 (para testes automatizados)</li>
        <li>Postman (para testar os endpoints da API)</li>
    </ul>

  <h2>Requisitos</h2>
    <ul>
        <li>JDK 17+</li>
        <li>Maven 3.8+</li>
        <li>Postman (opcional, para testar os endpoints da API manualmente)</li>
    </ul>

  <h2>Configuração do Ambiente de Desenvolvimento</h2>
    <p>Clone o repositório:</p>
    <pre width="2" height="2"><code>git clone https://github.com/JaineSantos0/sgtarefas.git</code></pre>

  <p>Compile e rode a aplicação:</p>
    <pre><code>mvn clean install</code></pre>
    <pre><code>mvn spring-boot:run</code></pre>

  <p>Acessando o Console H2:</p>
    <ul>
        <li>URL: <a href="http://localhost:8080/h2-console">http://localhost:8080/h2-console</a></li>
        <li>JDBC URL: jdbc:h2:mem:devdb</li>
        <li>Username: sa</li>
        <li>Password: (deixe vazio)</li>
    </ul>

  <h2>Configuração do Ambiente de Teste</h2>
    <p>Clone o repositório:</p>
    <pre><code>git clone https://github.com/JaineSantos0/sgtarefas.git</code></pre>

  <p>Compile e rode a aplicação:</p>
    <pre><code>mvn clean install -DskipTests</code></pre>
    <pre><code>mvn spring-boot:run -Dspring.profiles.active=test</code></pre>

  <p>Acessando o Console H2:</p>
    <ul>
        <li>URL: <a href="http://localhost:8080/h2-console">http://localhost:8080/h2-console</a></li>
        <li>JDBC URL: jdbc:h2:mem:testdb</li>
        <li>Username: sa</li>
        <li>Password: (deixe vazio)</li>
    </ul>

  <h2>Endpoints Principais</h2>
    <h3>Task</h3>
    <ul>
        <li><strong>POST /tasks</strong> - Cria uma nova lista de tarefas.</li>
        <li><strong>GET /tasks</strong> - Retorna todas as listas de tarefas com paginação.</li>
        <li><strong>GET /tasks/{id}</strong> - Retorna uma lista de tarefas específica pelo ID.</li>
    </ul>

  <h3>Item</h3>
    <ul>
        <li><strong>POST /tasks/{taskId}/items</strong> - Adiciona um novo item a uma lista de tarefas.</li>
        <li><strong>PUT /items/{id}</strong> - Atualiza um item existente.</li>
        <li><strong>DELETE /items/{id}</strong> - Remove um item.</li>
        <li><strong>PATCH /items/status/{id}</strong> - Atualiza o status de um item.</li>
        <li><strong>PATCH /items/highlighted/{id}</strong> - Atualiza o destaque de um item.</li>
        <li><strong>GET /items</strong> - Retorna todos os itens ordenados por destaque com filtragem opcional por status.</li>
    </ul>

  <h2>Como Rodar os Testes</h2>
    <p>Executar testes automatizados:</p>
    <pre><code>mvn test</code></pre>

  <h2>Documentação da API</h2>
    <p>Após rodar a aplicação, você pode acessar a documentação dos endpoints gerada pelo Swagger:</p>
    <ul>
        <li>URL: <a href="http://localhost:8080/swagger-ui.html">http://localhost:8080/swagger-ui.html</a></li>
      <li>Acessandp src/main/resources/static.</li>
    </ul>

  <h2>Estrutura do Projeto</h2>
    <pre><code>
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── sgtarefas/
│   │   │       ├── controllers/
│   │   │       │   ├── handlers/ 
│   │   │       ├── dto/
│   │   │       ├── entities/
│   │   │       ├── repositories/
│   │   │       ├── services/
│   │   │       │   └── exceptions/
│   │   │       └── SgtarefasApplication.java
│   │   ├── resources/
│   │   │   ├── application.properties
│   │   │   └── application-dev.properties
│   ├── test/
│   │   ├── java/
│   │   │   └── sgtarefas/
│   │   │       ├── controllers/
│   │   │       └── SgtarefasApplicationTests.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── application-test.properties
    </code></pre>

  <h2>Autor</h2>
    <p><strong>Nome do Autor:</strong> Jaine Santos</p>
    <p><strong>Email:</strong> jainejosiane@gmail.com</p>
