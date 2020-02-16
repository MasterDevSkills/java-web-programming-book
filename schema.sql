create schema eShoppers collate utf8mb4_0900_ai_ci;

create table product
(
	id bigint auto_increment
		primary key,
	name varchar(100) not null,
	description varchar(500) null,
	price decimal not null,
	version bigint not null,
	date_created timestamp not null,
	date_last_updated timestamp null
);

create table shipping_address
(
	id bigint auto_increment
		primary key,
	address varchar(250) not null,
	address2 varchar(250) not null,
	state varchar(20) null,
	zip varchar(10) null,
	country varchar(30) null,
	version bigint not null,
	date_created timestamp not null,
	date_last_updated timestamp not null,
	mobile_number varchar(20) null
);

create table user
(
	id bigint auto_increment
		primary key,
	username varchar(20) not null,
	password varchar(200) not null,
	version bigint not null,
	date_created timestamp not null,
	date_last_updated timestamp null,
	email varchar(100) null,
	first_name varchar(30) not null,
	last_name varchar(30) not null
);

create table cart
(
	id bigint auto_increment
		primary key,
	total_price decimal null,
	total_item int null,
	version bigint null,
	date_created timestamp null,
	date_last_updated timestamp null,
	user_id bigint null,
	constraint cart_user_id_fk
		foreign key (user_id) references user (id)
);

create table cart_item
(
	id bigint auto_increment
		primary key,
	quantity int not null,
	price decimal null,
	product_id bigint null,
	version bigint null,
	date_created timestamp null,
	date_last_updated timestamp null,
	cart_id bigint not null,
	constraint cart_item_cart_fk
		foreign key (cart_id) references cart (id),
	constraint cart_item_product_id_fk
		foreign key (product_id) references product (id)
);

create table `order`
(
	id bigint auto_increment
		primary key,
	shipping_address_id bigint not null,
	cart_id bigint not null,
	version bigint not null,
	shipping_date timestamp null,
	shipped tinyint(1) null,
	user_id bigint not null,
	date_created timestamp not null,
	date_last_updated timestamp not null,
	constraint order_cart_id_fk
		foreign key (cart_id) references cart (id),
	constraint order_shipping_address_id_fk
		foreign key (shipping_address_id) references shipping_address (id),
	constraint order_user_id_fk
		foreign key (user_id) references user (id)
);

