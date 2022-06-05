#### Шаг 10

Добавил реализации шагов сценария `ScenarioStep`

Всего у нас используется:
* pokemonReadStep: ScenarioStep<Path, Pokemon, ModifyContext>
* pokemonWriteStep: ScenarioStep<Pokemon, ScenarioResult<Pokemon>, SearchContext>
* pokemonWriteStep: ScenarioStep<List<Pokemon>, ScenarioResult<List<Pokemon>>, SearchContext>

Нужно написать для каждого шага свою реализацию

---

Для тестирования шагов я решил добавить еще один элемент: InOrder,
для проверки в каком порядке вызывались моки
