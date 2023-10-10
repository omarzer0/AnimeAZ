//package az.zero.paging
//
//import kotlinx.coroutines.async
//import kotlin.test.BeforeTest
//import kotlin.test.Test
//import kotlin.test.assertTrue
//
//class PagerTest : BaseTest() {
//    private lateinit var pager: Pager<String, Int>
//
//    @BeforeTest
//    fun setup() {
//        pager = Pager(
//            dataSource = { currentList, page -> getItems(currentList, page) },
//            initialList = emptyList(),
//            comparator = { a, b -> a == b },
//            initialPage = 1,
//            nextPageLoader = { it + 1 }
//        )
//    }
//
//    @Test
//    fun `test calling loadFirstPage loads first list items`() = runTest {
//        pager.loadFirstPageSuspend()
//        assertTrue {
//            pager.pagingState.value.currentList == getItems(
//                pager.initialList,
//                pager.initialPage
//            )
//        }
//    }
//
//
//    @Test
//    fun `test loading next page adds the second page`() = runTest {
//        val expectedPage = pager.pagingState.value.currentPage.plus(1)
//        val expectedList = getItems(pager.pagingState.value.currentList, expectedPage)
//
//        pager.loadNextPageSuspend()
//
//        val actualPage = pager.pagingState.value.currentPage
//        val actualList = pager.pagingState.value.currentList
//
//        assertTrue { expectedPage == actualPage }
//        assertTrue { expectedList == actualList }
//    }
//
//    @Test
//    fun `test refresh loads the first page and resets the currentList to initialList`() = runTest {
//        val expectedList = getItems(pager.initialList, pager.initialPage)
//        pager.refreshSuspend()
//        val actualList = pager.pagingState.value.currentList
//
//        assertTrue { expectedList == actualList }
//    }
//
////    @Test
////    fun `test if load first and next called together - loadNext should not work`() = runTest {
////        val first = async { pager.loadFirstPageSuspend() }
////        val next = async { pager.loadNextPageSuspend() }
////        val next2 = async { pager.loadNextPageSuspend() }
////        val next3 = async { pager.loadNextPageSuspend() }
////        val next4 = async { pager.loadNextPageSuspend() }
////
////        first.await()
////
////        val expectedList = getItems(pager.initialList, 1)
////        println("expectedList: $expectedList")
////        val actualList = pager.pagingState.value.currentList
////        println("actualList: $actualList")
////
////        assertTrue { expectedList == actualList }
////    }
//
//}