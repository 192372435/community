## 开源
Spring Boot 项目

## 资料
[SpringBoot 文档](https://spring.io/guides)

[SpringBoot-web 文档](https://spring.io/guides/gs/serving-web-content)

[Github 获取上传key文档](https://developer.github.com/v3/guides/managing-deploy-keys/#deploy-keys)

[Github 开发者文档](https://github.com/settings/apps)

## 工具
https://github.com

https://git-scm.com

## 脚本
```sql
create table user
(
	id int auto_increment,
	account_id varchar(100),
	name varchar(50),
	token char(36),
	gmt_create bigint,
	gmt_modified bigint,
	constraint user_1_pk
		primary key (id)
);
```