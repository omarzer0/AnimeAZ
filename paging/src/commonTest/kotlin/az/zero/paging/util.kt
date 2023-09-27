package az.zero.paging

fun getItems(currentList: List<String>, pageNumber: Int): List<String> {
    val newList = List(3) {
        "Item #${(3 * pageNumber) - 2 + it}"
    }

    // 1 +
    return currentList.plus(newList)
}
