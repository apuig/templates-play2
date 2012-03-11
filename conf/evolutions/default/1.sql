# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table category (
  id                        bigint not null,
  name                      varchar(255),
  constraint pk_category primary key (id))
;

create table template (
  id                        bigint not null,
  name                      varchar(255),
  category_id               bigint,
  category_name             varchar(255),
  disk_format_type          varchar(21),
  ethernet_driver           varchar(7),
  description               varchar(255),
  disk_file_path            varchar(255),
  disk_file_size            bigint,
  cpu                       integer,
  ram                       bigint,
  hd                        bigint,
  icon_path                 varchar(255),
  user_mail                 varchar(255),
  constraint ck_template_disk_format_type check (disk_format_type in ('UNKNOWN','RAW','INCOMPATIBLE','VMDK_STREAM_OPTIMIZED','VMDK_FLAT','VMDK_SPARSE','VHD_FLAT','VHD_SPARSE','VDI_FLAT','VDI_SPARSE','QCOW2_FLAT','QCOW2_SPARSE')),
  constraint ck_template_ethernet_driver check (ethernet_driver in ('E1000','PCNet32','VMXNET3')),
  constraint pk_template primary key (id))
;

create sequence category_seq;

create sequence template_seq;

alter table template add constraint fk_template_category_1 foreign key (category_id) references category (id) on delete restrict on update restrict;
create index ix_template_category_1 on template (category_id);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists category;

drop table if exists template;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists category_seq;

drop sequence if exists template_seq;

