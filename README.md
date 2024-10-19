# Job Offers Fetcher - Junior Java Developer

Aplikacja Spring Boot, która pobiera oferty pracy dla młodszych programistów Java z zewnętrznego serwera, zapisuje je w bazie danych MongoDB i udostępnia punkty końcowe, aby użytkownicy mogli uzyskać dostęp do tych ofert. Aplikacja wykorzystuje JWT do uwierzytelniania.

## Spis treści

- [Funkcjonalności](#funkcjonalności)
- [Użyte technologie](#użyte-technologie)
- [Punkty końcowe API](#punkty-końcowe-api)
- [Proces schedulera](#proces-schedulera)
- [Przebieg uwierzytelniania](#przebieg-uwierzytelniania)

## Funkcjonalności

- Pobieranie ofert pracy z zewnętrznego serwera HTTP.
- Zapisywanie i przechowywanie ofert pracy w MongoDB.
- Udostępnianie REST API umożliwiającego dostęp do ofert pracy.
- Uwierzytelnianie za pomocą JWT w celu zabezpieczenia punktów końcowych.
- Automatyczne pobieranie ofert pracy przy użyciu schedulera.

## Użyte technologie

- **Java 17**
- **Spring Boot**
- **Spring Security** (do uwierzytelniania JWT)
- **MongoDB** (do przechowywania ofert pracy)
- **Scheduler** (do okresowego pobierania nowych ofert pracy)

## Punkty końcowe API

### Uwierzytelnianie

- **POST** `/register`
  - Rejestracja nowego użytkownika za pomocą `username` i `password`.

- **POST** `/token`
  - Generowanie tokenu JWT po podaniu poprawnych danych logowania.

### Oferty pracy

- **GET** `/offers`
  - Pobranie wszystkich dostępnych ofert pracy (wymaga ważnego tokenu JWT).
  
- **GET** `/offers/{id}`
  - Pobranie konkretnej oferty pracy po jej ID (wymaga ważnego tokenu JWT).

## Proces schedulera

Aplikacja cyklicznie pobiera oferty pracy z zewnętrznego serwera HTTP przy użyciu schedulera. Oto typowy przebieg procesu:

1. **Stan początkowy**: Na zewnętrznym serwerze nie ma żadnych ofert pracy.
2. **Pierwsze uruchomienie schedulera**: Scheduler wykonuje zapytanie GET do zewnętrznego serwera, ale nie znajduje ofert do dodania do bazy danych.
3. **Pojawiają się nowe oferty**: Na zewnętrznym serwerze pojawiają się 2 nowe oferty. Przy kolejnym uruchomieniu schedulera, system dodaje te oferty do bazy danych.
4. **Kolejne uruchomienia schedulera**: Dodatkowe oferty pracy są pobierane i zapisywane w miarę ich pojawiania się na serwerze zewnętrznym.

## Przebieg uwierzytelniania

1. Użytkownik próbuje uzyskać token JWT, wysyłając zapytanie `POST /token` z nieprawidłowymi danymi logowania → **Odpowiedź: UNAUTHORIZED (401)**.
2. Użytkownik próbuje uzyskać oferty pracy bez tokenu JWT → **Odpowiedź: UNAUTHORIZED (401)**.
3. Użytkownik rejestruje się przez `POST /register` → **Odpowiedź: OK (200)**.
4. Użytkownik żąda tokenu JWT podając poprawne dane logowania → **Odpowiedź: OK (200)** i otrzymuje `jwtToken=AAAA.BBBB.CCC`.
5. Użytkownik pobiera oferty pracy z tokenem JWT w nagłówku (`Authorization: Bearer AAAA.BBBB.CCC`) → **Odpowiedź: OK (200)** z ofertami pracy.
6. W miarę uruchamiania schedulera, liczba ofert pracy rośnie w miarę dodawania nowych danych.

### Szczegółowe kroki:

```plaintext
Krok 1: Na zewnętrznym serwerze HTTP brak ofert pracy.

Krok 2: Scheduler uruchomiony pierwszy raz, GET z serwera zewnętrznego, 0 ofert dodanych do bazy danych.

Krok 3: Użytkownik wysłał POST /token z nieprawidłowymi danymi logowania → UNAUTHORIZED (401).

Krok 4: Użytkownik wysłał GET /offers bez tokenu JWT → UNAUTHORIZED (401).

Krok 5: Użytkownik zarejestrował się przez POST /register → OK (200).

Krok 6: Użytkownik wysłał POST /token z poprawnymi danymi logowania → OK (200), zwrócono token JWT.

Krok 7: Użytkownik wysłał GET /offers z poprawnym tokenem JWT → OK (200), 0 ofert zwróconych.

Krok 8: Na zewnętrznym serwerze HTTP pojawiły się 2 nowe oferty.

Krok 9: Scheduler uruchomiony drugi raz, GET z serwera zewnętrznego, 2 oferty (ID: 1000, 2000) dodane do bazy danych.

Krok 10: Użytkownik wysłał GET /offers z poprawnym tokenem JWT → OK (200), zwrócono 2 oferty o ID 1000 i 2000.

Krok 11: Użytkownik wysłał GET /offers/9999 → NOT_FOUND (404).

Krok 12: Użytkownik wysłał GET /offers/1000 → OK (200), zwrócono ofertę o ID 1000.

Krok 13: Na zewnętrznym serwerze HTTP pojawiły się 2 nowe oferty.

Krok 14: Scheduler uruchomiony trzeci raz, GET z serwera zewnętrznego, 2 oferty (ID: 3000, 4000) dodane do bazy danych.

Krok 15: Użytkownik wysłał GET /offers z poprawnym tokenem JWT → OK (200), zwrócono 4 oferty (ID 1000, 2000, 3000, 4000).
