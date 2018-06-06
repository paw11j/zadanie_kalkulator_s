# zadanie_kalkulator_s

Aplikacja służąca do wyliczania miesięcznego zarobeku netto w PLN na kontrakcie w Niemczech, Wielkiej Brytanii i Polsce.

Backend aplikacji łączy się do zewnętrznego API pobierając aktualne kursy walut. Backend został przetestowany jednostkowo oraz integracyjnie. 
Dodano testy z użyciem zamokowanego serwera.

Po stronie fronetendu po wpisaniu danych przez użytkownika, są one przekazaywane do serwera, a wynik prezentowany jest użytkownikowi. 
Dodano walidację wprowadzaniej kwoty oraz zdefiniowano pola wymagane.
