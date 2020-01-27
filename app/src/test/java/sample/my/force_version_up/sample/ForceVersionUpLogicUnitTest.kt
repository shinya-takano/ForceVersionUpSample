package sample.my.force_version_up.sample

import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ForceVersionUpLogicUnitTest {

    private lateinit var forceVersionUpLogic: ForceVersionUpLogic

    @Before
    fun setup() {
        forceVersionUpLogic = ForceVersionUpLogic()
    }

    @Test
    fun version_is_not_three_size() {
        assertEquals(false, forceVersionUpLogic.checkForceVersionUp("2.0", "1.0.0"))
        assertEquals(false, forceVersionUpLogic.checkForceVersionUp("2.0.0", "1.0"))
        assertEquals(false, forceVersionUpLogic.checkForceVersionUp("1.1", "1.0"))
        assertEquals(false, forceVersionUpLogic.checkForceVersionUp("2", "1"))
    }

    @Test
    fun version_equal() {
        assertEquals(false, forceVersionUpLogic.checkForceVersionUp("1.0.0", "1.0.0"))
        assertEquals(false, forceVersionUpLogic.checkForceVersionUp("0.10.0", "0.10.0"))
    }

    @Test
    fun version_matrix() {
        assertEquals(true, forceVersionUpLogic.checkForceVersionUp("1.0.1", "1.0.0"))
        assertEquals(true, forceVersionUpLogic.checkForceVersionUp("1.1.0", "1.0.0"))
        assertEquals(true, forceVersionUpLogic.checkForceVersionUp("2.0.0", "1.0.0"))
        assertEquals(false, forceVersionUpLogic.checkForceVersionUp("1.0.1", "1.0.2"))
        assertEquals(false, forceVersionUpLogic.checkForceVersionUp("1.1.0", "1.2.0"))
        assertEquals(false, forceVersionUpLogic.checkForceVersionUp("2.0.0", "3.0.0"))
    }

    @Test
    fun version_is_not_decimal() {
        assertEquals(true, forceVersionUpLogic.checkForceVersionUp("1.1.10", "1.1.9"))
        assertEquals(true, forceVersionUpLogic.checkForceVersionUp("1.10.0", "1.9.0"))
        assertEquals(true, forceVersionUpLogic.checkForceVersionUp("10.0.0", "9.0.0"))
        assertEquals(false, forceVersionUpLogic.checkForceVersionUp("1.1.9", "1.1.10"))
        assertEquals(false, forceVersionUpLogic.checkForceVersionUp("1.9.0", "1.10.0"))
        assertEquals(false, forceVersionUpLogic.checkForceVersionUp("9.0.0", "10.0.0"))
    }

    @Test
    fun version_is_not_number() {
        assertEquals(false, forceVersionUpLogic.checkForceVersionUp("a.0.0", "1.0.0"))
        assertEquals(false, forceVersionUpLogic.checkForceVersionUp("0.a.0", "1.0.0"))
        assertEquals(false, forceVersionUpLogic.checkForceVersionUp("0.0.a", "1.0.0"))
        assertEquals(false, forceVersionUpLogic.checkForceVersionUp("1.0.0", "-.0.0"))
        assertEquals(false, forceVersionUpLogic.checkForceVersionUp("1.0.0", "1.*.0"))
        assertEquals(false, forceVersionUpLogic.checkForceVersionUp("1.0.0", "1.0.¥"))
    }
}
