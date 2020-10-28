drop user 'user'@'localhost';
drop schema chatbot_helpdesk;

create schema chatbot_helpdesk;

use chatbot_helpdesk;

create user 'user'@'localhost' identified by 'pass123';

grant select, insert, delete, update on chatbot_helpdesk.* to 'user'@'localhost';

create table usr_usuario (
  usr_id bigint unsigned not null auto_increment,
  usr_nome varchar(20) not null,
  usr_senha varchar(100) not null,
  usr_avatar varchar(100),
  primary key (usr_id),
  unique key uni_usuario_nome (usr_nome)
);

create table aut_autorizacao (
  aut_id bigint unsigned not null auto_increment,
  aut_nome varchar(20) not null,
  primary key (aut_id),
  unique key uni_aut_nome (aut_nome)
);

create table uau_usuario_autorizacao (
  usr_id bigint unsigned not null,
  aut_id bigint unsigned not null,
  primary key (usr_id, aut_id),
  foreign key aut_usuario_fk (usr_id) references usr_usuario (usr_id) on delete restrict on update cascade,
  foreign key aut_autorizacao_fk (aut_id) references aut_autorizacao (aut_id) on delete restrict on update cascade
);


create table usr_notificacoes (
  not_id bigint unsigned not null auto_increment,
  not_remetente_id bigint unsigned not null,
  not_destinatario_id bigint unsigned not null,
  not_titulo varchar(20) not null,
  not_conteudo varchar(255) not null,
  primary key (not_id),
  foreign key (not_remetente_id) references usr_usuario (usr_id) on delete restrict on update cascade,
  foreign key (not_destinatario_id) references usr_usuario (usr_id) on delete restrict on update cascade
);


create table conversas (
  chat_id bigint unsigned not null auto_increment,
  primary key (chat_id)
);

create table usuarios_conversas(
  chat_id bigint unsigned not null,
  usr_id bigint unsigned not null,
  primary key (chat_id, usr_id),
  foreign key chat_id_fk (chat_id) references conversas (chat_id) on delete restrict on update cascade,
  foreign key usr_id_fk (usr_id) references usr_usuario (usr_id) on delete restrict on update cascade
);

create table mensagens(
  mensagem_id bigint unsigned not null auto_increment,
  chat_id bigint unsigned not null,
  destinatario bigint unsigned not null,
  data_hora varchar(20) not null,
  conteudo varchar(255) not null,
  primary key (mensagem_id),
  foreign key chat_id_fk (chat_id) references conversas (chat_id) on delete restrict on update cascade,
  foreign key destinatario_fk (destinatario) references usr_usuario (usr_id) on delete restrict on update cascade
);

create table usuarios_mensagens(
  mensagem_id bigint unsigned not null,
  usr_id bigint unsigned not null,
  primary key (mensagem_id, usr_id),
  foreign key mensagem_id_fk (mensagem_id) references mensagens (mensagem_id) on delete restrict on update cascade,
  foreign key usr_id_fk (usr_id) references usr_usuario (usr_id) on delete restrict on update cascade
);


insert into usr_usuario (usr_nome, usr_senha)
    values ('admin','$2a$10$i3.Z8Yv1Fwl0I5SNjdCGkOTRGQjGvHjh/gMZhdc3e7LIovAklqM6C');
insert into aut_autorizacao(aut_nome)
    values ('ROLE_ADMIN');
insert into uau_usuario_autorizacao 
    values (1, 1);
