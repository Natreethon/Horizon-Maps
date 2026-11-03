package com.headunit.launcher

/** Whole-command matching avoids treating Thai place names as navigation commands. */
internal object ThaiVoice {
    fun command(raw: String): String? {
        val t = raw.trim().removePrefix("เฮ้ฮอไรซอน").trim()
        val exact = mapOf(
            "กลับบ้าน" to "go home", "ไปบ้าน" to "go home", "นำทางกลับบ้าน" to "go home",
            "ไปที่ทำงาน" to "go work", "นำทางไปที่ทำงาน" to "go work",
            "หยุดนำทาง" to "stop navigation", "จบการนำทาง" to "stop navigation", "ยกเลิกเส้นทาง" to "stop navigation",
            "เปิดเสียง" to "unmute", "ปิดเสียง" to "mute", "เฉพาะเสียงเตือน" to "alerts only",
            "ซูมเข้า" to "zoom in", "ซูมออก" to "zoom out", "ดูเส้นทางทั้งหมด" to "overview",
            "ถึงกี่โมง" to "eta", "เหลืออีกกี่นาที" to "eta", "เหลืออีกไกลไหม" to "eta",
            "แชร์เวลาถึง" to "share", "กี่โมงแล้ว" to "what time",
            "หลีกเลี่ยงทางด่วน" to "avoid toll", "หลีกเลี่ยงค่าผ่านทาง" to "avoid toll",
            "หลีกเลี่ยงมอเตอร์เวย์" to "avoid motorway", "หลีกเลี่ยงถนนนี้" to "avoid this road",
            "หาปั๊มน้ำมัน" to "find fuel", "หาจุดชาร์จ" to "find charging",
            "หาห้องน้ำ" to "find toilet", "หาร้านกาแฟ" to "find coffee",
            "หาร้านอาหาร" to "find restaurant", "หาที่จอดรถ" to "find parking",
            "รายงานอุบัติเหตุ" to "report accident", "รายงานรถเสีย" to "report breakdown",
            "รายงานอันตราย" to "report hazard", "รายงานตำรวจ" to "report police",
        )
        exact[t]?.let { return it }
        for (prefix in listOf("นำทางไป", "พาไป", "ค้นหา", "ไปที่")) {
            if (t.startsWith(prefix)) {
                val place = t.removePrefix(prefix).trim()
                if (place.isNotBlank()) return "search:$place"
            }
        }
        return null
    }
}
