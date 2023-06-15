#### Шаг 27

Я хотел сделать другой вариант командной строки (CLI) с использованием Spring Shell.
Для этого нам нужна зависимость (правда очень библиотека очень старая):
```xml
<dependency>
    <groupId>org.springframework.shell</groupId>
    <artifactId>spring-shell</artifactId>
    <version><!-- ... --></version>
</dependency>
```

Так же для Spring Boot есть более новая версия `spring-shell-starter`:
```xml
<dependency>
    <groupId>org.springframework.shell</groupId>
    <artifactId>spring-shell-starter</artifactId>
    <version>${spring-boot.version}</version>
</dependency>
```

Заведем отдельный модуль `kziuron-pokedex-ui-spring-shell`, в котором будут лежать все классы для Spring Shell
Структура модуля такая же как и у `kziuron-pokedex-ui-console`:
- модуль с бизнес логикой [`kziuron-pokedex-ui-spring-shell-business`]
- модуль с конфигурациями и тестами [`kziuron-pokedex-ui-spring-shell-spring`]

Название модуля конфигураций не очень, но соответствует общему принципу.
В этом шаге я просто подготавливаю почту, обновляю версии зависимостей (см. ниже).
Дальнейшая разработка будет в следующем шаге.

---

В maven-central почему-то нет `spring-shell-starter` с версией `3.0.6`.
Но есть версия `3.1.0`, поэтому я решил перевести весь spring-boot на данную версию

К слову, так же обновил версию логгера:
```text
slf4j.version:
2.0.0   -->   2.0.7
logback-classic.version:
1.4.0   -->   1.4.7
```

Так же добавил зависимость на `logback-core`.
Поправил logback.xml файлы, теперь в них нет ошибок (которые раньше подсвечивала idea)

---

Дополнительная информация о библиотеке:
- https://www.baeldung.com/spring-shell-cli
- https://spring.io/projects/spring-shell
- https://github.com/spring-projects/spring-shell
- https://reflectoring.io/spring-shell/


