create table movies (
    id         varchar(36) primary key,
    name       varchar (140) not null,
    year       numeric(4) not null,
    imdb_score numeric(4, 1),
    imdb_url   varchar(140)
);

create index movies_score_index on movies(imdb_score);
create index movies_imdb_url_index on movies(imdb_url);
create index movies_name_imdb_index on movies(imdb_score);
create index movies_year_index on movies(year);
