Предполагаемая структура:

[BD] <--> [core module] <--> [business module] <--> [ui module]

**BD**  
База данных, в которой будут лежать данные  
Скорее всего, придется поднимать ее в докере, чтобы не устанавливать ее на компьютер

**core module**  
Модуль коры  
Отвечает за CRUD операции, предоставляет поисковые сервисы, отвечает за асинхронные операции
(crud - create, read, update, delete)
Если будет добавлен кэш, модуль коры будет отвечать за него и синхронизацию кеша с базой

**business module**  
Модуль бизнес операций  
Тут должны быть различные бизнесовые опции и проверки. Тут реализуется сама логика работы программы

**ui module**  
Модуль UI пользователя  
Этот модуль будет предосталвять интерфейс пользователя для взаимодействия с остальной системой

---

Работа с системой:

```text
Пользователь использует UI --> UI вызывает бизнес модуль --> Бизнес модуль выполняет проверки
        Если нашел ошибку --> вернуть ошибку в UI
        Если все в порядке --> Вызывает кору --> кора выполняет CRUD операцию на БД
                Если случилась ошибка --> вернуть в бизнес ошибку --> бизнес возвращает в UI ошибку
                Если все в порядке --> вернуть в бизнес сообщение об успехе -> бизнес возвращает в UI успех
```
