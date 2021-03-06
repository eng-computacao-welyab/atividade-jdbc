# Universidade Federal do Maranhão - UFMA

```
Bacharelado Interdisciplinar em Ciência e Tecnologia
Engenharia da Computação
Disciplina: Paradigmas de programação
Professor: Sérgio Souza Costa
```

## O que este projeto faz

* Cria um DAO para uma POJO de "Filme" com alguns métodos para interação com o banco de dados
  * Também define um DAO genérico para definição de métodos comuns de um CRUD
* Implementa uma estrutura de banco de dados
* Cria um formulário em Java Swing para cadastro de filmes na base de dados utilizando `GridBagLayout`
* Implementa uma `JTable` cujo o `TableModel` é implementado internamente usando `@Annotations` para definição dos valores que devem ser apresentados na tabela.
* Implementa um micro framework para construção de queries SQL `SELECT`

## Decisões de implementação

* Foi implementado um mecanismo rudimentar para inversão de controle e injeção de dependências
* Foi implementado micro framework muito simles para construção de consultas SQL

## Tecnologias uitilizadas

* Apache Maven como ferramenta de build
* Java 17
* Java Swing para interface gráfica
* HSQL para banco de dados relacional embutido
* HikariCP para pool de conexões
* Google Guava para rotinas auxiliares
* Apache Commons IO para rotinas auxiliares
* Log4J para logs

## Estrutura de pacotes

```
dev.welyab.bict.paradigmas.atividadejdbc -- pacote raiz
  | core.entities -- definição do modelo de dados
  | core.services -- definição dos serviços realizados pela aplicação
  | application -- implementação do que está definido no pacote 'core'
  | gui -- construção de interfaces gráficas baseadas em Java Swing
    | config -- configurações diversas
      | database -- conexão com o banco de dados da aplicação
      | ioc -- uma implementação rudimentar de IoC
    | exception -- exceções lançadas pela aplicação
    | repository.dao -- implementação de DAOs para acesso a dados
    | services -- implementação dos serviços definidos em 'core.services'
```
## Layout do formulário de cadastro de filmes

O layout utiliza `GridBagLayout`. As linhas e colunas foram definidas como segue:

<p align="center">
  <img src="layout-gui-cadastro-filmes.jpg">
</p>

## Executando o projeto

Este projeto não possui mecanismos para executar de forma isolada. A forma mais indicada é abrir em uma IDE, como
Eclipse ou IntelliJ, ou qualquer outro ambiente com suporte o Maven.

Em todo caso, é possível executar em linha de comando a classe `Main`. Este classe foi pensada para servir como teste de
funcionalidades. O comando em mavem para linha de comand é algo parecido com o que segue:

```
$ mvn compile exec:java -Dexec.mainClass="dev.welyab.bict.paradigmas.atividadejdbc.MainDatabaseTest"
```

O resultado é algo parecido com o seguinte:

```
Movie{id='134db232-5eae-42ff-982d-de878bbefabe', name='Clube da Luta', year=1999, imdbScore=8.8, imdbUrl='https://www.imdb.com/title/tt0137523/'}
Movie{id='219b0b85-1917-46f5-94ca-a046fdafd003', name='Parasita', year=2019, imdbScore=8.5, imdbUrl='https://www.imdb.com/title/tt6751668/'}
...
Movie{id='e95d836e-86a7-4daf-8e0f-895767e8e01f', name='O Cavaleiro das Trevas', year=2008, imdbScore=9.0, imdbUrl='https://www.imdb.com/title/tt0468569/'}
Movie{id='fb08d7a9-b52d-48f8-8309-01bd49412369', name='Bacurau', year=2019, imdbScore=8.5, imdbUrl='https://www.imdb.com/title/tt2762506/'}
```

## Outras informações

* Este projeto não implementa nenhum mecanismo de testes unitários