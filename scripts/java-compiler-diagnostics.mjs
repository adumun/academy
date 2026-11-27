// javac -XDrawDiagnostics emits one primary code per diagnostic line.
// Arguments may themselves contain diagnostic codes; only the primary code counts.
export function compilerErrorCodes(stderr) {
    return stderr.split(/\r?\n/)
        .map(line => line.match(/^(?:.*:\d+:\d+:\s*)?(compiler\.err\.[a-z0-9_.-]+)(?=:|$)/)?.[1])
        .filter(Boolean);
}

export function matchesCompilerRejection(result, expected) {
    const codes = compilerErrorCodes(result.stderr || '');
    return !result.error && result.status === 1
        && codes.length === 1 && codes[0] === expected;
}
