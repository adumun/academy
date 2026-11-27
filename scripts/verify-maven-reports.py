"""Verify exact Surefire cases; a failed build alone is not mutation evidence."""
import sys
from pathlib import Path
import xml.etree.ElementTree as ET

reports = Path(sys.argv[1])
mode = sys.argv[2]
expected = {
    "DemoJacksonTest": 6,
    "DemoGsonTest": 6,
    "RangoTest": 2,
    "RangoBoundaryTest": 2,
}
if mode == "mutation":
    expected = {"RangoTest": 2}
elif mode != "normal":
    raise SystemExit("Invalid verification mode")
observed = {}
failed = []
for file in reports.glob("TEST-*.xml"):
    suite = ET.parse(file).getroot()
    name = suite.attrib["name"]
    if name in observed:
        raise SystemExit("Duplicate suite: " + name)
    cases = suite.findall("testcase")
    observed[name] = len(cases)
    for case in cases:
        if case.find("error") is not None or case.find("skipped") is not None:
            raise SystemExit("Error or skipped test: " + case.attrib["name"])
        if case.find("failure") is not None:
            failed.append((name, case.attrib["name"]))
if observed != expected:
    raise SystemExit(f"Unexpected suites: {observed}; expected {expected}")
wanted = [("RangoTest", "rangoValido")] if mode == "mutation" else []
if failed != wanted:
    raise SystemExit(f"Unexpected failures: {failed}; expected {wanted}")
print(f"Maven {mode}: {sum(observed.values())} tests, {len(failed)} expected failures PASS")
