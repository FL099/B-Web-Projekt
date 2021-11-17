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

  
 ## Sonstiges


## nützliche links
[spring.io mysql guide](https://spring.io/guides/gs/accessing-data-mysql/)  
