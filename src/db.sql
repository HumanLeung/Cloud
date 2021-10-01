CREATE table `payment`(
                          `id`bigint(20) NOT NULL auto_increment comment 'ID',
                          `serial` varchar(200) default '',
                          PRIMARY KEY(`id`)
)ENGINE=InnoDB auto_increment=1 default charset=utf8