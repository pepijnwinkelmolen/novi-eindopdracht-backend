Installatiehandleiding TochGevonden<br/>

1. Inleiding<br/>

Welkom bij de installatiehandleiding van TochGevonden. Deze RESTful API stelt je in staat om te registreren, in te loggen, nieuwe advertenties op te zetten, op advertenties te bieden, verschillende functionaliteiten te bekijken en te zoeken op basis van ingevulde informatie.<br/>
In deze handleiding lees je hoe je de API installeert, configureert en gebruikt, inclusief uitleg over de beschikbare endpoints en de bijbehorende Postman-collectie.<br/>

2. Inhoudsopgave<br/>

Inleiding<br/>
Inhoudsopgave<br/>
Vereisten en installatie<br/>
Overzicht projectstructuur<br/>
Authenticatie en autorisatie<br/>

3. Vereisten en installatie<br/>

Systeemvereisten<br/>
JDK 17<br/>
Intellij en Webstorm<br/>
Node.js (v22+)<br/>
PostgreSQL (v17+)<br/>
pgAdmin 4 v8<br/>
Postman (laatste versie)<br/>

Installatie (lokaal)<br/>
Backend<br/>
Je pakt de bijgevoegde ZIP uit in je eigen Intellij projects folder.<br/>
Vervolgens maak je met pgAdmin een database aan en onthoud de naam.<br/>
Hierna verander je binnen de properties.application de volgende dingen:<br/>
spring.datasource.url=jdbc:postgresql://localhost:5432/*HIER JE DATABASE NAAM*<br/>
spring.datasource.username=*JE PGADMIN USERNAME*<br/>
spring.datasource.password=*JE PGADMIN PASSWORD*<br/>
Vervolgens draai je de applicatie in Intellij. Kijk hierbij even of de uploads folder (directory) binnen Intellij wordt aangemaakt in de eerste laag van het project.<br/>
Mocht dit niet zo zijn, maak deze zelf vervolgens eerst aan.<br/>
Hierna voeg je de bijgevoegde foto’s aan de uploads folder toe.<br/>
Frontend<br/>
Als het goed is heb je een package.json meegekregen. Hier staan alle benodigde dependencies in. Om deze te installeren typ je in de terminal “npm install”.<br/>
Vervolgens draai je de applicatie door in de terminal “npm run dev” te typen.<br/>

Als alles is voltooid kun je nu via Postman de backend benaderen en via de link in de terminal van Webstorm de bijbehorende website bezoeken.<br/>

4. Overzicht projectstructuur<br/>
   
Intellij:<br/>

![projectstructuur intellij](https://github.com/user-attachments/assets/51625aa7-69fb-48eb-b516-90967c722760)<br/>



Webstorm:<br/>

![projectstructuur webstorm](https://github.com/user-attachments/assets/22b695ee-2e1e-407f-97f6-713fd44f365d)<br/>



5. Authenticatie en autorisatie<br/>

Er zijn in deze applicatie 3 rollen:<br/>
Bezoeker<br/>
Gebruiker<br/>
Administrator<br/>

Als bezoeker hoef je geen account te hebben. De andere twee rollen moeten dat wel. <br/>

Gebruikersaccount:<br/>
username: testuser<br/>
password: password<br/>

Administratoraccount:<br/>
username: testadmin<br/>
password: password<br/>


Mocht je van plan zijn om de applicatie met Postman te gebruiken, zul je eerst moeten inloggen. Het token dat aangemaakt wordt moet worden opgeslagen en deze zul je vervolgens meegeven aan bepaalde endpoints die autorisatie hebben.<br/>
In de collectie die meegestuurd is, wordt een lokale variabele, {{jwt}}, gebruikt. Deze zal in de nodige plekken toegevoegd zijn, dus hoef je deze alleen zelf aan te maken.<br/>
