#### Шаг 26

Я подумал, что стоит вернуться немного назад  
У нас до сих пор нет рабочего приложения, которое можно было бы запустить, поэтому я решил в этом шаге сделать
консольное приложение из того что есть

Сейчас у нас есть модуль `kziuron-pokedex-ui-console-spring` внутри которого готовая конфигурация спринга для 
консольного приложения. Но есть проблема: нам не хватает классов. Отсутствующие для работы главной конфигурации 
классы были помечены аннотацией `ImplementationRequired`

Сейчас не хватает следующего:
```java
@ImplementationRequired(classes = {
        AuthorizationService.class,
        PokemonApiService.class,
        SearchPokemonApiService.class
})
```

Так же у нас есть конфигурация для авторизации: `ListAuthorizationConfiguration`, лдя работы которой нужно использовать
файл конфигурации (аннотация `@NeedToImplement`)

---

Таким образом мы можем использовать готовые конфигурации, добавить конфиг авторизации и сделать стабы сервисов  
Пока самих api-сервисов у нас нет, мы можем вместо них использовать стабы!

Для готовых приложений используем модуль `kziuron-pokedex-app`, внутри него уже
есть модуль `kziuron-pokedex-app-single-console`. Видимо у нас будет несколько консолей, поэтому
добавим в данный модуль еще один уровень модульности: `kziuron-pokedex-app-single-console-business-stub`

---

Настройки для `ListAuthorizationConfig` указаны в application.yml, и загружаются в класс `ListAuthorizationConfigImpl`  
Но стоит заметить что там есть класс прослойка, со структурой принимающей данные из конфигураций.
А уже эта структура преобразуется в `ListAuthorizationConfig`

В пекедж `stub` добавим наши заглушки для api. Реализация будет простой :)

После всего этого, при сборке мавеном в target проекта упадет jar, который можно будет попробовать запустить:
```bash
java -jar kziuron-pokedex/kziuron-pokedex-app/kziuron-pokedex-app-single-console/kziuron-pokedex-app-single-console-business-stub/target/kziuron-pokedex-app-single-console-business-stub-1.0-SNAPSHOT.jar
```

Конфигурации плагина `spring-boot-maven-plugin` нужны для того, чтобы добавить наш запускаемый класс `ConsoleApp`
в манифест jar-файла. Без добавления класса в манифест, при попытке запустить jar будем получать ошибку:
```text
no main manifest attribute, in
```


---

Всю бизнес-логику вынесем в класс `ConsoleAppService`, который уже внутри будет работать с нашими командами
В общем-то работает, но сильно я данное приложение не тестировал. Просто хотелось уже написать что-то реально
рабочее
