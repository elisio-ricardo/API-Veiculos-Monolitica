# API de Veiculos Back-End desenvolvida em Java Arquitetura Monolitica

Esta API foi criada como parte da avaliação para empresaTinNova.

Onde Sua principal função é gerar um CRUD de veiculos e fazer filtros dos veiculos, como quantidade por marca, quantidade por decada,
filtro por marca, ano e cor.

# Banco de Dados

Foi utilizado o banco de dados em Memoria h2

http://localhost:8080/h2-console

 username: sa
 
 password: password

# Documentação Swagger

http://localhost:8080/swagger-ui/index.html

# Rotas para teste no postman ou similar

## Rota para post/getAll
localhost:8080/veiculo

## Rota para getAll com os filtros

Esses parametros são opcionais, porem o filtro só é feito quando enviar todos os tres, se faltar algum sera feito um getAll sem filtro.

localhost:8080/veiculo?marca={marca}&ano={ano}&cor={cor}

## Rota para getFindById / delete / put / patch 

localhost:8080/veiculo/{id}

## Rota para executar o filtro de quantidade não vendida

localhost:8080/veiculo/naovendido


## Rota para executar o filtro de quantidade de veiculos por cada fabricante

localhost:8080/veiculo/marca


## Rota para executar o filtro de quantidade de veiculos por cada decada

localhost:8080/veiculo/decada

## Rota para executar o filtro de quantidade de veiculos adicionada no ultima semana

localhost:8080/veiculo/ultimasemana



