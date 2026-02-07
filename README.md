Autoflex Inventory Challenge

Este projeto consiste em um sistema para gerenciamento de produtos e matérias-primas com sugestão de produção baseada no estoque disponível. O objetivo é permitir o cadastro de produtos, cadastro de matérias-primas, associação entre produtos e materiais e o cálculo da melhor estratégia de produção considerando o estoque disponível e priorizando produtos de maior valor.

O sistema foi desenvolvido utilizando Java com Spring Boot no backend e React no frontend, seguindo uma arquitetura simples e organizada para facilitar a manutenção e evolução do projeto.

Tecnologias utilizadas

Backend: Java 17, Spring Boot, Spring Data JPA, PostgreSQL, Maven, JUnit 5 e Mockito.
Frontend: React, Vite, Axios e TailwindCSS.

Funcionalidades

O sistema permite cadastrar produtos, cadastrar matérias-primas, associar matérias-primas aos produtos informando a quantidade necessária para produção, visualizar os dados cadastrados através da interface web e calcular a sugestão de produção com base no estoque disponível. O cálculo prioriza automaticamente os produtos de maior valor, garantindo melhor aproveitamento do estoque.

Fluxo da aplicação

A aplicação é dividida em três telas principais.

A tela Raw Materials é responsável pelo gerenciamento das matérias-primas, permitindo o cadastro de novos materiais com código, nome e quantidade em estoque, além da visualização e remoção dos materiais cadastrados. A quantidade em estoque é utilizada posteriormente no cálculo da produção.

A tela Products é responsável pelo gerenciamento dos produtos, permitindo o cadastro de produtos com código, nome e preço, a associação de matérias-primas aos produtos informando a quantidade necessária para produção, a visualização dos materiais associados e a remoção de produtos. Um produto pode possuir uma ou mais matérias-primas associadas.

A tela Production é responsável pelo cálculo da sugestão de produção. Nela é possível calcular a quantidade possível de produção para cada produto com base no estoque disponível, priorizando automaticamente os produtos de maior valor e exibindo a quantidade possível de produção e o valor total gerado. O cálculo não altera o estoque no banco de dados, sendo apenas uma simulação baseada nos dados atuais.

Como executar o projeto

Para executar o backend, acessar a pasta do projeto backend e executar o comando:

mvn spring-boot:run ou ./mvn spring-boot:run 

A aplicação ficará disponível em http://localhost:8080
.

O projeto utiliza PostgreSQL como banco de dados. É necessário criar previamente um banco chamado autoflexdb e configurar as credenciais no arquivo application.properties com a URL, usuário e senha do banco.

Para executar o frontend, acessar a pasta do frontend, instalar as dependências e iniciar o projeto:

npm install
npm run dev

O frontend ficará disponível em http://localhost:5173
.

Como testar o sistema

Primeiro, acessar a tela Raw Materials e cadastrar matérias-primas com quantidade em estoque. Em seguida, acessar a tela Products e cadastrar produtos. Depois disso, associar matérias-primas aos produtos informando a quantidade necessária para produção. Por fim, acessar a tela Production e clicar em Calculate Production para visualizar a sugestão de produção baseada no estoque disponível.

Exemplo de teste

Ferro com estoque 200 e Plástico com estoque 100. Produto A com preço 100 consumindo 50 de Ferro e Produto B com preço 200 consumindo 100 de Ferro. O resultado esperado é que o Produto B seja priorizado por possuir maior valor e o estoque restante seja utilizado para produzir o Produto A.

Testes

Os testes unitários foram implementados utilizando JUnit 5 e Mockito. Os testes validam principalmente a regra de negócio responsável pelo cálculo de produção, garantindo que os produtos sejam priorizados corretamente pelo maior valor e que o consumo do estoque seja realizado de forma adequada.

Os testes podem ser executados com o comando:

mvn test ou ./mvn test

Regra de negócio principal

O cálculo de produção ordena os produtos pelo maior preço e, em seguida, calcula quantas unidades podem ser produzidas de acordo com o estoque disponível das matérias-primas. O estoque é consumido apenas em memória durante o cálculo e o sistema retorna a sugestão contendo o nome do produto, quantidade possível de produção e valor total gerado.

Autora

Ana Júlia Medeiros Faustino
Desenvolvedora Full Stack
