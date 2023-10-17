package az.zero.paging

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

class PagerTest : BaseTest() {
    private lateinit var pager: Pager<String, Int>
    private val initialEmptyList = emptyList<String>()
    private val initialListWithData = listOf("Init #test1", "Init #test2", "Init #test3")
    private val initialPage = 1

    @BeforeTest
    fun setup() {
        println("Setup method run")
        pager = Pager(
            initialList = initialEmptyList,
            comparator = { a, b -> a == b },
            initialPage = initialPage,
            howToLoadNext = { it + 1 }
        )
    }

    @Test
    fun `test calling loadFirstPage loads first list items`() = runTest {
        pager.loadFirstPage(dataSource = { getItems(initialEmptyList, it) })

        val expected = getItems(initialEmptyList, initialPage)
        val actual = pager.pagingResult.value.items
        println("expected: $expected")
        println("actual: $actual")
        assertTrue { expected == actual }
    }


    @Test
    fun `test loading next page adds the second page`() = runTest {
        pager.loadFirstPage(dataSource = { getItems(initialEmptyList, it) })
        val expectedList = getItems(pager.pagingResult.value.items, 2)

        pager.loadNextPage()

        val actualList = pager.pagingResult.value.items

        println("expectedList: $expectedList")
        println("actualList: $actualList")

        assertTrue { expectedList == actualList }
    }


    @Test
    fun `test refresh loads the first page and resets the currentList to initialList`() = runTest {
        pager.loadFirstPage(dataSource = { getItems(initialEmptyList, it) })

        pager.refresh()

        val expectedList = getItems(initialEmptyList, initialPage)
        val actualList = pager.pagingResult.value.items

        println("expectedList: $expectedList")
        println("actualList: $actualList")

        assertTrue { expectedList == actualList }
    }

    @Test
    fun `test if load first and next called together - loadNext should not work`() = runTest {
        val next = async { pager.loadNextPage() }
        val next2 = async { pager.loadNextPage() }
        val first = async { pager.loadFirstPage(dataSource = { getItems(initialEmptyList, it) }) }

        awaitAll(first, next, next2)

        val expectedList = getItems(initialEmptyList, initialPage)
        val actualList = pager.pagingResult.value.items

        println("expectedList: $expectedList")
        println("actualList: $actualList")

        assertTrue { expectedList == actualList }
    }

//    @Test
//    fun `test refresh without loading first should throw exception`() = runTest {
//
//    }

}