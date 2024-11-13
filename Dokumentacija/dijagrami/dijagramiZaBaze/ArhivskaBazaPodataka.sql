CREATE TABLE FilmskaTraka
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