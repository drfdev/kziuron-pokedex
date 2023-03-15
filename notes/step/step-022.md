#### Шаг 22

Я думал что будет нужно для реализации бизнес функций
В каждой сущности есть поле `version`, означающая версию объекта. Смысл поля в том, что каждый раз когда объект
меняется версия должна увеличиваться на единицу. Чтобы понять, нужно ли менять версию объекта, нужно уметь
вычислять изменения.

Т.е. у нас должна быть процедура которой мы отдаем два объекта: `initial` и `actual`, а она проверяет в каких 
блоках были изменения и увеличивает версию этих блоков.

Лучше всего с этой задачей должен справиться шаблон Визитор, позволяющий обойти весь объект и внести необходимые изменения
Но хорошо бы иметь визитор с возможностью настройки условий его работы

Алгоритм работы должен быть примерно такой:
* создать копию входящего объекта
* внести изменения в объект
* сравнить изначальную копию и измененный объект
* обойти объект визитором и обновить версии всем измененным блокам

Второй пункт - это бизнес логика сервисов изменений
Остальные пункты нужно реализовать отдельно

---

Начнем с создания копии объекта
Это можно сделать по разному, например, используя стандартный метод `clone`
Но мне не нравится такой подход. Наша модель данных должны быть независима от всяких функциональных настроек
Т.е. все копирование объекта должно быть отдельно от модели
Так же хотелось бы, чтобы копирование объекта не зависело от структуры объекта, и работало быстро

Мое решение данной проблемы - сгенерировать класс копирования объекта по струкруре самого объекта в момент сборки проекта

Для этого напишем свой мавен плагин, который будет генерировать код нового класса.

---

Для этого добавим модуль `kziuron-pokedex-maven-plugins`, внутри него сделаем модуль плагина-генератора `kziuron-pokedex-model-processor-maven-plugin`s
Гайд по написанию мавен плагина можно посмотреть тут:
https://maven.apache.org/guides/plugin/guide-java-plugin-development.html
или тут:
https://www.baeldung.com/maven-plugin

Для выполнения плагина в модуль модели добавим:
```xml
<build>
    <plugins>
        <plugin>
            <groupId>dev.drf.pokedex</groupId>
            <artifactId>kziuron-pokedex-model-processor-maven-plugin</artifactId>
            <version>${project.version}</version>
            <executions>
                <execution>
                    <id>process-model</id>
                    <phase>generate-sources</phase>
                    <goals>
                        <goal>process-model</goal>
                    </goals>
                </execution>
            </executions>
            <configuration>
                <modelPackage>dev.drf.pokedex.model</modelPackage>
            </configuration>
        </plugin>
    </plugins>
</build>
```

И в процессе сборки проекта увидем в логах:
```text
[INFO] --- kziuron-pokedex-model-processor-maven-plugin:1.0-SNAPSHOT:process-model (process-model-id) @ kziuron-pokedex-model ---
[INFO] !! Hello Mojo !!
[INFO] Package: dev.drf.pokedex.model
```

Следующим шагом, нужно будет научить плагин генерировать нужные классы