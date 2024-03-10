use spese;

create table if not exists spesa (
id_spesa int(11) not null auto_increment primary key,
titolo_spesa varchar(50) not null,
descrizione_spesa varchar(255) default null,
autore_spesa varchar(50) default null,
ammontare_spesa double(10,2) not null
);



