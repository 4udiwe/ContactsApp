# Contacts App
Приложение для просмотра контактов устройства с возможностью поиска и быстрого звонка.

## Функционал

* Просмотр списка контактов устройства
* Поиск контактов по имени
* Быстрый звонок по нажатию на контакт
* Отображение состояния загрузки/ошибки

## Стек
* Язык: Kotlin
* Архитектура: Clean Architecture + MVVM
* UI: Jetpack Compose
* DI: Koin
* Асинхронность: Kotlin Coroutines + Flow

## Тестирование
Реализованы unit-тесты для ContactDataSourceImpl: JUnit, MockK

## Особенности реализации
* Использование ContentResolver для доступа к контактам
* Оптимизированный поиск с регулярными выражениями

## Скриншоты
<details>
    <summary>Скриншоты приложения</summary>
<img alt="contactsPermission.png" src="pictures/contactsPermission.png" width="200"/>
<img alt="mainScreen.png" src="pictures/mainScreen.png" width="200"/>
<img alt="search.png" src="pictures/search.png" width="200"/>
<img alt="callPermossionDialog.png" src="pictures/callPermossionDialog.png" width="200"/>
<img alt="callpermission.png" src="pictures/callpermission.png" width="200"/>
<img alt="call.png" src="pictures/call.png" width="200"/>
</details>


