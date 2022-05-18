#### Шаг 6

Перейдем к написанию консольного приложения
для этого заведем модуль `kziuron-pokedex-app`, внутри которого будут отдельные модули самих приложений, то типам
Первое из них будет `kziuron-pokedex-app-single-console`

Для консольного приложения нужно будет реализовать консольный UI, бизнес часть и кору.
Так же на этом этапе будем подключать Spring, там где это возможно

Так же нужна будет обертка для транспорта, хотя в едином приложении никакого транспорта не будет.
Просто нужно добавить классы для будущего развития идеи транспорта

Придется разделить написание полного приложения на несколько шагов.
Начнем с UI консоли. Тут нужно реализовать операцию авторизации, получения и просмотра данных, создания/обновления/деактивации покемонов.

Я думаю стоит сделать концепцию работы через _команды_ и _сценарии_.
Сначала пользователь удачно авторизуется, затем может писать команды. Каждая команда запускает свой сценарий в котором может быть один или более шагов.

**Команды**:

Команда по умолчанию - авторизация, если она не завершается успехом, приложение не должно продолжать работу
Остальные команды будут представлять собой
```
[имя команды] [параметры команды]
```
После успешного ввода команды - запустить сценарий этой команды


**Сценарии**:

Набор шагов, которые будут выполняться один за другим.
Возможно условные шаги и разветвления.
Возможны дополнительные уточнения у пользователя (ввод данных), вывод каких-то данных на консоль

---

На этом шаге мы добавим классы для чтения данных с консоли, запись данных в консоль, интерфейсы для Команд и Сценариев
Минимальный список команд:
* авторизация
* получить данные по ID (вывод в файл или на экран)
* создать покемона (из файла в формате JSON)
* обновить покемона (из файла в формате JSON)
* деактивировать покемона (из файла в формате JSON)
* вызов smart-update (из файла в формате JSON)
* поиск по ID и версии (вывод в файл или на экран)
* поиск по покемона по имени (вывод в файл или на экран)

Дополнительно нужно реализовать чтение и запись в файл, конвертер объекта Pokemon в JSON (и обратно)

Реализовывать все сразу трудно, поэтому сделаем это все в несколько шагов.
Так же, для каждой реализации нужно будет написать unit-тесты, чтобы убедиться, что все работает как планировалось.

---

Пекеджы:
`dev.drf.pokedex.ui.console.command` - команды
`dev.drf.pokedex.ui.console.command` - чтение и запись в консоль (контекст консоли)
`dev.drf.pokedex.ui.console.files` - работа с файлами
`dev.drf.pokedex.ui.console.json` - работа с JSON-ом (конвертер JSON)
`dev.drf.pokedex.ui.console.scenario` - сценарии