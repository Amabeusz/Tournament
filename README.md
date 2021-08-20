# Turniej 

> Aplikacja pozwala przeprowadzić turniej (fazę grupową i turniejową) dla 32 drużyn.

## Spis treści

- [Ustawienie](#Ustawienie)
- [Technologie](#Technologie)
- [Features](#feaFeaturestures)
- [Instrukcja](#Instrukcja)

## Ustawienie

Do uruchomienia aplikacji potrzebna jest baza danych [PostgreSQL](https://www.postgresql.org/) z odpowiednimi ustawieniami umożliwiającymi połączenie w pliku [hibernate.cfg.xml](src\main\resources\hibernate.cfg.xml).

## Technologie

- Java
  
- JavaFX

- Hibernate

- PostgreSQL

## Funkcje

Aplikacja pozwala na:

- dodanie 32 drużyn (nazwa, opis, avatar), jeśli nie zostaną wybrane drużyna otrzyma domyślne wartości, drużyny są zapisywane w bazie danych

- rozlosowanie drużyn do 8 grup

- wprowadzenie wyników meczy (liczba strzelonych bramek) jednorazowo do bazy danych, w fazie grupowej w zależności od wyniku przypisywane są odpowiednio punkty, w razie równości drużyn losowanie rozstrzyga, która zajmie wyższą pozycję

- wyświetlanie listy drużyn, meczy, grup oraz drzewa fazy finałowej (listy pozwalają na otwarcie okna podglądu drużyny poprzez kliknięcie w wybraną drużynę (oprócz listy meczy))

- samoskonfigurowanie bazy danych po nawiązaniu połączenia

- wprowadzenie parametrów połączeniowych w pliku **hibernate.cfg.xml**

## Instrukcja

1.  Po uruchomieniu programu należy dodać drużyny za pomocą przycisku **Add Team**

<p  align="center">
	<img  src="https://snipboard.io/V9zIaF.jpg">
</p>

2. Drużyny można dodawać pojedynczo lub wiele na raz

<p  align="center">
	<img  src="https://snipboard.io/tUkFyI.jpg">
</p>
  
3. Po dodaniu drużyn komunikat informuje o rozlosowaniu ich do grup

<p  align="center">
	<img  src="https://snipboard.io/ubQYrN.jpg">
</p>

5. Aplikacja blokuje możliwość dodania więcej niż 32 drużyn

<p  align="center">
	<img  src="https://snipboard.io/3PlYqr.jpg">
</p>

6. W karcie **Matches** widoczne są rozegrane i jeszcze nierozegrane mecze.
Poniżej listy znajdują się inputy do wprowadzania goli zdobytych przez obydwie drużyny w danym meczu.
Wynik dla danego meczu jest dodawany jednorazowo i nie ma możliwości jego zmiany.
Przycisk **Add result** lub **Enter** wysyła wynik do bazy danych.
<p  align="center">
	<img  src="https://snipboard.io/qPyuD4.jpg">
</p>
Jeśli pola zostaną puste aplikacja o tym powiadomi.
<p  align="center">
	<img  src="https://snipboard.io/meFKdE.jpg">
</p>
Po rozegraniu wszystkich meczy aplikacja informuje o zakończeniu turnieju.
<p  align="center">
	<img  src="https://snipboard.io/i9DVxz.jpg">
</p>
<p  align="center">
	<img  src="https://snipboard.io/PIG8Fx.jpg">
</p>

7. Aplikacja umożliwia podgląd drużyn w karcie **Teams**

<p  align="center">
	<img  src="https://snipboard.io/Q71WqA.jpg">
</p>

7. Podgląd grup jest możliwy w karcie **Groups**

<p  align="center">
<img  src="https://snipboard.io/0whoKF.jpg">
</p>

8. Podgląd drzewa fazy turniejowej jest widoczny jest w karcie **Tournament tree**
<p  align="center">
<img  src="https://snipboard.io/OwDxtf.jpg">
</p>

9. W powyższych kartach możliwe jest wyświetlenie podglądu drużyny.
<p  align="center">
	<img  src="https://snipboard.io/9W5bqI.jpg">
</p>
