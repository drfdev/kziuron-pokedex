#### Шаг 17

Добавим тесты для проверки конфигураций спринга
Для этого будем использовать аннотацию `ExtendWith`, с агрументом `SpringExtension` из зависимости `spring-test`
Это специальная зависимость для тестирования спринга через junit 5

---

Для того чтобы все работало корректно, нужно добавить нереализованные бины
Для этого зададим тестовые конфигурации, которые передадим в тест, вместе с основными конфигурациями через аннотацию `ContextConfiguration`

---

Чем полезны тесты:
Заметил, что забыл реализовать `JsonConverter<List<Pokemon>>`, пришлось добавить

---

Так же я не стал пилить много тестов, потому что они все примерно одинаковые и достаточно длинные
Может быть в следующий раз добью тестами, или нет :)