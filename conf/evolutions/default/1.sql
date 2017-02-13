# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table brand_name (
  name                      varchar(255),
  id                        integer)
;

create table composite_key_example (
  id1                       integer,
  id2                       integer,
  name                      varchar(255),
  constraint pk_composite_key_example primary key (id1, id2))
;

create table geolocation (
  longitude                 double,
  latitude                  double,
  country                   varchar(255),
  constraint pk_geolocation primary key (longitude, latitude))
;

create table geographicaldistribution (
  entityId                  integer,
  countrycode               varchar(255),
  numberoftweets            integer,
  constraint pk_geographicaldistribution primary key (entityId, countrycode))
;

create table handle (
  name                      varchar(255),
  id                        integer)
;

create table impressions (
  entityId                  integer,
  numberofimpressions       bigint,
  timestamp                 datetime)
;

create table influentialfollower (
  userid                    bigint,
  entityid                  integer,
  screenname                varchar(255),
  score                     double,
  timestamp                 datetime,
  followercount             bigint)
;

create table mentions (
  entity_id                 integer,
  timestamp                 datetime,
  numberofmentions          integer,
  constraint pk_mentions primary key (entity_id, timestamp))
;

create table Mostretweetedtweets (
  tweetId                   bigint,
  entityId                  integer,
  tweettext                 varchar(255),
  retweetcount              bigint,
  parenttweetid             bigint)
;

create table query (
  id                        integer auto_increment not null,
  name                      varchar(255),
  constraint pk_query primary key (id))
;

create table entitysentimentscore (
  entityId                  integer,
  positivetweetscount       integer,
  negativetweetscount       integer,
  timestamp                 datetime)
;

create table trendinghashtags (
  entityId                  integer,
  hashTag                   varchar(255),
  timestamp                 datetime,
  score                     double,
  constraint pk_trendinghashtags primary key (entityId, hashTag, timestamp))
;

create table user (
  screen_name               varchar(255) not null,
  access_token              varchar(255),
  access_secret             varchar(255),
  constraint pk_user primary key (screen_name))
;

create table user_entities (
  screenName                varchar(255),
  id                        integer)
;

alter table brand_name add constraint fk_brand_name_query_1 foreign key (id) references query (id) on delete restrict on update restrict;
create index ix_brand_name_query_1 on brand_name (id);
alter table handle add constraint fk_handle_query_2 foreign key (id) references query (id) on delete restrict on update restrict;
create index ix_handle_query_2 on handle (id);
alter table user_entities add constraint fk_user_entities_user_3 foreign key (screenName) references user (screen_name) on delete restrict on update restrict;
create index ix_user_entities_user_3 on user_entities (screenName);
alter table user_entities add constraint fk_user_entities_query_4 foreign key (id) references query (id) on delete restrict on update restrict;
create index ix_user_entities_query_4 on user_entities (id);



# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table brand_name;

drop table composite_key_example;

drop table geolocation;

drop table geographicaldistribution;

drop table handle;

drop table impressions;

drop table influentialfollower;

drop table mentions;

drop table Mostretweetedtweets;

drop table query;

drop table entitysentimentscore;

drop table trendinghashtags;

drop table user;

drop table user_entities;

SET FOREIGN_KEY_CHECKS=1;

