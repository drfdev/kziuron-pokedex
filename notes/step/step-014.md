#### Шаг 14

Теперь можно добавить спринг в проект
для этого сделаем зависимость на `Spring Boot Dependencies` последней версии на текущий момент

Затем заведем модуль `kziuron-pokedex-ui-console-spring`, для которого сделаем зависимость на модуль всех классов консоли
В новом модуле будет лежать спринговая конфигурация и спринговые тесты (но это сделаем следующим шагом, как и реализация авторизации)

Конфигурацию спринга можно сделать разными вариантами, но мне больше всего нравится описание через конфигурацию классов
Используем аннотацию `@Configuration` и сделаем класс конфигураций
Затем мы сможем просто импортировать данную конфигурацию в приложение и использовать все описанные в ней бины