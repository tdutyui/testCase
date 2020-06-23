**Запуск приложения**

Приложение запускается при исполнении класса Application.java, который находится в \src\main\java\oilPrice. Если база данных не создана, то
стоит запустить Database.java в \src\main\java\oilPrice\database.

**Выполнение запросов**

Запросы выполняются через адресную строку в браузере по адресу http://localhost:8080. Например, чтобы получить все данные из CSV-файла в
формате JSON и вызвать соответствующий для этого метод allData в Controller.java, в адресную строку нужно ввести http://localhost:8080/data.
Запросы для методов с параметрами выполняются таким же образом в адресной строке браузера. Вид запроса можно увидеть над методом, например,
@RequestMapping(path = "/data/{date}"). В фигурных скобках указана переменная, которая будет передана как параметр. В данном случае это
date. Чтобы задать этой переменной значение, в адресную строку надо ввести http://localhost:8080/data/2013-04-14. Этот запрос вызывает 
метод priceOnDate и отображает цену на 14 апреля 2013 года. Дата принимается только в формате yyyy-MM-dd, также учитывается только конечная
дата - это 14 день любого месяца, то есть если будет 2013-04-13 или 2013-04-15, то по запросу ничего не будет найдено. 
