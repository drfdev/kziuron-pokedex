#### Шаг 9

Нужно добавить реализацию для консоли.
В принципе есть несколько вариантов как это сделать:
Можно использовать `System.in` и `System.out` напрямую, но для ввода данных слишком запарно

Можно использовать стандартный класс `java.io.Console`
Это отличное решение, но с ним неудобно работать из IDE, потому что `System.console()` будет возвращать всегда null

Можно использовать класс `java.util.Scanner` для чтения с консоли

---

Я выбрал вариант такой:
писать напрямую в поток, читать через класс Scanner

Сделал я это для облегчения тестирования
Наиболее красивый вариант, я все таки считаю через использование класса `Console`, но из-за того что он финальный, его неудобно мокать
А из-за того что он всегда null при запуске из идеи, еще и неудобно тестировать

---

С тестированием есть некоторые сложности:
Для теста на запись текста через стандартный вывод, мне пришлось подменить стандартный поток вывода, и вычитывать результат записи из моего потока
Для теста на запись, я подложил в сканер не стандартный поток ввода, а просто строку, сканер должен просто вычитать ее до первого символа переноса строки

У библиотеки Mockito есть ограничения: нельзя сделать mock из `final` класса
А для класса `Console` невозможно использовать spy из-за каких-то ограничений джавы
Поэтому иногда приходится делать странные костыли для проверки базовых вещей