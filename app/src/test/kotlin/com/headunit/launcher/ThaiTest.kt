package com.headunit.launcher

import org.junit.Assert.*
import org.junit.Test

class ThaiTest {
    @Test fun labelsTranslateWithoutChangingStoredValuesOrAddresses() {
        assertEquals("การตั้งค่า", Thai.text("Settings"))
        assertEquals(" การตั้งค่า ", Thai.text(" Settings "))
        assertEquals("การตั้งค่า", Thai.text("SETTINGS"))
        assertEquals("บ้าน · ที่ทำงาน", Thai.text("Home · Work"))
        assertEquals("ถนนพระรามที่ 4", Thai.text("ถนนพระรามที่ 4"))
        assertEquals("123 High Street", Thai.text("123 High Street"))
        assertEquals("mbtoken", Thai.text("mbtoken"))
        assertEquals("https://api.mapbox.com", Thai.text("https://api.mapbox.com"))
    }

    @Test fun guidanceUsesThaiWithNumericRoundaboutExitsAndLaneCounts() {
        assertEquals("เลี้ยวซ้าย", Geo.maneuverText("TURN_LEFT"))
        assertEquals("เลี้ยวขวา", Geo.maneuverText("TURN_RIGHT"))
        assertEquals("กลับรถ", Geo.maneuverText("MAKE_UTURN"))
        assertEquals("ที่วงเวียน ใช้ทางออกที่ 2", Geo.maneuverText("ROUNDABOUT_RIGHT", 2))
        assertEquals("200 เมตร", Geo.spoken(200.0, false))
        val sec = LaneSec(0.0, 10.0, listOf(Lane(listOf("LEFT"), "LEFT"), Lane(listOf("STRAIGHT"), null)))
        assertEquals("ใช้ ช่องทาง ด้านซ้าย", Geo.laneAdvice(sec, "TURN_LEFT"))
        assertEquals("ทางออกที่ 3", Geo.stepTag(Step(0.0, "ROUNDABOUT", "", "", roundExit = 3)))
    }

    @Test fun missingThaiVoiceDoesNotFallBackToAnEnglishVoice() {
        assertFalse(Thai.voiceAvailable(null))
        assertFalse(Thai.voiceAvailable(-1))
        assertFalse(Thai.voiceAvailable(-2))
        assertTrue(Thai.voiceAvailable(0))
        assertTrue(Thai.voiceAvailable(2))
    }

    @Test fun thaiVoiceCommandsPreservePlaceNamesAndDoNotRecursivelyMatchEnglish() {
        assertEquals("go home", ThaiVoice.command("กลับบ้าน"))
        assertEquals("stop navigation", ThaiVoice.command("หยุดนำทาง"))
        assertEquals("search:วัดพระแก้ว", ThaiVoice.command("นำทางไปวัดพระแก้ว"))
        assertNull(ThaiVoice.command("go home"))
        assertNull(ThaiVoice.command("ถนนกลับบ้านซอยสอง"))
    }
}
