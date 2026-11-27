import { mkdtempSync, readdirSync, readFileSync, rmSync } from 'node:fs';
import { tmpdir } from 'node:os';
import { join } from 'node:path';
import { spawnSync } from 'node:child_process';
import { fileURLToPath } from 'node:url';
const source = fileURLToPath(new URL('../examples/java/handbook/', import.meta.url));
const suites = JSON.parse(readFileSync(join(source, 'test-suites.json'), 'utf8'));
const manifest = JSON.parse(readFileSync(join(source, 'snippets.json'), 'utf8'));
const covered = new Set();
let passed = 0;
function run(command, args) {
    const result = spawnSync(command, args, { encoding: 'utf8', timeout: 30000 });
    if (result.error || result.status !== 0) throw new Error(command + ': ' + (result.error || result.stderr || result.stdout));
    return result.stdout.replace(/\r\n/g, '\n').trimEnd();
}
for (const suite of suites) {
    if (!/^[a-z0-9-]+$/.test(suite.directory) || covered.has(suite.directory)) throw new Error('Invalid suite ' + suite.directory);
    covered.add(suite.directory);
    const dir = join(source, suite.directory);
    const files = readdirSync(dir).filter(name => name.endsWith('.java')).sort();
    if (!files.length || !suite.runs.length) throw new Error('Empty suite ' + suite.directory);
    const out = mkdtempSync(join(tmpdir(), 'academy-source-'));
    try {
        run('javac', ['--release', '21', '-encoding', 'UTF-8', '-d', out, ...files.map(name => join(dir, name))]);
        const executed = new Set();
        for (const test of suite.runs) {
            if (!/^[A-Za-z][A-Za-z0-9]*$/.test(test.className) || executed.has(test.className)) throw new Error('Invalid test ' + test.className);
            executed.add(test.className);
            const actual = run('java', ['-Dstdout.encoding=UTF-8', '-Dstderr.encoding=UTF-8', '-cp', out, test.className]);
            if (test.expected !== null && actual !== test.expected) throw new Error(test.className + ': unexpected output ' + JSON.stringify(actual));
            console.log('PASS ' + suite.directory + '/' + test.className);
            if (test.expected === null) console.log(actual);
            passed++;
        }
    } finally { rmSync(out, { recursive: true, force: true }); }
}
for (const entry of manifest) for (const snippet of entry.snippets) {
    if (!covered.has(snippet.file.split('/')[0])) throw new Error('Uncovered snippet ' + snippet.id);
}
console.log('Java source executions: ' + passed + ' PASS');

const negatives = JSON.parse(readFileSync(join(source, 'negative-cases.json'), 'utf8'));
for (const test of negatives) {
    if (!/^[A-Za-z][A-Za-z0-9]*\.java$/.test(test.file) || !manifest.some(entry => entry.page === test.page)) {
        throw new Error('Invalid negative fixture ' + test.file);
    }
    const out = mkdtempSync(join(tmpdir(), 'academy-negative-'));
    try {
        const result = spawnSync('javac', ['--release', '21', '-encoding', 'UTF-8', '-XDrawDiagnostics', '-d', out, join(source, 'negative', test.file)], { encoding: 'utf8', timeout: 30000 });
        const diagnostics = (result.stderr || '').split(/\r?\n/).filter(line => line.includes('compiler.err.'));
        if (result.error || result.status !== 1 || diagnostics.length !== 1 || diagnostics[0].match(/\bcompiler\.err\.[\w.]+/)?.[0] !== test.diagnostic) {
            throw new Error(test.file + ': unexpected compiler result: ' + (result.error || result.stderr || result.stdout));
        }
        console.log('PASS expected compilation rejection ' + test.file);
    } finally { rmSync(out, { recursive: true, force: true }); }
}
console.log('Java negative compilation cases: ' + negatives.length + ' PASS');
