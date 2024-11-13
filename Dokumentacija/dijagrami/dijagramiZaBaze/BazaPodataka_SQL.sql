CREATE TABLE FILMSKA_TRAKA
(
  IDEmisije INT NOT NULL,
  OriginalniNaslov VARCHAR(255) NOT NULL,
  RadniNaslov VARCHAR(255),
  JezikOriginala VARCHAR(255) NOT NULL,
  Ton VARCHAR(255) NOT NULL,
  Emisija VARCHAR(255),
  Porijeklo_ZemljaProizvodnje VARCHAR(50) NOT NULL,
  Licenca VARCHAR(255),
  GodinaProizvodnje INT NOT NULL,
  MarkIN LOCALTIME NOT NULL,
  MarkOUT LOCALTIME INT NOT NULL,
  Duration LOCALTIME NOT NULL,
  BrojMedija VARCHAR(50) NOT NULL,
  VrstaSadrzaja VARCHAR(100),
  PRIMARY KEY (IDEmisije)
);

CREATE TABLE STATUS_DIGITALIZACIJE
(
  IDStatusDigitalizacije INT NOT NULL,
  NazivStatusa VARCHAR(100) NOT NULL,
  PRIMARY KEY (IDStatusDigitalizacije)
);

CREATE TABLE ULOGA_KORISNIKA
(
  IDUlogeKorisnika INT NOT NULL,
  NazivUlogeKorisnika VARCHAR(100) NOT NULL,
  PRIMARY KEY (IDUlogeKorisnika)
);

CREATE TABLE KORISNIK
(
  IDKorisnika INT NOT NULL,
  Ime VARCHAR(255) NOT NULL,
  Prezime VARCHAR(255) NOT NULL,
  KorisnickoIme VARCHAR(255) NOT NULL,
  Email VARCHAR(255) NOT NULL,
  Lozinka VARCHAR(15) NOT NULL,
  IDUlogeKorisnika INT NOT NULL,
  PRIMARY KEY (IDKorisnika),
  FOREIGN KEY (IDUlogeKorisnika) REFERENCES ULOGA_KORISNIKA(IDUlogeKorisnika)
);

CREATE TABLE STATISTIKA_DIGITALIZACIJE
(
  BrojDigitaliziranih INT,
  BrojNaDigitalizaciji INT,
  IDStatistike INT NOT NULL,
  IDKorisnika INT NOT NULL,
  PRIMARY KEY (IDStatistike),
  FOREIGN KEY (IDKorisnika) REFERENCES KORISNIK(IDKorisnika)
);

CREATE TABLE GRUPA_ZA_DIGITALIZACIJU
(
  IDGrupe INT NOT NULL,
  VrijemePocetka LOCALTIMESTAMP NOT NULL,
  VrijemeZavrsetka LOCALTIMESTAMP NOT NULL,
  IDDjelatnikaIznio INT NOT NULL,
  IDDjelatnikaVratio INT NOT NULL,
  IDStatusDigitalizacije INT NOT NULL,
  PRIMARY KEY (IDGrupe),
  FOREIGN KEY (IDDjelatnikaIznio) REFERENCES KORISNIK(IDKorisnika),
  FOREIGN KEY (IDDjelatnikaVratio) REFERENCES KORISNIK(IDKorisnika),
  FOREIGN KEY (IDStatusDigitalizacije) REFERENCES STATUS_DIGITALIZACIJE(IDStatusDigitalizacije)
);

CREATE TABLE grupiranje
(
  IDEmisije INT NOT NULL,
  IDGrupe INT NOT NULL,
  PRIMARY KEY (IDEmisije, IDGrupe),
  FOREIGN KEY (IDEmisije) REFERENCES FILMSKA_TRAKA(IDEmisije),
  FOREIGN KEY (IDGrupe) REFERENCES GRUPA_ZA_DIGITALIZACIJU(IDGrupe)
);