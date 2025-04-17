Installatiehandleiding TochGevonden

1. Inleiding

Welkom bij de installatiehandleiding van TochGevonden. Deze RESTful API stelt je in staat om te registreren, in te loggen, nieuwe advertenties op te zetten, op advertenties te bieden, verschillende functionaliteiten te bekijken en te zoeken op basis van ingevulde informatie.
In deze handleiding lees je hoe je de API installeert, configureert en gebruikt, inclusief uitleg over de beschikbare endpoints en de bijbehorende Postman-collectie.

2. Inhoudsopgave

Inleiding
Inhoudsopgave
Vereisten en installatie
Overzicht projectstructuur
Authenticatie en autorisatie

3. Vereisten en installatie

Systeemvereisten
JDK 17
Intellij en Webstorm
Node.js (v22+)
PostgreSQL (v17+)
pgAdmin 4 v8
Postman (laatste versie)

Installatie (lokaal)
Backend
Je pakt de bijgevoegde ZIP uit in je eigen Intellij projects folder.
Vervolgens maak je met pgAdmin een database aan en onthoud de naam.
Hierna verander je binnen de properties.application de volgende dingen:
spring.datasource.url=jdbc:postgresql://localhost:5432/*HIER JE DATABASE NAAM*
spring.datasource.username=*JE PGADMIN USERNAME*
spring.datasource.password=*JE PGADMIN PASSWORD*
Vervolgens draai je de applicatie in Intellij. Kijk hierbij even of de uploads folder (directory) binnen Intellij wordt aangemaakt in de eerste laag van het project.
Mocht dit niet zo zijn, maak deze zelf vervolgens eerst aan.
Hierna voeg je de bijgevoegde foto’s aan de uploads folder toe.
Frontend
Als het goed is heb je een package.json meegekregen. Hier staan alle benodigde dependencies in. Om deze te installeren typ je in de terminal “npm install”.
Vervolgens draai je de applicatie door in de terminal “npm run dev” te typen.

Als alles is voltooid kun je nu via Postman de backend benaderen en via de link in de terminal van Webstorm de bijbehorende website bezoeken.

4. Overzicht projectstructuur
   
Intellij:

![projectstructuur intellij](https://github.com/user-attachments/assets/51625aa7-69fb-48eb-b516-90967c722760)



Webstorm:

![projectstructuur webstorm](https://github.com/user-attachments/assets/22b695ee-2e1e-407f-97f6-713fd44f365d)



5. Authenticatie en autorisatie

Er zijn in deze applicatie 3 rollen:
Bezoeker
Gebruiker
Administrator

Als bezoeker hoef je geen account te hebben. De andere twee rollen moeten dat wel. 

Gebruikersaccount:
username: testuser
password: password

Administratoraccount:
username: testadmin
password: password


Mocht je van plan zijn om de applicatie met Postman te gebruiken, zul je eerst moeten inloggen. Het token dat aangemaakt wordt moet worden opgeslagen en deze zul je vervolgens meegeven aan bepaalde endpoints die autorisatie hebben.
In de collectie die meegestuurd is, wordt een lokale variabele, {{jwt}}, gebruikt. Deze zal in de nodige plekken toegevoegd zijn, dus hoef je deze alleen zelf aan te maken.
