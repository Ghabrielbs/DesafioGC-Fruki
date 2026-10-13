# Desafio GC Fruki
Recomendador de produtos personalizado por CNPJ, integrado ao WhatsApp, usando histórico de compras, produtos relacionados, previsão climática e datas comemorativas.

## Backend

Para rodar o backend da aplicação, siga os passos abaixo

**Passo 1**

Instale o Java 21

**Passo 2**

Usando Docker, instale o banco de dados e configure o banco de dados Postgre e o pgAdmin usandos os comandos abaixo

`docker network create fruki-network`

`docker run --name frukidb -p 5432:5432 -e POSTGRES_PASSWORD=postgres -e POSTGRES_USER=postgres -e POSTGRES_DB=fruki -d --network fruki-network postgres:17.6`

`docker run --name pgadmin4 -p 15432:80 -e PGADMIN_DEFAULT_EMAIL=admin@admin.com -e PGADMIN_DEFAULT_PASSWORD=admin --network fruki-network -d dpage/pgadmin4`

Inicie o container do banco de dados e do pgAdmin

**Passo 3**

Configureo pgAdmin para acessar o bando de dados. 

Acesse no navegador o endereço [http://localhost:15432/login](http://localhost:15432/login). Use o email _admin@admin.com_ e a senha _admin._

Clique em _Add New Server_ e insira as informações abaixo:

*   Em General/Name: fruki
*   Em Connection/Host Name: frukidb
*   Em Connection/Port: 5432
*   Em Connection/Maintenance database: fruki
*   Em Connection/Username: postgres
*   Em Connection/Password: postgres

