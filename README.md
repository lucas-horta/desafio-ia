# desafio-ia

### Desenvolvimento
A aplicação foi desenvolvida como parte da entrevista técnica para Desenvolvedor de Software no Instituto Atlântico, Fortaleza-CE.  
Foi utilizada a IDE IntelliJ IDEA 2021.2, Java SDK 11.  
Foi utilizada uma imagem docker do redis para o cache de sessão e a persistência de dados da aplicação durante todo o processo de desenvolvimento.  

### Docker
A imagem da aplicação foi criada através do plugin spring-boot do maven (build-image).  
A imagem do redis é construída através do dockerfile presente em ./redis.  
Os containers são criados e linkados utilizando o ./docker-compose.yaml.  
A imagem do projeto está no dockerhub nesse repositório: [link](https://hub.docker.com/repository/docker/lucashorta/desafio-ia)

### Execução
As portas 8080 e 6379 no localhost devem estar livres para teste da aplicação.  
Para execução do projeto, o banco de dados redis deve estar funcionando em localhost:6379, localmente ou em container.  
O docker-compose do projeto sobe um container tanto com a imagem da aplicação quanto a imagem do redis.  

### REST API
Um pacote de chamadas do Postman para os endpoints está presente em ./chamadas API.json