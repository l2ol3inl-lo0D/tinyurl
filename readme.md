# Application Tiny Url

## Dépendences:
- Postgesql avec un utilisateur et mot de passe configuré.
   L'application utilise les variables d'environnements: 
  - DB_USERNAME 
  - DB_PASSWORD

- La base de données sur postgres s'appelle tinydb et l'adresse utilisée pour 
   rejoindre la base de données est:
  - localhost:5432/tinydb
  
 - L'utilisateur doit avoir les droits d'écriture et lecture sur les tables et séquence de ce schéma.

## Recommandations:
- L'utilisateur doit être créé dans l'optique d'avoir un accès exclusif aux tables tinydb sans avoir accès au reste 
   des tables système et de ses fonctions.

- Il est possible d'utiliser les instructions suivantes pour configurer la base de données, 
    mais ce n'est pas obligatoire, une installation similaire personnalisé est possible.

_*Note: L'utilisateur utilise un mot de passe chiffré non présent dans le code mis en exemple._
```roomsql
CREATE ROLE dbuser WITH
LOGIN
NOSUPERUSER
INHERIT
CREATEDB
NOCREATEROLE
NOREPLICATION
NOBYPASSRLS;

CREATE SEQUENCE IF NOT EXISTS public.tiny_url_id_seq
    INCREMENT 50
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

ALTER SEQUENCE public.tiny_url_id_seq
    OWNER TO dbuser;

CREATE TABLE IF NOT EXISTS public.tiny_url
(
    tiny_url_id bigint NOT NULL,
    generated_url character varying(255) COLLATE pg_catalog."default",
    source_url character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT tiny_url_pkey PRIMARY KEY (tiny_url_id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.tiny_url
OWNER to dbuser;
```

## Exécution
- Dans cet exemple, l'utilisateur' est dbuser et le mot de passe est dbuser1.
```bash
java -DDB_USERNAME=dbuser -DDB_PASSWORD=dbuser1 -jar 'tinyurl-1.0.0-SNAPSHOT.jar'
```
_*On peut arriver au même comportement en utilisant les variables d'environnements du 
   serveur ou de l'image contenant le jar._ 

## Utilisation
### /shorten
- Faire une commande url de type **POST** avec le paramètre _url_ contenent un url valide pour recevoir 
   la version miniature de l'url original.

```bash
curl -X POST -d "url=http://www.google.com" http://localhost:8080/shorten
```
### /find
- Faire une commande url de type **GET** avec un paramètre _key_ qui contient la version miniature de l'url afin de 
    recevoir l'url originale.

_*Dans cet exemple, la clé est 8960101b10 et n'existe pas nécessairement pour vous._

```bash
curl -X GET "http://localhost:8080/find?key=8960101b10"
```