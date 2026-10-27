# Require4Testing

Require4Testing ist ein prototypisches webbasiertes Testmanagementsystem.
Die Anwendung ermöglicht die Verwaltung von Anforderungen, Testfällen,
Testläufen und Testergebnissen.

## Umgesetzte Funktionen

1. Anforderungen erfassen und anzeigen
2. Testfälle erstellen und einer Anforderung zuordnen
3. Testläufe erstellen und anzeigen
4. Eine Testperson und mehrere Testfälle einem Testlauf zuordnen
5. Testergebnisse mit Status und tatsächlichem Ergebnis dokumentieren

## Verwendete Technologien

- Java 17 oder neuer
- Spring Boot
- Spring MVC
- Thymeleaf
- Spring Data JPA
- Hibernate
- H2-Datenbank
- Maven

## Projektstruktur

- `model`: JPA-Entities und Ergebnisstatus
- `repository`: Datenbankzugriff mit Spring Data JPA
- `service`: Geschäftslogik und Transaktionssteuerung
- `controller`: Verarbeitung der HTTP-Anfragen
- `templates`: Thymeleaf-Webseiten
- `static/css`: Gestaltung der Benutzeroberfläche

## Anwendung starten

Unter Windows:

```powershell
.\mvnw.cmd spring-boot:run

## Datenbank

Die Anwendung verwendet eine dateibasierte H2-Datenbank:

`jdbc:h2:file:~/require4testing-db`

Die H2-Konsole ist während der Programmausführung erreichbar unter:

`http://localhost:8080/h2-console`

Die Daten bleiben nach einem Neustart der Anwendung erhalten.