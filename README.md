# Fechadura Inteligente — Iteração 2 (Construção)

## 1. Apresentação do projeto

O **Fechadura Inteligente** é uma aplicação web acadêmica desenvolvida para simular o controle de acesso de uma fechadura eletrônica. A aplicação permite que uma pessoa faça login, visualize o painel do sistema e simule as ações de bloquear e desbloquear a fechadura. O perfil administrador também pode cadastrar usuários, alterar a situação de uma conta e consultar o histórico de acessos.

**Escopo desta versão:** trata-se de uma simulação de software. O projeto não está conectado a uma fechadura física, sensor, motor, microcontrolador ou outro equipamento eletrônico. O comando de bloqueio/desbloqueio altera somente o estado lógico apresentado pela aplicação.

## 2. Objetivos e funcionalidades

As funcionalidades implementadas no código-fonte desta versão são:

- autenticar usuários por nome de usuário e senha;
- impedir o login de contas inativas ou com credenciais inválidas;
- registrar tentativas de autenticação autorizadas e negadas;
- apresentar um painel para o usuário autenticado;
- simular o bloqueio e o desbloqueio da fechadura;
- permitir ao administrador cadastrar usuários;
- permitir ao administrador ativar, desativar e excluir usuários (a conta inicial `admin` não pode ser excluída);
- permitir ao administrador consultar os registros de acesso;
- armazenar usuários e registros no banco de dados H2.

## 3. Tecnologias e dependências

| Tecnologia | Utilização no projeto |
|---|---|
| Java 17 | Linguagem e plataforma de execução |
| Spring Boot 3.4.4 | Inicialização e estrutura da aplicação |
| Maven | Gerenciamento de dependências e construção do projeto |
| Spring Web | Controladores e rotas HTTP |
| Thymeleaf | Renderização das páginas HTML no servidor |
| Spring Data JPA | Persistência das entidades |
| H2 Database | Banco de dados local em arquivo |
| BCrypt | Geração e verificação do hash das senhas |
| Jakarta Validation | Dependência de validação declarada no projeto |
| HTML e CSS | Estrutura e apresentação das telas |

As dependências estão declaradas no arquivo `pom.xml`. O Maven faz o download delas durante a atualização ou construção do projeto.

## 4. Requisitos para executar a aplicação

Antes de importar ou executar o projeto, tenha instalado:

1. **JDK 17** — é necessário um JDK, não apenas um navegador. O projeto declara Java 17 no `pom.xml`.
2. **Eclipse IDE** — uma edição com suporte a Java e Maven. O Eclipse IDE for Java Developers com a integração Maven (m2e), ou o Eclipse IDE for Enterprise Java and Web Developers, pode ser utilizado.
3. **Maven 3.8 ou superior**, caso queira executar os comandos pelo terminal. No Eclipse, também é possível usar a integração Maven.
4. **Navegador atualizado** — para acessar a aplicação local.

Para conferir as instalações pelo Prompt de Comando/terminal:

```bash
java -version
mvn -version
```

O primeiro comando deve indicar Java 17. O segundo confirma que o Maven está disponível no terminal. Este repositório não contém Maven Wrapper (`mvnw`/`mvnw.cmd`); portanto, use o Maven instalado ou a integração Maven do Eclipse.

## 5. Como obter e importar o projeto no Eclipse

### 5.1 Extrair o projeto

1. Baixe o arquivo ZIP do projeto ou clone o repositório Git.
2. Se baixou o ZIP, extraia-o para uma pasta fora da pasta interna do workspace do Eclipse, por exemplo `C:\Projetos\FechaduraInteligente`.
3. Verifique se o arquivo `pom.xml` está diretamente dentro da pasta `FechaduraInteligente`.

### 5.2 Importar como projeto Maven

1. Abra o Eclipse.
2. Acesse **File > Import**.
3. Selecione **Maven > Existing Maven Projects** e clique em **Next**.
4. Em **Root Directory**, clique em **Browse** e selecione a pasta que contém o `pom.xml`.
5. Marque a caixa ao lado do projeto encontrado (`pom.xml`).
6. Clique em **Finish** e aguarde a importação.
7. Se houver dependências pendentes, clique com o botão direito no projeto e selecione **Maven > Update Project...**. Confirme em **OK** e aguarde o término.

**Se o Eclipse informar que não pode importar o projeto de uma pasta do workspace existente:** extraia ou copie o projeto para outra pasta, fora do diretório do workspace, e repita a importação. Não é necessário apagar o projeto anterior.

### 5.3 Conferir a versão do Java no Eclipse

1. Clique com o botão direito no projeto e selecione **Properties**.
2. Abra **Java Build Path > Libraries** e confira se a biblioteca do sistema utiliza Java 17.
3. Em **Java Compiler**, confira se o nível de compilação está em 17.
4. Se o JDK 17 não estiver cadastrado, acesse **Window > Preferences > Java > Installed JREs**, adicione o caminho do JDK 17 e marque-o como padrão para o workspace ou associe-o ao projeto.

Os nomes de alguns menus podem variar conforme a versão do Eclipse.

## 6. Como executar a aplicação

### 6.1 Execução pelo Eclipse

1. No **Project Explorer**, expanda o projeto.
2. Expanda `src/main/java`.
3. Expanda o pacote `br.com.fechadura`.
4. Localize `FechaduraInteligenteApplication.java`.
5. Clique com o botão direito diretamente nesse arquivo.
6. Selecione **Run As > Java Application**. Se a opção **Spring Boot App** estiver disponível, ela também pode ser utilizada.
7. Observe a aba **Console**. Aguarde a inicialização do Spring Boot e a indicação de que o servidor iniciou na porta `8080`.

A classe principal contém o método `main` que chama `SpringApplication.run(...)`.

### 6.2 Execução pelo terminal

Abra o terminal na pasta raiz do projeto, isto é, na pasta em que está o `pom.xml`, e execute:

```bash
mvn spring-boot:run
```

Para interromper a aplicação, pressione `Ctrl+C` no terminal ou utilize o botão **Terminate** no Console do Eclipse.

### 6.3 Endereços locais

Com a aplicação em execução, utilize:

- Página inicial: `http://localhost:8080/`
- Login: `http://localhost:8080/login`
- Painel (após autenticação): `http://localhost:8080/painel`
- Console do H2: `http://localhost:8080/h2-console`

A rota `/` redireciona para o login quando não existe usuário autenticado na sessão; caso exista, redireciona para o painel.

## 7. Contas iniciais para demonstração

Na inicialização, a classe `Inicializacao` solicita ao `UsuarioService` a criação das contas abaixo, caso os respectivos logins ainda não existam no banco:

| Perfil | Login | Senha inicial |
|---|---|---|
| Administrador | `admin` | `admin123` |
| Usuário de teste | `maria` | `123456` |

Essas contas são **credenciais públicas de demonstração acadêmica**. Não utilize essas senhas em sistemas reais. As senhas são armazenadas no banco como hash BCrypt; o sistema compara a senha informada com o hash durante a autenticação.

## 8. Como utilizar as funcionalidades

### 8.1 Login e logout

1. Acesse `http://localhost:8080/login`.
2. Informe o login e a senha de uma das contas de demonstração.
3. Com credenciais válidas e conta ativa, o sistema cria uma sessão e direciona para o painel.
4. Com credenciais incorretas ou conta inativa, a tela apresenta a mensagem de erro e a tentativa é registrada.
5. Utilize a opção de sair/logout na interface para encerrar a sessão.

### 8.2 Painel e simulação da fechadura

Depois do login, o painel apresenta o estado lógico da fechadura. As ações de bloquear e desbloquear são enviadas por requisições HTTP e atualizam o estado mantido pelo serviço `FechaduraService`. Cada ação é registrada no histórico. O estado inicial é **bloqueado** e, como é mantido em memória, volta a bloqueado quando a aplicação é reiniciada.

### 8.3 Administração de usuários

Entre com a conta `admin` e acesse a área de usuários pela navegação da aplicação. O administrador pode:

- cadastrar usuário informando nome, login, senha e perfil;
- ativar ou desativar uma conta;
- excluir usuários, exceto a conta administrativa inicial `admin`.

O código verifica se o login já existe e exige senha com pelo menos seis caracteres no cadastro. As rotas administrativas verificam se a sessão pertence a um usuário com perfil `ADMIN`.

### 8.4 Registros de acesso

A área de registros é destinada ao administrador. Ela apresenta eventos armazenados, como autenticações autorizadas ou negadas, bloqueio/desbloqueio simulado e ações administrativas registradas pelo sistema.

## 9. Banco de dados H2

A aplicação utiliza H2 em modo arquivo. As configurações estão em `src/main/resources/application.properties`:

| Propriedade | Valor configurado |
|---|---|
| URL JDBC | `jdbc:h2:file:./data/fechadura-db` |
| Driver | `org.h2.Driver` |
| Usuário | `sa` |
| Senha | vazia |
| Console web | habilitado em `/h2-console` |
| Porta da aplicação | `8080` |
| Atualização do esquema | `spring.jpa.hibernate.ddl-auto=update` |

Para abrir o console H2, inicie a aplicação e acesse `http://localhost:8080/h2-console`. Informe a URL JDBC exatamente como indicada acima, usuário `sa` e deixe a senha em branco; depois clique em **Connect**.

O caminho `./data` é relativo ao diretório de execução da aplicação. A pasta pode ser criada automaticamente quando o banco for inicializado. Ela contém dados locais e está excluída do Git pelo `.gitignore`. Faça cópia de segurança dos arquivos do banco se precisar preservar os dados. Não remova os arquivos enquanto a aplicação estiver em execução.

## 10. Organização do código-fonte

```text
FechaduraInteligente/
├── .gitignore
├── README.md
├── pom.xml
└── src/
    └── main/
        ├── java/br/com/fechadura/
        │   ├── config/
        │   ├── controller/
        │   ├── model/
        │   ├── repository/
        │   ├── service/
        │   └── FechaduraInteligenteApplication.java
        └── resources/
            ├── static/css/
            ├── templates/
            └── application.properties
```

### 10.1 Responsabilidade das camadas e classes

| Pacote/classe | Responsabilidade |
|---|---|
| `FechaduraInteligenteApplication` | Classe principal que inicia o Spring Boot. |
| `config/Inicializacao` | Cria as contas iniciais de demonstração quando ainda não existem. |
| `controller/LoginController` | Apresenta o login, autentica, cria a sessão e encerra a sessão. |
| `controller/PainelController` | Apresenta o painel e recebe as ações de bloqueio/desbloqueio. |
| `controller/AdministracaoController` | Controla o cadastro, a ativação/desativação, a exclusão de usuários e a consulta dos registros. |
| `model/Usuario` | Representa os dados de um usuário. |
| `model/Acesso` | Representa um evento registrado no histórico de acesso. |
| `repository/UsuarioRepository` | Interface de persistência dos usuários com Spring Data JPA. |
| `repository/AcessoRepository` | Interface de persistência e consulta dos registros de acesso. |
| `service/UsuarioService` | Contém autenticação, cadastro e operações de administração dos usuários. |
| `service/FechaduraService` | Mantém o estado simulado de bloqueio/desbloqueio em memória. |
| `service/AcessoService` | Registra e consulta eventos de acesso. |
| `resources/templates` | Páginas HTML processadas pelo Thymeleaf. |
| `resources/static/css` | Arquivos de estilo da interface. |
| `application.properties` | Configura banco de dados, console H2, Thymeleaf e porta HTTP. |

## 11. Compilação e verificação

Na pasta raiz do projeto, os comandos Maven abaixo podem ser utilizados:

**Limpar arquivos de compilação anteriores e executar os testes disponíveis:**

```bash
mvn clean test
```

**Gerar o pacote JAR:**

```bash
mvn clean package
```

O arquivo JAR é gerado dentro da pasta `target/`. Para executá-lo, use o nome do arquivo gerado, por exemplo:

```bash
java -jar target/fechadura-inteligente-1.0.0.jar
```

**Observação sobre testes:** na estrutura entregue nesta versão não foi localizada uma pasta `src/test` com classes de testes automatizados. Portanto, os comandos acima permitem compilar e solicitar a execução dos testes configurados, mas não constituem evidência de que testes funcionais foram realizados ou aprovados. Registre no relatório acadêmico somente resultados que tenham sido efetivamente verificados.

### 11.1 Roteiro de testes manuais sugerido

Execute os testes com a aplicação iniciada e registre o resultado observado (aprovado, reprovado ou não executado):

| Nº | Procedimento | Resultado esperado |
|---|---|---|
| 1 | Abrir `http://localhost:8080` sem estar autenticado. | Redirecionar para a tela de login. |
| 2 | Entrar com `admin` e `admin123`. | Abrir o painel com a sessão autenticada. |
| 3 | Tentar entrar com senha incorreta. | Exibir mensagem de credenciais inválidas e registrar a tentativa negada. |
| 4 | Entrar como administrador e abrir a área de usuários. | Exibir a lista de usuários. |
| 5 | Cadastrar um usuário de teste com senha de pelo menos seis caracteres. | Salvar o usuário e exibi-lo na lista. |
| 6 | Desativar o usuário de teste e tentar autenticar com ele. | Impedir a autenticação enquanto a conta estiver inativa. |
| 7 | Ativar novamente o usuário e autenticar. | Permitir a autenticação se as credenciais estiverem corretas. |
| 8 | No painel, acionar desbloquear e depois bloquear. | Atualizar o estado lógico exibido e registrar as ações. |
| 9 | Como administrador, abrir a área de registros. | Exibir os eventos armazenados. |
| 10 | Reiniciar a aplicação. | Manter usuários/registros no banco H2 e iniciar a fechadura no estado bloqueado. |

## 12. Solução de problemas comuns

**O Eclipse não encontra o projeto Maven:** confirme que o campo **Root Directory** aponta para a pasta que contém diretamente o `pom.xml`. Se a pasta já estiver dentro do workspace, extraia o projeto em outro diretório e importe novamente.

**O Eclipse apresenta erro de Java ou versão incompatível:** confira se o JDK 17 está instalado e selecionado em **Window > Preferences > Java > Installed JREs**. Depois confira o nível 17 em **Project > Properties > Java Compiler**.

**As dependências Maven não carregam:** verifique a conexão com a internet. No Eclipse, execute **Maven > Update Project...**. Pelo terminal, tente `mvn -U clean package`.

**A porta 8080 já está em uso:** encerre a outra aplicação que utiliza essa porta ou altere temporariamente `server.port` em `application.properties` para uma porta livre. Se alterar, acesse a aplicação usando a nova porta.

**A página não abre:** confira no Console se o Spring Boot iniciou sem erros e se a porta indicada corresponde à URL utilizada no navegador.

**Não consegue entrar com a conta inicial:** confirme se está usando `admin` / `admin123` ou `maria` / `123456`. Se os logins já existirem no banco, a rotina de inicialização não redefine suas senhas. Os dados persistidos podem estar em `data/`; faça backup antes de qualquer procedimento de limpeza.

## 13. Limitações conhecidas e melhorias futuras

- A fechadura é simulada por software; não há integração física com atuadores ou sensores.
- O estado de bloqueio/desbloqueio é mantido em memória e reinicia como bloqueado quando a aplicação é iniciada novamente.
- A autenticação e o controle de sessão foram implementados para fins acadêmicos. Esta versão não utiliza a configuração completa do Spring Security.
- O projeto pode evoluir com testes automatizados, controle de autorização mais abrangente, proteção CSRF, política de sessão, auditoria aprimorada e integração futura com hardware, caso faça parte do escopo de uma próxima iteração.

## 14. Controle de versão e publicação da Iteração 2

O repositório remoto utilizado para a entrega é:

`https://github.com/mah85/tag-v2`

Execute os comandos a seguir no terminal, dentro da pasta raiz do projeto (a mesma pasta em que está o `pom.xml`). Antes de enviar, confira os arquivos que serão incluídos:

```bash
git status
```

Se o Git ainda não foi inicializado nessa pasta:

```bash
git init
git add .
git commit -m "Entrega da Iteracao 2"
git branch -M main
```

Se o repositório remoto `origin` ainda não estiver configurado:

```bash
git remote add origin https://github.com/mah85/tag-v2.git
```

Se `origin` já existir, confira o endereço cadastrado:

```bash
git remote -v
```

Caso seja necessário corrigir o endereço:

```bash
git remote set-url origin https://github.com/mah85/tag-v2.git
```

Envie a branch principal:

```bash
git push -u origin main
```

Depois de confirmar que o commit correto está na branch `main`, crie e envie a tag solicitada pelo professor:

```bash
git tag -a v2 -m "Versao 2 - Iteracao 2"
git push origin v2
```

Se a tag `v2` já existir localmente ou remotamente, não a recrie nem a sobrescreva sem verificar o histórico e confirmar qual commit deve representar a entrega. Ao final, abra o GitHub e confira se a branch `main` e a tag `v2` estão visíveis.

## 15. Informações da versão

- Projeto: Fechadura Inteligente
- Etapa acadêmica: Construção — Iteração 2
- Linguagem: Java 17
- Framework: Spring Boot 3.4.4
- Empacotamento: JAR
- Banco de dados: H2 em arquivo
- Porta padrão: 8080
- Repositório remoto planejado para a entrega: `https://github.com/mah85/tag-v2`
