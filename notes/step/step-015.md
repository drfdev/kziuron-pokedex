#### Шаг 15

Внезапный рефакторинг!

Сейчас у нас проект выглядит таким образом:
```text
kziuron-pokedex
  kziuron-pokedex-ui
    kziuron-pokedex-ui-console
    kziuron-pokedex-ui-console-spring
```

Получается, что у нас для модуля относящихся к консоли лежат внутри модуля UI
Но мы знаем что будут другие UI
Поэтому нужен еще один уровень вложенности, нужно сделать вот так:
```text
kziuron-pokedex
  kziuron-pokedex-ui
    kziuron-pokedex-ui-console
      kziuron-pokedex-ui-console-business
      kziuron-pokedex-ui-console-spring
    kziuron-pokedex-ui-other
```

Перенесем проект `kziuron-pokedex-ui-console` в `kziuron-pokedex-ui-console-business`
Но когда мы начнем делать другой вариант UI, мы сможем сделать для него отдельный проект `kziuron-pokedex-ui-other` (или какое-нибудь название получше)

Так же теперь можно сделать различные модули, типа `kziuron-pokedex-ui-common` или `kziuron-pokedex-ui-console-common`
Или добавлять новые модули с дополнительной функциональностью
