REST сервис получения информации о студентах.
Состав объекта студента:
1. Фамилия;
2. Имя;
3. Отчество;
4. Группа;
5. Средняя оценка.
Приложение:
1. Производит авторизацию по протоколу OAuth2.0 и возвращает в ответ access_token;
2. Принимает запросы HTTP GET на получения списка объектов студентов;
3. Принимает запросы HTTP POST на изменения сущности объекта студента;
4. Принимает запросы HTTP PUT на добавление новой сущности студента;
5. Принимает запросы HTTP DELETE на удаление объекта студента.
