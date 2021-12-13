# B-Web-Projekt
Web-Backend Semesterprojekt

## Voraussetzungen
* Java(11) Installiert

## Commands
**DB und ersten User erstellen:**
```
create database projekt;
create user 'projektuser'@'%' identified by 'password';
grant all on projekt.* to 'projektuser'@'%';
```

## DB Sachen

```
xampp starten mysql starten, 
konsole über xampp starten, dann:
> mysql -u root -p
falls keine Datenbank ausgewählt: 
MariaDB[(none)]> use projekt;
```

## Production-ready machen

Alle Rechte an der DB entfernen
```
mysql> revoke all on projekt.* from 'projektuser'@'%';
```
Notwendige Rechte wieder hinzufügen
```
mysql> grant select, insert, delete, update on db_example.* to 'projektuser'@'%';
```
  
## Sonstiges

### Überlegungen: 
* bei Auctions: zusätzlicher "open/closed" Parameter, oder übers Datum?
* ~~Offers: Zuordnung zu Auctions über DB oder im Frontend über ID?~~
* Files in Struktur speichern (Profilbilder getrennt von Icons usw)
* Zugriffsverwaltung Files
* Ersatz für _userId_ (bei Offers, Auktionen usw)-> Sicherheit
* Individueller ID-Counter pro Table
* __Tests!!__


## nützliche links
[spring.io mysql guide](https://spring.io/guides/gs/accessing-data-mysql/)  
[spring.io fileupload guide](https://spring.io/guides/gs/uploading-files/)
