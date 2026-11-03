"""Guard localisation boundaries that must not alter routing or saved data."""
from pathlib import Path
import re
root = Path(__file__).resolve().parents[1]
k = root / 'app/src/main/kotlin/com/headunit/launcher'
assert 'applicationIdSuffix = ".thai"' in (root/'app/build.gradle.kts').read_text()
assert 'Natreethon/Horizon-Maps' in (root/'app/build.gradle.kts').read_text()
assert 'setLanguage(Locale.UK)' not in (k/'NavEngine.kt').read_text()
assert 'Thai.voiceAvailable(tts?.setLanguage(Thai.locale))' in (k/'NavEngine.kt').read_text()
assert 'language=en-GB' not in (k/'Geo.kt').read_text()
assert 'Thai.tomtomLanguage' in (k/'Geo.kt').read_text()
assert 'Thai.mapboxLanguage' in (k/'Mapbox.kt').read_text()
for line in (k/'Prefs.kt').read_text().splitlines():
    if 'const val ' in line:
        assert not re.search('[ก-๙]', line), line
# A bare $variable immediately followed by Thai is parsed by Kotlin as one identifier.
for p in k.rglob('*.kt'):
    assert not re.search(r'\$[A-Za-z_][A-Za-z_0-9]*[ก-๙]', p.read_text()), str(p)
print('Thai package, routing language, voice, interpolation and preference checks passed')
