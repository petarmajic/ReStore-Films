# ReStore-Films

>Ovaj projekt rezultat je timskog rada u sklopu projeknog zadatka kolegija [Programsko inženjerstvo](https://www.fer.unizg.hr/predmet/proinz) na Fakultetu elektrotehnike i računarstva Sveučilišta u Zagrebu. 
>
>Cilj projekta je razviti aplikaciju koja upravlja digitalizacijom arhivskih filmskih materijala, optimizirajući proces grupiranja, digitalizacije i praćenja materijala.
>
>Korisnicima ReStore-Films aplikacije omogućit ćemo intuitivno sučelje za jednostavno korištenje aplikacije te precizno i točno evidentiranje zahtjeva za digitalizaciju filmskih materijala.
>
>Spremni smo riješiti neefikasno korištenje postojećih resursa za digitalizaciju. Pružit ćemo bolju dostupnost i očuvanje arhivskih filmova te osigurati očuvanje kulturne baštine i filmske industrije.
>
>Kroz rad na ovom projektu želimo unaprijediti svoje znanje o projektiranju i izgradnji složenih aplikacija, upravljanju bazama podataka, radu s API-jem i optimizacijom procesa, autentifikaciji putem OAuth2, dokumentiranju svih koraka tijekom timskog rada te procesu deploya aplikacije.


# Funkcijski zahtjevi

> 
>1. Aplikacija podržava čitanje bar koda s uređaja za bar kod čitanje.
>2. Na temelju očitanog bar koda, aplikacija prepoznaje film i učitava metapodatke povezane s tom kutijom.
>3. Ako je spajanje na bazu moguće, aplikacija preuzima podataka direktno putem API-ja, inače preuzima iz XML datoteke.
>4. Nakon preuzimanja podataka, aplikacija omogućava korisnicima ručnu izmjenu i nadopunu podataka uz validaciju unosa.
>5. Aplikacija grupira arhivske snimke kako bi se optimalno iskoristio kapacitet uređaja za digitalizaciju.
>6. Aplikacija vodi evidenciju o tome koji su filmski zapisi poslani na digitalizaciju ili vraćeni s iste, kada su poslani/vraćeni i tko ih je iz skladišta iznio/unio  pri čemu se generira PDF dokument. 
>7. Korisničke uloge:
>>- Djelatnik: unosi i mijenja podatke te preuzima filmski materijal iz skladišta
>>- Djelatnik: samo pregledava podatke
>>- Voditelj digitalizacije: pregledava i mijenja sve podatke unutar sustava, generira izvještaje i statistike
>>- Administrator: upravlja korisnicima i ima potpuni pristup aplikaciji.
>8. Podaci o filmovima mogu se izvesti u PDF, XML i XLSX formatu, a izvještaji o statistici u PDF ili XLSX format.
>9. Autentifikacija korisnika prilikom registracije i prijave u aplikaciju omogućena servisom OAuth2.
>10. Aplikacija je prilagođena radu na različitim uređajima.

# Nefunkcijski zahtjevi

>1. Aplikacija omogućava istovremeni unos podataka za do 25 djelatnika.
>2. Aplikacija mora grupirati arhivske snimke do maksimalnog trajanja od 45 minuta.
>3. Vrijeme odziva prilikom učitavanja podataka iz XML datoteka ili baze podataka mora biti unutar 5 sekundi.
>4. Aplikacija osigurava da svi uneseni podaci budu trajno sačuvani i otporni na padove sustava ili gubitak veze s bazom podataka.
>5. Aplikacija je spremna na povećanje broja korisnika i količine podataka te proširenje funkcionalnosti.
>6. Korisničko sučelje intuitivno je i prilagođeno svakodnevnoj upotrebi korisnika uz jasno definirane uloge.
>7. Za kodiranje se koristi isti stil i konvencija za sve članove tima kako bi kod bio lako razumljiv i čitljiv.
>8. Sustavna dokumentacija koda osigurava da su sve funkcionalnosti i koraci projekta detaljno objašnjeni.
>9. Aplikacija omogućava integraciju sa SUBP-om i podržava formate datoteka (XML, PDF, XLSX).


# Tehnologije

- SpringBoot - backend dio aplikacije
- React.js - frontend dio aplikacije
- PostgreSQL - relacijska baza podataka


# Članovi tima 

> **Voditelj tima**:
>>- Petar Majić (petar.majic@fer.unizg.hr) - Full-stack programer, dokumentacija
>
> **Backend programeri**:
>>- Lucija Kukavica (lucija.kukavica@fer.unizg.hr)
>>- Anastazija Čelar (anastazija.celar@fer.unizg.hr)
>>- Matej Šimek (matej.simek@fer.unizg.hr)
>
> **Frontend programeri**:
>>- Zvonimir Kilić (zvonimir.kilic@fer.unizg.hr)
>>- Amalija Mustapić (amalija.mustapic@fer.unizg.hr)
>>- Leon Vuk (leon.vuk@fer.unizg.hr)

# 📝 Kodeks ponašanja [![Contributor Covenant](https://img.shields.io/badge/Contributor%20Covenant-2.1-4baaaa.svg)](CODE_OF_CONDUCT.md)
Kao studenti sigurno ste upoznati s minimumom prihvatljivog ponašanja definiran u [KODEKS PONAŠANJA STUDENATA FAKULTETA ELEKTROTEHNIKE I RAČUNARSTVA SVEUČILIŠTA U ZAGREBU](https://www.fer.hr/_download/repository/Kodeks_ponasanja_studenata_FER-a_procisceni_tekst_2016%5B1%5D.pdf), te dodatnim naputcima za timski rad na predmetu [Programsko inženjerstvo](https://wwww.fer.hr).
Očekujemo da ćete poštovati [etički kodeks IEEE-a](https://www.ieee.org/about/corporate/governance/p7-8.html) koji ima važnu obrazovnu funkciju sa svrhom postavljanja najviših standarda integriteta, odgovornog ponašanja i etičkog ponašanja u profesionalnim aktivnosti. Time profesionalna zajednica programskih inženjera definira opća načela koja definiranju  moralni karakter, donošenje važnih poslovnih odluka i uspostavljanje jasnih moralnih očekivanja za sve pripadnike zajenice.

Kodeks ponašanja skup je provedivih pravila koja služe za jasnu komunikaciju očekivanja i zahtjeva za rad zajednice/tima. Njime se jasno definiraju obaveze, prava, neprihvatljiva ponašanja te  odgovarajuće posljedice (za razliku od etičkog kodeksa). U ovom repozitoriju dan je jedan od široko prihvačenih kodeks ponašanja za rad u zajednici otvorenog koda.

# Kontribucije
>Pravila su izdvojena u CODE_OF_CONDUCT.md

# 📝 Licenca
Važeča (1)
[![CC BY-NC-SA 4.0][cc-by-nc-sa-shield]][cc-by-nc-sa]

Ovaj repozitorij sadrži otvoreni obrazovni sadržaji (eng. Open Educational Resources)  i licenciran je prema pravilima Creative Commons licencije koja omogućava da preuzmete djelo, podijelite ga s drugima uz 
uvjet da navođenja autora, ne upotrebljavate ga u komercijalne svrhe te dijelite pod istim uvjetima [Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License HR][cc-by-nc-sa].
>
> ### Napomena:
>
> Svi paketi distribuiraju se pod vlastitim licencama.
> Svi upotrijebleni materijali  (slike, modeli, animacije, ...) distribuiraju se pod vlastitim licencama.

[![CC BY-NC-SA 4.0][cc-by-nc-sa-image]][cc-by-nc-sa]

[cc-by-nc-sa]: https://creativecommons.org/licenses/by-nc/4.0/deed.hr 
[cc-by-nc-sa-image]: https://licensebuttons.net/l/by-nc-sa/4.0/88x31.png
[cc-by-nc-sa-shield]: https://img.shields.io/badge/License-CC%20BY--NC--SA%204.0-lightgrey.svg

Orginal [![cc0-1.0][cc0-1.0-shield]][cc0-1.0]
>
>COPYING: All the content within this repository is dedicated to the public domain under the CC0 1.0 Universal (CC0 1.0) Public Domain Dedication.
>
[![CC0-1.0][cc0-1.0-image]][cc0-1.0]

[cc0-1.0]: https://creativecommons.org/licenses/by/1.0/deed.en
[cc0-1.0-image]: https://licensebuttons.net/l/by/1.0/88x31.png
[cc0-1.0-shield]: https://img.shields.io/badge/License-CC0--1.0-lightgrey.svg

### Reference na licenciranje repozitorija

