username: user
password: 111

username: admin 
password: 111

Git:
1. Создать Git проект jd2_project_week4
2. Проинициализировать проект 
3. Создать ветку develop
4. Написать проект
5. Внести изменения в ветку develop c соответствующим комментарием
6. Сделать pull request в ветку master
7. Прислать ссылку на pull request

Проект:
	Требования:
	- Spring Boot 2.1.4
	- JDBC
	- MySQL
1. Создать многомодульный проект, состоящий из трех jar подпроектов: spring-boot-module/service-module/repository-module
2. Создать зависимость service-module oт repository-module
3. Создать зависимость spring-boot-module oт service-module
4. Создать и реализовать интерфейсы в модуле service-module  
public interface ItemService {
    List<ItemDTO> getItems();
}
public interface UserService {
    List<UserDTO> getUsers();
	void add(UserDTO user);
}
5. Создать и реализовать необходимые репозитории в модуле repository-module
6. Требование к Item модели в базе данных:
	- id
	- name(не более 80 символов)
	- status(READY, STEADY, GO)
7. Требование к User модели в базе данных:
	- id
	- username(не более 70 символов)
	- password(не более 20 символов)
8. Требование к Role модели в базе данных:
	- id
	- name(не более 30 символов)
9. Конвертация в сущность в базе на уровне service-module
10. Работа с транзакциями на уровне service-module
11. Создать страницу, на которой отображается список Item
12. Создать страницу, на которой отображается список пользователей 
13. Создать общедоступную страницу входа для пользователя
14. Создать общедоступную страницу о сайте
15. Создать REST API на добавление пользователя
16. Список пользователей и API добавления пользователя, доступно только пользователю с ролью Administrator. 
17. Список Item показывать только пользователю с ролью Customer  
18. Покрыть unit/integration тестами нужные компоненты
