Решение тестового задания для участия в асессорском проекте

В /src находятся решения предложенных заданий.

ПЕРВОЕ. "Даны два строковых представления чисел A и B. Нужно максимизировать A, заменив в нём любую цифру на цифру из B. Каждую цифру B можно использовать только один раз."

Решение данного задания представленно в классе Maximization. Задача была решена принципиально двумя способами:

1. Добавление элементов второго числа в массив с последующим вызовом метода Arrays.sort()
2. Добавление элементов в PriorityQueue с переданным реверсивным компоратором

Оказалось, что первый метод более производительный (производится одна сортировка - n*log(n), в то время как во втором случае выполняются queue.offer() и queue.poll() так же с n*log(n)).

Для оптимизации алгоритма при a.length() >> b.length() и b.length() >> a.length() было предложено следующее. При более длинной второй строке, алгоритм проходит по первой строке, выясняя сколько цифр в строке не является "9", затем он проходит по второй строке, прерываясь если находит в ней необходимое количество цифр "9". В случае, если вторая строка меньше первой, то после того, как все цифры из второй строки подставлены в первую, вызывается метод substring, прекращая иттерацию. Таким образом, complexity приближается к O(1).

Для утверждения в полученных выводах был создан метод getTests(Method method), возвращающий результаты двух тестов для обозначенных выше алгоритмов. testaB - тест, иллюстрирующий изменение времени исполнения алгоритма, при фиксированной длине "A" и увеличивающейся длине "B". testAb - аналогично при фиксированной длине B. Значения теста - двумерный массив, содержащий пары - длина изменяющегося объекта / время исполнения. 

Complexity данных алгоритмов - O(n*log(n)).

ВТОРОЕ. "Найти все простые числа меньше или равные заданному числу N".

Решение данного классического задания представленно в классе SimpleNums. Задача так же была решена двумя принципиальными образами.

1. Перебор значений до N, проверка их на простоту делением на простые числа, которые меньше квадратного корня проверяемого числа.
2. Алгоритм Эратосфена: поиск простых чисел до N путем перебора всех чисел до квадратного корня N и вычеркивания всех тех чисел до N, что кратны текущему простому числу. Все оставшиеся числа (после квадратного корня N) - простые.

Решето оказывается значительно более эффективным, сложность алгоритма - O(n*log(log(n))).

Выходные данные метода Main - массив пар: N/время исполнения алгоритма.
