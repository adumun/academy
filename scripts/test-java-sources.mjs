import { mkdtempSync, rmSync } from 'node:fs';
import { tmpdir } from 'node:os';
import { join } from 'node:path';
import { spawnSync } from 'node:child_process';
import { fileURLToPath } from 'node:url';

const source = fileURLToPath(new URL('../examples/java/handbook/methods/', import.meta.url));
const out = mkdtempSync(join(tmpdir(), 'academy-source-'));
function run(command, args) {
    const result = spawnSync(command, args, { encoding: 'utf8', timeout: 20000 });
    if (result.error || result.status !== 0) throw new Error(command + ': ' + (result.error || result.stderr || result.stdout));
    process.stdout.write(result.stdout);
}
try {
    run('javac', ['--release', '21', '-encoding', 'UTF-8', '-d', out, ...['MetodosInstanciaDemo', 'MetodosDemo', 'SobrecargaDemo', 'MethodsTest'].map(name => join(source, name + '.java'))]);
    run('java', ['-cp', out, 'MethodsTest']);
} finally {
    rmSync(out, { recursive: true, force: true });
}
