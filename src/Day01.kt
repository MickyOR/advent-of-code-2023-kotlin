fun main() {
    fun part1(input: List<String>): Int {
        var ans = 0;
        for (line in input) {
            var num = 0;
            val digits = mutableListOf<Int>();
            for (c in line) {
                if (c in '0'..'9') {
                    digits.add(c.digitToInt());
                }
            }
            num = digits.first()*10 + digits.last();
            ans += num;
        }
        return ans;
    }

    fun part2(input: List<String>): Int {
        var spelledOut = mutableListOf<Pair<String, Int>>();
        spelledOut.add(Pair("one", 1));
        spelledOut.add(Pair("two", 2));
        spelledOut.add(Pair("three", 3));
        spelledOut.add(Pair("four", 4));
        spelledOut.add(Pair("five", 5));
        spelledOut.add(Pair("six", 6));
        spelledOut.add(Pair("seven", 7));
        spelledOut.add(Pair("eight", 8));
        spelledOut.add(Pair("nine", 9));
        var ans = 0;
        for (line in input) {
            var num = 0;
            val digits = mutableListOf<Int>();
            for (i in line.indices) {
                if (line[i].isDigit()) {
                    digits.add(line[i].digitToInt());
                }
                for (nameVal in spelledOut) {
                    if (i+nameVal.first.length <= line.length && nameVal.first == line.substring(i, i+nameVal.first.length)) {
                        digits.add(nameVal.second);
                    }
                }
            }
            num = digits.first()*10 + digits.last();
            ans += num;
        }
        return ans;
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part2(testInput) == 281)

    val input = readInput("Day01")
    part1(input).println()
    val input2 = readInput("Day01_2")
    part2(input2).println()
}
